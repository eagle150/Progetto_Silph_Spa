package it.uniroma3.siw.silphspa.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.silphspa.model.*;

public interface FunzionarioRepository extends CrudRepository<Funzionario,Long>{
	
	public Funzionario findByEmail(String email);

}
