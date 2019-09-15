package it.uniroma3.siw.silphspa.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.silphspa.model.Album;
import it.uniroma3.siw.silphspa.model.Fotografia;
import it.uniroma3.siw.silphspa.model.Fotografo;
import it.uniroma3.siw.silphspa.model.Funzionario;
import it.uniroma3.siw.silphspa.model.SearchQuery;
import it.uniroma3.siw.silphspa.services.AlbumService;
import it.uniroma3.siw.silphspa.services.FotografiaService;
import it.uniroma3.siw.silphspa.services.FotografoService;
import it.uniroma3.siw.silphspa.services.SearchQueryValidator;

@Controller
public class MainController {

	@Autowired
	private SearchQueryValidator searchQueryValidator;
	@Autowired
	private FotografiaService fotografiaService;
	@Autowired
	private FotografoService fotografoService;
	@Autowired
	private AlbumService albumService;
	@Autowired
	private FotografiaController fotografiaController;

	@RequestMapping(value="/about",method=RequestMethod.GET)
	public String mostraInformazioni() {
		return "aboutPage";
	}
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String homePage(Model model) {		
		model.addAttribute("search_query", new SearchQuery());
		return "index";
	}
	
	@RequestMapping(value="/search",method=RequestMethod.POST)
	public String searchMethod(@Valid @ModelAttribute("search_query") SearchQuery searchQuery,
			Model model, BindingResult bindingResult) {
		String nextPage = "index";
		this.searchQueryValidator.validate(searchQuery,bindingResult);
		if (!bindingResult.hasErrors()) {
			/* eseguo un controllo sul tipo di ricerca */
			if (searchQuery.getType().equals("Fotografia")) { //ricerca per Fotografia
				Fotografia fotografia_trovata = this.fotografiaService.cercaPerNome(searchQuery.getQuery()+".jpg");
				if (fotografia_trovata==null) {
					model.addAttribute("notFoundMessage","Non sono riuscito a trovare la fotografia richiesta");
					model.addAttribute("notFoundType","Fotografia");
					return "notFoundPage";
				}
				else {
					model.addAttribute("fotografia", fotografia_trovata);
					model.addAttribute("fotoPath", fotografiaController.downloadMethod(fotografia_trovata));
					model.addAttribute("isASearch", true);
					nextPage = "fotografia";
				}
			}
			else if (searchQuery.getType().equals("Album")) { //ricerca per Album
				Album album_trovato = this.albumService.cercaPerNome(searchQuery.getQuery());
				if (album_trovato==null) {
					model.addAttribute("notFoundMessage","Non sono riuscito a trovare l'album richiesto");
					model.addAttribute("notFoundType","Album");
					return "notFoundPage";
				}
				else {
					List<String> fotografie_paths = 
							fotografiaController.getPaths(this.fotografiaService.cercaPerAlbum(album_trovato));
					model.addAttribute("fotografie_paths",fotografie_paths);
					model.addAttribute("album", album_trovato);
					nextPage = "album";
				}
			}
			else { //ricerca per Fotografo
				Fotografo fotografo_trovato = this.fotografoService.cercaPerNome(searchQuery.getQuery());
				if (fotografo_trovato==null) {
					model.addAttribute("notFoundMessage","Non sono riuscito a trovare il fotografo richiesto");
					model.addAttribute("notFoundType","Fotografo");
					return "notFoundPage";
				}
				else {
					model.addAttribute("fotografo", fotografo_trovato);
					nextPage = "fotografo";
				}
			}
		}
		return nextPage;
	}
	
	@RequestMapping("/login")
	public String login(Model model) {
		Funzionario funzionario = new Funzionario();
		model.addAttribute("funzionario", funzionario);
		return "funzionarioLogin";
	}
}
