package it.uniroma3.siw.silphspa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FunzionarioController {
	
	@RequestMapping(value="/homeFunzionario",method=RequestMethod.GET)
	public String returnToHomeFunzionario() {
		return "funzionarioHome";
	}

}
