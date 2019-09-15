package it.uniroma3.siw.silphspa.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.silphspa.model.Fotografo;

public interface FotografoRepository extends CrudRepository<Fotografo,Long> {

	public Fotografo findByNome(String nome);
	public Fotografo findByNomeAndCognome(String nome, String Cognome);
	
}
