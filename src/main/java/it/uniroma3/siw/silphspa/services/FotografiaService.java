package it.uniroma3.siw.silphspa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import it.uniroma3.siw.silphspa.model.Album;
import it.uniroma3.siw.silphspa.model.Fotografia;
import it.uniroma3.siw.silphspa.model.Fotografo;
import it.uniroma3.siw.silphspa.repository.FotografiaRepository;

@Service
public class FotografiaService {

	@Autowired
	private FotografiaRepository fotografiaRepository;
	
	@Transactional
	public Fotografia cercaPerId(Long id) {
		return this.fotografiaRepository.findById(id).get();
	}
	
	@Transactional
	public Fotografia cercaPerNome(String nome) {
		return this.fotografiaRepository.findByNome(nome);
	}
	
	public Fotografia inserisci(Fotografia fotografia){
		return this.fotografiaRepository.save(fotografia);
	}
	
	@Transactional
	public List<Fotografia> tutte(){
		return (List<Fotografia>) this.fotografiaRepository.findAll();
	}

	@Transactional
	public List<Fotografia> cercaPerAlbum(Album album) {
		return (List<Fotografia>) this.fotografiaRepository.findByAlbum(album);
	}

	@Transactional
	public List<Fotografia> cercaPerFotografo(Fotografo fotografo) {
		return (List<Fotografia>)this.fotografiaRepository.findByFotografo(fotografo);
	}
}