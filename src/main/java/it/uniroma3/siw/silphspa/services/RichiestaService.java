package it.uniroma3.siw.silphspa.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.silphspa.model.Richiesta;
import it.uniroma3.siw.silphspa.model.Utente;
import it.uniroma3.siw.silphspa.repository.RichiestaRepository;

@Service
public class RichiestaService {

	@Autowired
	private RichiestaRepository richiestaRepository;
	
	@Transactional
	public Richiesta inserisci(Richiesta richiesta) {
		return this.richiestaRepository.save(richiesta);
	}
	
	@Transactional
	public Richiesta cercaPerId(Long id) {
		return this.richiestaRepository.findById(id).get();
	}

	@Transactional
	public List<Richiesta> cercaPerUtente(Utente utente) {
		return this.richiestaRepository.findByUtente(utente);
	}

}