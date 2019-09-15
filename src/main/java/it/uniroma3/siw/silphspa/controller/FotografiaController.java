package it.uniroma3.siw.silphspa.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.silphspa.model.Album;
import it.uniroma3.siw.silphspa.model.Fotografia;
import it.uniroma3.siw.silphspa.model.Fotografo;
import it.uniroma3.siw.silphspa.services.AlbumService;
import it.uniroma3.siw.silphspa.services.FotografiaService;
import it.uniroma3.siw.silphspa.services.FotografoService;

@Controller
public class FotografiaController {

	@Autowired
	private FotografiaService fotografiaService;
	@Autowired
	private FotografoService fotografoService;
	@Autowired
	private AlbumService albumService;

	/* path della directory per la gestione della galleria di immagini */
	/*System.getProperty("user.dir")+"/src/main/resources/static/*/
	private String download_path = //was protected static
			it.uniroma3.siw.silphspa.SilphSpaApplication.application_pathToStaticFolder+"/downloads_silph/";
	
	

	/**
	 * Questo metodo prende un oggetto Fotografia e ne crea il relativo file .jpg nella directory destinata alla gallery
	 * @param foto - oggetto di tipo Fotografia
	 * @return la stringa al percorso da usare nelle pagine html, null altrimenti
	 */
	protected String downloadMethod(Fotografia foto) {
		String file_name = foto.getId().toString()+"_"+foto.getNome();
		File file = new File(download_path+file_name);
		if (!file.exists()) { //se il file immagine non esiste lo creo
			try {
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(foto.getImmagine());
				fos.close();
				return "/downloads_silph/"+file_name;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		else //altrimenti ritorno il path al file gia' esistente
			return ("/downloads_silph/"+file_name);
	}
	
	/*@RequestMapping(value="/addFotografia", method=RequestMethod.GET)
	public String aggiungiFotografia(Model model) {
		model.addAttribute("fotografia", new Fotografia());
		return "fotografiaForm";
	}*/
	
	@RequestMapping(value="/addFotografia", method=RequestMethod.GET)
	public String aggiungiFotografoAllaFotografia(Model model) {
		model.addAttribute("fotografia", new Fotografia());
		model.addAttribute("fotografi",this.fotografoService.tutti());
		return "scegliFotografo";
	}
	
	@RequestMapping(value="/scegliAlbum/{id_fotografo}", method=RequestMethod.GET)
	public String aggiungiAlbumAllaFotografia(@PathVariable("id_fotografo")Long id_fotografo, 
			@ModelAttribute("fotografia")Fotografia fotografia, Model model) {
		Fotografo fotografo = this.fotografoService.cercaPerId(id_fotografo);
		model.addAttribute("fotografo",id_fotografo);
		model.addAttribute("albums_fotografo",fotografo.getAlbums());
		return "scegliAlbum";
	}
	
	@RequestMapping(value="/scegliFotografia/{id_fotografo}/{id_album}", method=RequestMethod.GET)
	public String aggiungiFileAllaFotografia(@PathVariable("id_album")Long id_album,
			@PathVariable("id_fotografo")Long id_fotografo,
			@ModelAttribute("fotografia")Fotografia fotografia,Model model) {
		model.addAttribute("fotografo",id_fotografo);
		model.addAttribute("album",id_album);
		return "scegliFotografia";
	}
	
	/**
	 * Questo metodo gestisce il caricamento sul database di un oggetto fotografia 
	 * @param file - questo file sara' scelto dall'utente durante l'uso della webapp
	 * @param fotografia - l'oggetto fotografia in salvataggio
	 * @param model
	 * @return la prossima vista
	 */
	@RequestMapping(value="/upload/{id_fotografo}/{id_album}", method=RequestMethod.POST)
	public String salvaFotografia(@RequestParam("image_file")MultipartFile file,
			@ModelAttribute("fotografia")Fotografia fotografia, Model model,
			@PathVariable("id_album")Long id_album, @PathVariable("id_fotografo")Long id_fotografo) {
		try {
			
			Fotografo fotografo = this.fotografoService.cercaPerId(id_fotografo);
			Album album = this.albumService.cercaPerId(id_album);
			
			/* aggiungo gli attributti alla fotografia */
			fotografia.setAlbum(album);
			fotografia.setFotografo(fotografo);
			fotografia.setImmagine(file.getBytes());
			if (!fotografia.getNome().contains(".jpg")) //aggiungo il tipo di file per le foto non predefinite
				fotografia.setNome(fotografia.getNome().concat(".jpg"));
			
			/* salvo la fotografia */
			fotografia = this.fotografiaService.inserisci(fotografia);	
			
			/* aggiungo i riferimenti alla fotografia e aggiorno le entita' */
			fotografo.addFoto(fotografia);
			album.addFotografia(fotografia);
			
			this.fotografoService.inserisci(fotografo);
			this.albumService.inserisci(album);		
			
			if (downloadMethod(fotografia)!=null) {
				model.addAttribute("messaggioConferma","Fotografia correttamente salvata!");
				return "funzionarioHome";	
			}
			else {
				model.addAttribute("erroreIO", "non sono riuscito a creare il file dall'array di byte");
				return "myErrorPage";
			}
		} catch (IOException e) {
			e.printStackTrace();
			model.addAttribute("erroreIO", "non sono riuscito a creare l'array di byte dal file");
			return "myErrorPage";
		}
	}
	
	/**
	 * Questo metodo gestisce la visualizzazione della galleria con tutte le immagini salvate nel db
	 * @param model
	 * @return la prossima vista
	 */
	@RequestMapping(value="/gallery", method=RequestMethod.GET)
	public String visualizzaGalleriaFotografie(Model model) {
		/* recupero tutti gli oggetti Fotografia salvati nel db */
		List<Fotografia> fotografie = this.fotografiaService.tutte();
		/* riempio la directory creando i relativi file .jpg
		 * inoltre creo una lista con tutti i percorsi relativi ai files creati */
		List<String> files_galleria = getPaths(fotografie);
		if (files_galleria==null) { //la lista dei paths e' vuota
			model.addAttribute("erroreIO", "non riesco a scaricare tutti i file, leggi la console di eclipse");
			return "myErrorPage";
		}
		model.addAttribute("files_galleria", files_galleria);
		return "gallery";
	}

	/**
	 * Questo metodo si occupa di scaricare le fotografie presenti nella lista passata per parametro
	 * crea inoltre una lista con tutti i percorsi di riferimento per la visualizzazione
	 * @param List<Fotografia> fotografie
	 * @return List<String> percorsi alle foto
	 */
	protected List<String> getPaths(List<Fotografia> fotografie) {
		List<String> file_paths = new LinkedList<>();
		String temp_filepath = "";
		for (Fotografia f : fotografie) {
			temp_filepath = downloadMethod(f);
			if (temp_filepath==null) {
				System.out.println("non riesco a scaricare il file "+f.getId()+"_"+f.getNome());
				return null;
			}
			file_paths.add(temp_filepath);
		}
		
		return file_paths;
	}
	
	/**
	 * Questo metodo gestisce la visualizzazione delle fotografie di un fotografo
	 * @param model
	 * @return la prossima vista
	 */
	@RequestMapping(value="/fotografiePerFotografo/{id_fotografo}", method=RequestMethod.GET)
	public String visualizzaFotografieFotografo(@PathVariable("id_fotografo")Long id_fotografo, Model model) {
		List<Fotografia> fotografieFotografo = 
				this.fotografiaService.cercaPerFotografo(this.fotografoService.cercaPerId(id_fotografo));
		List<String> file_paths = getPaths(fotografieFotografo);
		if (file_paths==null) { //la lista dei paths e' vuota
			model.addAttribute("erroreIO", "non riesco a scaricare tutti i file, leggi la console di eclipse");
			return "myErrorPage";
		}
		model.addAttribute("fotografie_paths", file_paths);
		return "fotografie";
	}
	
	@RequestMapping(value="/fotografia/{id}", method=RequestMethod.GET)
	public String visualizzaFotografia(@PathVariable("id")Long id_fotografia, Model model) {
		Fotografia fotografia_trovata = this.fotografiaService.cercaPerId(id_fotografia);
		model.addAttribute("fotografia", fotografia_trovata);
		model.addAttribute("fotoPath", downloadMethod(fotografia_trovata));
		return "fotografia";
	}
	
}
