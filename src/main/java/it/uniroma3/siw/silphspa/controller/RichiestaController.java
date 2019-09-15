package it.uniroma3.siw.silphspa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.silphspa.model.Fotografia;
import it.uniroma3.siw.silphspa.model.Richiesta;
import it.uniroma3.siw.silphspa.model.Utente;
import it.uniroma3.siw.silphspa.services.RichiestaService;
import it.uniroma3.siw.silphspa.services.ShoppingCartServiceImpl;
import it.uniroma3.siw.silphspa.services.UtenteService;

@Controller
public class RichiestaController {

	@Autowired
	private RichiestaService richiestaService;
	@Autowired
	private UtenteService utenteService;
	@Autowired
	private ShoppingCartServiceImpl shoppingCartService;
	
	@RequestMapping(value="/salvaRichiesta",method=RequestMethod.POST)
	public String validazioneRichiesta(@ModelAttribute("richiesta")Richiesta richiesta, Model model,
			BindingResult bindingResult, @ModelAttribute("utente")Utente utente) {
		
		List<Fotografia> fotografie = shoppingCartService.getFotografieNelCarrello(); //prendo le fotografie nel carrello
		
		
		Utente utente_trovato = this.utenteService.cercaPerEmail(utente.getEmail());
		if (utente_trovato!=null) { //utente gia' noto al sistema, aggiorno i suoi dati
			utente_trovato.setNome(utente.getNome());
			utente_trovato.setCognome(utente.getCognome());
			utente = utente_trovato;
		}

		richiesta.setUtente(utente);
		for (Fotografia f : fotografie) {
			richiesta.addFotografia(f);
		}
		utente.addRichiesta(richiesta);
		this.utenteService.inserisci(utente); //inserisce il nuovo utente o lo aggiorna
		this.richiestaService.inserisci(richiesta);
		
		model.addAttribute("richiesteUtente", utente.getRichieste());
		this.shoppingCartService.azzeraCarrello(); //azzero il carrello per continuare a utilizzare l'applicazione
		return "fineRichiesta";
	}
	
	@RequestMapping(value="/richiesta/{id}",method=RequestMethod.GET)
	public String salvataggioRichiesta(@PathVariable("id")Long id_richiesta, Model model) {
		Richiesta richiesta = this.richiestaService.cercaPerId(id_richiesta);
		model.addAttribute("richiesta", richiesta);
		model.addAttribute("fotografie", richiesta.getFoto());
		model.addAttribute("utente", richiesta.getUtente());
		return "richiesta";
	}
}
