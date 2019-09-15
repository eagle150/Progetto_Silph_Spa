package it.uniroma3.siw.silphspa.model;

import javax.persistence.*;
import java.util.List;
import java.util.LinkedList;

@Entity
@NamedQuery(name="findAllFotografi", query="SELECT f FROM Fotografo f")
public class Fotografo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private String cognome;
	@OneToMany(mappedBy = "fotografo",
			   cascade = CascadeType.REMOVE)
	private List<Fotografia> foto;
	@OneToMany(mappedBy = "fotografo", //added this mappedBy
			   cascade = CascadeType.REMOVE)
	private List<Album> albums;
	
	public Fotografo(String nome, String cognome) {
		this.nome = nome;
		this.cognome = cognome;
		this.foto = new LinkedList<>();
		this.albums = new LinkedList<>();
	}
	
	public Fotografo() {
		this.foto = new LinkedList<>();
		this.albums = new LinkedList<>();
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
	public String getCognome() {
		return this.cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public List<Fotografia> getFoto() {
		return this.foto;
	}

	public void setFoto(List<Fotografia> foto) {
		this.foto = foto;
	}

	public List<Album> getAlbums() {
		return this.albums;
	}
	public void setAlbums(List<Album> listaAlbums) {
		this.albums = listaAlbums;
	}
	
	public boolean addAlbum(Album album) {
		return this.albums.add(album);
	}
	
	public boolean addFoto(Fotografia foto) {
		return this.foto.add(foto);
	}
	
	
	/* equals - hashCode - toString */
	@Override
	public int hashCode() {
		return this.getNome().hashCode()+this.getCognome().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		Fotografo f = (Fotografo) obj;
		return (this.getNome().equals(f.getNome()) && this.getCognome().equals(f.getCognome()));
	}

	@Override
	public String toString() {
		return "Fotografo [id = " + id + ", nome = " + nome + ", cognome = " + cognome + ", n. album=" + albums.size() + "]";
	}
}