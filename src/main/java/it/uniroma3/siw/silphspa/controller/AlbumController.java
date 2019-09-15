package it.uniroma3.siw.silphspa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.silphspa.model.Album;
import it.uniroma3.siw.silphspa.model.Fotografo;
import it.uniroma3.siw.silphspa.services.AlbumService;
import it.uniroma3.siw.silphspa.services.FotografiaService;
import it.uniroma3.siw.silphspa.services.FotografoService;

@Controller
public class AlbumController {
	
	@Autowired
	private AlbumService albumService;
	@Autowired
	private FotografiaService fotografiaService;
	@Autowired
	private FotografiaController fotografiaController;
	@Autowired
	private FotografoService fotografoService;

	@RequestMapping(value = "/album/{id}", method = RequestMethod.GET)
	public String getAlbum(@PathVariable("id") Long id, Model model) {
		if (id != null) {
			Album album = this.albumService.cercaPerId(id);
			List<String> fotografie_paths = fotografiaController.getPaths(this.fotografiaService.cercaPerAlbum(album));
			model.addAttribute("fotografie_paths",fotografie_paths);
			model.addAttribute("album", album);
			return "album";
		} else
			return getAlbums(model);
	}
	
	@RequestMapping(value="/albumsPerFotografo/{id_fotografo}",method=RequestMethod.GET)
	public String getAlbumPerFotografo(@PathVariable("id_fotografo")Long id_fotografo, Model model) {
		if (id_fotografo!=null) {
			model.addAttribute("albums",this.albumService.cercaPerFotografo(this.fotografoService.cercaPerId(id_fotografo)));
			return "albums";
		}
		else
			return getAlbums(model);
	}
	
	@RequestMapping(value = "/albums", method = RequestMethod.GET)
	public String getAlbums(Model model) {
		model.addAttribute("albums", this.albumService.tutti());
		return "albums";
	}
	
	@RequestMapping(value="/addAlbum",method=RequestMethod.GET)
	public String aggiungiAlbum(Model model) {
		model.addAttribute("album",new Album());
		model.addAttribute("fotografi",this.fotografoService.tutti());
		return "scegliFotografoAlbum";
	}
	
	@RequestMapping(value= "/creaAlbum/{id_fotografo}",method=RequestMethod.GET)
	public String aggiungiNomeAlbum(@PathVariable("id_fotografo")Long id_fotografo, Model model,
			@ModelAttribute("album")Album album) {
		model.addAttribute("fotografo",id_fotografo);
		model.addAttribute("album", album);
		return "albumForm";
	}
	
	@RequestMapping(value="/salvaAlbum/{id_fotografo}",method=RequestMethod.GET)
	public String salvaAlbum(@ModelAttribute("album")Album album, Model model,
			@PathVariable("id_fotografo")Long id_fotografo) {
		Fotografo fotografo = this.fotografoService.cercaPerId(id_fotografo);
		album.setFotografo(fotografo);
		fotografo.addAlbum(album);
		this.albumService.inserisci(album);
		this.fotografoService.inserisci(fotografo);
		
		model.addAttribute("messaggioConferma","Album correttamente salvato!");
		return "funzionarioHome";
	}

}
