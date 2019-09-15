package it.uniroma3.siw.silphspa.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.silphspa.model.Utente;

public interface UtenteRepository extends CrudRepository<Utente,Long> {

	Utente findByEmail(String email);

}
