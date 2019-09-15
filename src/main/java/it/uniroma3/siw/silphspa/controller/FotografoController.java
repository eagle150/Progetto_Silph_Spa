package it.uniroma3.siw.silphspa.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.silphspa.model.Fotografo;
import it.uniroma3.siw.silphspa.services.FotografoService;
import it.uniroma3.siw.silphspa.services.FotografoValidator;

@Controller
public class FotografoController {
	
	@Autowired
	private FotografoValidator fotografoValidator;
	
	@Autowired
	private FotografoService fotografoService;

	
	@RequestMapping("/addFotografo")
	public String addFotografo(Model model) {
		
		model.addAttribute("fotografo",new Fotografo());
		return "fotografoForm.html";
		
	}
	
	@RequestMapping(value="/fotografo/{id}", method=RequestMethod.GET)
	public String getFotografo(@PathVariable("id")Long id, Model model) {
		
		if(id!=null) {
			
			model.addAttribute("fotografo", this.fotografoService.cercaPerId(id));
			return "fotografo.html";
			
		}else {
			return getFotografi(model);
		}
		
	}
	
	@RequestMapping(value="/fotografi", method=RequestMethod.GET)
	public String getFotografi(Model model) {
		model.addAttribute("fotografi", this.fotografoService.tutti());
		return "fotografi";
	}
	
	@RequestMapping(value = "/fotografo", method = RequestMethod.POST)
	public String newFotografo(@Valid @ModelAttribute("fotografo") Fotografo fotografo, Model model,
			BindingResult bindingResult) {
		
		this.fotografoValidator.validate(fotografo, bindingResult);
		if(!bindingResult.hasErrors()) {
			
			this.fotografoService.inserisci(fotografo);
			model.addAttribute("messaggioConferma","Fotografo correttamente salvato!");
			return "funzionarioHome";
			
		}else {
			
			return "fotografoForm.html";
			
		}

	}
}
