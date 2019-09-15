package it.uniroma3.siw.silphspa.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.silphspa.model.Richiesta;
import it.uniroma3.siw.silphspa.model.Utente;

public interface RichiestaRepository extends CrudRepository<Richiesta,Long> {

	List<Richiesta> findByUtente(Utente utente);

}
