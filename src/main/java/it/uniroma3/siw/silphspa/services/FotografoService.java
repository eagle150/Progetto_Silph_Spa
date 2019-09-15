package it.uniroma3.siw.silphspa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import it.uniroma3.siw.silphspa.model.Fotografo;
import it.uniroma3.siw.silphspa.repository.FotografoRepository;

@Service
public class FotografoService {
	
	@Autowired
	private FotografoRepository fotografoRepository;
	
	@Transactional
	public Fotografo cercaPerNome(String nome) {
		return this.fotografoRepository.findByNome(nome);
	}
	
	@Transactional
	public Fotografo cercaPerNomeECognome(String nome, String cognome) {
		return this.fotografoRepository.findByNomeAndCognome(nome, cognome);
	}
	
	@Transactional
	public Fotografo inserisci(Fotografo fotografo) {
		return this.fotografoRepository.save(fotografo);
	}
	
	@Transactional
	public List<Fotografo> tutti() {
		return (List<Fotografo>)this.fotografoRepository.findAll();
	}
	
	@Transactional
	public Fotografo cercaPerId(Long id) {
		return this.fotografoRepository.findById(id).get();
	}

}
