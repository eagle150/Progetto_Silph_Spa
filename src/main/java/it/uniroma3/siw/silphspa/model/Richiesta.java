package it.uniroma3.siw.silphspa.model;

import java.time.LocalDate;
import java.util.*;
import javax.persistence.*;

@Entity
public class Richiesta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String descrizione;
	private LocalDate dataInserimento;
	@ManyToMany
	private List<Fotografia> foto;
	@ManyToOne
	private Utente utente;
	
	public Richiesta() {
		foto = new LinkedList<>();
		dataInserimento = LocalDate.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<Fotografia> getFoto() {
		return foto;
	}

	public void setFoto(List<Fotografia> foto) {
		this.foto = foto;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	
	public LocalDate getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(LocalDate data) {
		this.dataInserimento = data;
	}
	
	public boolean addFotografia(Fotografia f) {
		return this.foto.add(f);
	}

	@Override
	public String toString() {
		StringBuilder descrizione = new StringBuilder("L'utente "+this.utente.getNome()+" "+this.utente.getCognome()+
				", ha richiesto l'accesso alle seguenti foto:\n");
		
		for(Fotografia f : foto) {
			descrizione.append(f.getNome());
		}
		
		return descrizione.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Richiesta other = (Richiesta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
