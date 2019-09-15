package it.uniroma3.siw.silphspa.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.silphspa.model.Album;
import it.uniroma3.siw.silphspa.model.Fotografia;
import it.uniroma3.siw.silphspa.model.Fotografo;

public interface FotografiaRepository extends CrudRepository<Fotografia,Long> {

	public Fotografia findByNome(String nome);
	public List<Fotografia> findByAlbum(Album album);
	public List<Fotografia> findByFotografo(Fotografo fotografo);

}