package it.uniroma3.siw.silphspa.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.silphspa.model.Album;
import it.uniroma3.siw.silphspa.model.Fotografo;

public interface AlbumRepository extends CrudRepository<Album,Long> {

	public Album findByNome(String nome);

	public List<Album> findByFotografo(Fotografo fotografo);
	
}
