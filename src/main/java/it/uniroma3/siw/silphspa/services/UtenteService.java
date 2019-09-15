package it.uniroma3.siw.silphspa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.silphspa.model.Utente;
import it.uniroma3.siw.silphspa.repository.UtenteRepository;

@Service
public class UtenteService {
	
	@Autowired
	private UtenteRepository utenteRepository;

	@Transactional
	public Utente inserisci(Utente utente) {
		return this.utenteRepository.save(utente);
	}

	@Transactional
	public Utente cercaPerId(Long id) {
		return this.utenteRepository.findById(id).get();
	}

	public Utente cercaPerEmail(String email) {
		return this.utenteRepository.findByEmail(email);
	}
}
