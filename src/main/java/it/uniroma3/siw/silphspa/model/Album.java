package it.uniroma3.siw.silphspa.model;

import javax.persistence.*;
import java.util.List;
import java.util.LinkedList;
import java.time.*;

@Entity
@NamedQuery(name="findAllAlbums", query="SELECT a FROM Album a")
public class Album {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String nome;
	private LocalDate dataRegistrazione;
	@OneToMany
	private List<Fotografia> fotografie;
	@ManyToOne
	private Fotografo fotografo;
	
	public Album(String nome) {
		this.nome = nome;
		this.dataRegistrazione = LocalDate.now();
		this.fotografie = new LinkedList<>();
	}
	
	public Album() {
		this.dataRegistrazione = LocalDate.now();
		this.fotografie = new LinkedList<>();
	}
	
	
	/* getters - setters */
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public LocalDate getDataRegistrazione() {
		return this.dataRegistrazione;
	}
	public void setDataRegistrazione(LocalDate data) {
		this.dataRegistrazione = data;
	}
	public List<Fotografia> getFotografie(){
		return this.fotografie;
	}
	public void setFotografie(List<Fotografia> listaFotografie) {
		this.fotografie = listaFotografie;
	}
	
	public Fotografo getFotografo() {
		return fotografo;
	}

	public void setFotografo(Fotografo fotografo) {
		this.fotografo = fotografo;
	}

	public boolean addFotografia(Fotografia fotografia) {
		return this.fotografie.add(fotografia);
	}

	
	/* equals - hashCode - toString */
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
		Album other = (Album) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Album [id = " + id + ", nome = " + nome + ", data di registrazione = " + dataRegistrazione + "]";
	}
	
}