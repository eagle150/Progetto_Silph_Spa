package it.uniroma3.siw.silphspa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.silphspa.model.Funzionario;
import it.uniroma3.siw.silphspa.repository.FunzionarioRepository;

@Service
public class FunzionarioService {
	
	@Autowired
	private FunzionarioRepository funzionarioRepository;
	
	@Transactional
	public Funzionario funzionarioPerEmail(String email) {
		return this.funzionarioRepository.findByEmail(email);
	}

	@Transactional
	public Funzionario inserisci(Funzionario funzionario) {
		return this.funzionarioRepository.save(funzionario);
	}

}
