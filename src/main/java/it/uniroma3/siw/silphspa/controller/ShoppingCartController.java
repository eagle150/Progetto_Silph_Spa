package it.uniroma3.siw.silphspa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.uniroma3.siw.silphspa.model.Fotografia;
import it.uniroma3.siw.silphspa.model.Richiesta;
import it.uniroma3.siw.silphspa.model.Utente;
import it.uniroma3.siw.silphspa.services.FotografiaService;
import it.uniroma3.siw.silphspa.services.ShoppingCartServiceImpl;

@Controller
public class ShoppingCartController {

	private final ShoppingCartServiceImpl shoppingCartService;
	
	private final FotografiaService fotografiaService;

	@Autowired
	public ShoppingCartController(ShoppingCartServiceImpl shoppingCartService, FotografiaService fotografiaService) {
		this.shoppingCartService = shoppingCartService;
		this.fotografiaService = fotografiaService;
	}

	@GetMapping("/shoppingCart")
	public ModelAndView shoppingCart() {
		ModelAndView modelAndView = new ModelAndView("/shoppingCart");
		modelAndView.addObject("fotografie", shoppingCartService.getFotografieNelCarrello());
		return modelAndView;
	}

	/* seguono due metodi sovraccarichi per la gestione dell'aggiunta di fotografie al carrello
	 * entrambi reagiscono allo stesso modo tramite */
	@GetMapping("/shoppingCart/addFotografia/{fotografiaId}")
	public ModelAndView aggiungiFotografiaAlCarrello(@PathVariable("fotoId") Long fotoId) {
		if (!this.shoppingCartService.getFotografieNelCarrello().contains(this.fotografiaService.
				cercaPerId(fotoId))) {
			this.shoppingCartService.aggiungiFotografia(this.fotografiaService.cercaPerId(fotoId));
		}
		return shoppingCart();
	}
	
	@GetMapping(value= {"/shoppingCart/aggiungiAlCarrelloDallaGallery",
						"/album/shoppingCart/aggiungiAlCarrelloDallAlbum",
						"/shoppingCart/aggiungiAlCarrelloDallAlbum",
						"/fotografiePerFotografo/shoppingCart/aggiungiAlCarrelloDalleFotografie",
						"/shoppingCart/aggiungiAlCarrelloDalleFotografie"})
	public ModelAndView aggiungiFotoAlCarrello(@RequestParam("fotoPath") String fotoPath) {
		/* stessa logica del metodo aggiungiFotografiaAlCarrello(...) ma usa il path della foto */
		Fotografia foto = this.fotografiaService.cercaPerId(extractIdFromPath(fotoPath));
		if (!this.shoppingCartService.getFotografieNelCarrello().contains(foto)) {
			this.shoppingCartService.aggiungiFotografia(foto);
		}
		return shoppingCart();
	}

	@GetMapping("/shoppingCart/rimuoviFotografia/{fotografiaId}")
	public ModelAndView rimuoviFotografiaDalCarrello(@PathVariable("fotografiaId") Long fotoId) {
		if (this.shoppingCartService.getFotografieNelCarrello().contains(this.fotografiaService.
				cercaPerId(fotoId))) {
			this.shoppingCartService.rimuoviFotografia(this.fotografiaService.cercaPerId(fotoId));
		}
			return shoppingCart();
	}

	@GetMapping("/shoppingCart/checkout")
	public ModelAndView checkout() {
		ModelAndView nextModelAndView = new ModelAndView("/richiestaForm");
		nextModelAndView.addObject("richiesta", new Richiesta());
		nextModelAndView.addObject("utente", new Utente());
		return nextModelAndView;
	}

	/**
	 * Questo metodo estrae l'id della fotografia dal percorso del relativo file
	 * @param path - il percorso del file
	 * @return (Long) id
	 */
	private Long extractIdFromPath(String path) {
		char[] name_file = path.substring(17).toCharArray();
		String id_string = "";
		for (char c : name_file) {
			if (!(c>='0' && c<='9'))
				break;
			else {
				id_string = id_string.concat(Character.toString(c));
			}
		}
		return Long.parseLong(id_string);
	}
}