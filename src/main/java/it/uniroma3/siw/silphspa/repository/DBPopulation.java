package it.uniroma3.siw.silphspa.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.silphspa.model.Album;
import it.uniroma3.siw.silphspa.model.Fotografia;
import it.uniroma3.siw.silphspa.model.Fotografo;
import it.uniroma3.siw.silphspa.model.Funzionario;
import it.uniroma3.siw.silphspa.services.AlbumService;
import it.uniroma3.siw.silphspa.services.FotografiaService;
import it.uniroma3.siw.silphspa.services.FotografoService;
import it.uniroma3.siw.silphspa.services.FunzionarioService;

/*
 * è un componente della nostra applicazione
 */
@Component
public class DBPopulation implements ApplicationRunner{

	@Autowired
	private AlbumService albumService;
	@Autowired
	private FotografoService fotografoService;
	@Autowired
	private FotografiaService fotografiaService;
	@Autowired
	private FunzionarioService funzionarioService;
	
	
	

	@Override
	public void run(ApplicationArguments args) throws Exception {
		//this.deleteAll(); questa istruzione non serve per ora (la strategia d'avvio del db e' create)
		this.addAll();
	}
	
	/*private void deleteAll() {         vedi sopra
		albumService.deleteAll();
		fotografoService.deleteAll();
		fotografiaService.deleteAll();
	}*/
	
	
	private void addAll() {
		
		Funzionario funzionario1;
		Funzionario funzionario2;
		
		funzionario1 = funzionarioSet("Valerio", "Rossi", "valerossi@gmail.com","1234");
		funzionario2 = funzionarioSet("Gabriele", "Bianchi", "gabribianchi@gmail.com","4321");
		
		/*salvo i funzionari nel DB*/
		this.funzionarioService.inserisci(funzionario1);
		this.funzionarioService.inserisci(funzionario2);
		
		/*seguiranno salvataggi di foto,fotografi e album per testare la piattaforma*/
		
		/* salvataggio fotografi */
		Fotografo fotografo1 = new Fotografo("Mario","Gentili");
		Fotografo fotografo2 = new Fotografo("Alfredo","Biondi");
		this.fotografoService.inserisci(fotografo1);
		this.fotografoService.inserisci(fotografo2);
		
		/* salvataggio album */
		Album album1 = new Album("architettura");
		Album album2 = new Album("natura");
		Album album3 = new Album("shots");
		album1.setFotografo(fotografo1);
		fotografo1.addAlbum(album1);
		album2.setFotografo(fotografo1);
		fotografo1.addAlbum(album2);
		album3.setFotografo(fotografo2);
		fotografo2.addAlbum(album3);
		this.albumService.inserisci(album1);
		this.albumService.inserisci(album2);
		this.albumService.inserisci(album3);
		
		/* salvataggio fotografie */
		String images_path = it.uniroma3.siw.silphspa.SilphSpaApplication.application_pathToStaticFolder+"/images/";
		String[] files = new File(images_path).list();
		File file = null;
		Fotografia foto = null;
		for (String s : files) {
			file = new File(images_path+s);
			foto = new Fotografia();
			try {
				foto.setImmagine(IOUtils.toByteArray(new FileInputStream(file)));
			} catch (IOException e) {
				System.out.println("errore durante l'estrazione di byte[]\nfile: "+images_path+s);
				e.printStackTrace();
			}
			foto.setNome(s);
			if (s.equals("beautiful_seaside.jpg")
					|| s.equals("sunset_tree.jpg")
					|| s.equals("little_fish.jpg")) {
				album2.addFotografia(foto);
				foto.setAlbum(album2);
				foto.setFotografo(album2.getFotografo());
			} else if (s.equals("venice.jpg")) {
				album1.addFotografia(foto);
				foto.setAlbum(album1);
				foto.setFotografo(album1.getFotografo());
			} else {
				album3.addFotografia(foto);
				foto.setAlbum(album3);
				foto.setFotografo(album3.getFotografo());
			}
			this.fotografiaService.inserisci(foto);
		}
	}
	
	
	private Funzionario funzionarioSet(String nome, String cognome, String email, String password) {
		Funzionario f = new Funzionario();
		f.setNome(nome);
		f.setCognome(cognome);
		f.setEmail(email);
		f.setPassword(password);
		return f;
	}
	
}
