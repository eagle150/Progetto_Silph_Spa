package it.uniroma3.siw.silphspa.model;

import javax.persistence.*;

@Entity
public class Fotografia {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String nome;
	@Column(nullable=false) //la logica del caricamento delle immagini non risponderebbe se questo attributo fosse nullo
	private byte[] immagine;
	@ManyToOne
	private Fotografo fotografo;
	@ManyToOne
	private Album album;
	

	public Fotografia(String nome, Fotografo fotografo, Album album) {
		this.nome = nome;
		this.fotografo = fotografo;
		this.album = album;
	}

	public Fotografia(String nome, Fotografo fotografo) {
		this.nome = nome;
		this.fotografo = fotografo;
	}
	
	public Fotografia(String nome) {
		this.nome = nome;
	}

	public Fotografia() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Fotografo getFotografo() {
		return fotografo;
	}

	public void setFotografo(Fotografo fotografo) {
		this.fotografo = fotografo;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public byte[] getImmagine() {
		return immagine;
	}

	public void setImmagine(byte[] file) {
		this.immagine = file;
	}
	
	@Override
	public int hashCode() {
		return this.getNome().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		Fotografia f = (Fotografia) obj;
		return this.getId().equals(f.getId());
	}
	
	

}