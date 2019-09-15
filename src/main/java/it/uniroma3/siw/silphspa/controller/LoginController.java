package it.uniroma3.siw.silphspa.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.silphspa.model.Funzionario;
import it.uniroma3.siw.silphspa.services.FunzionarioService;
import it.uniroma3.siw.silphspa.services.FunzionarioValidator;


@Controller
public class LoginController {
	
	@Autowired
	private FunzionarioService funzionarioService;
	@Autowired
	private FunzionarioValidator funzionarioValidator;

	@RequestMapping(value = "/loginControl", method = RequestMethod.POST)
	public String controllaCredenziali(
			@Valid @ModelAttribute("funzionario") Funzionario funzionario, 
			Model model, BindingResult bindingResult) {
		this.funzionarioValidator.validate(funzionario, bindingResult);
		if(!bindingResult.hasErrors()) {
			
			Funzionario funzionarioInMemoria = this.funzionarioService.funzionarioPerEmail(funzionario.getEmail());
			if((funzionarioInMemoria!=null)&&(funzionario.checkPassword(funzionarioInMemoria))) {
				return "funzionarioHome";
			}
		}
		model.addAttribute("dipendente", funzionario);
		model.addAttribute("credenzialiErrate","Credenziali Errate");
		return "funzionarioLogin";
				
		
		
		
	}
}