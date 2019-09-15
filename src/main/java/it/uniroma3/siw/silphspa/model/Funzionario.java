package it.uniroma3.siw.silphspa.model;

import javax.persistence.*;

@Entity
@NamedQuery(name="findAllFunzionari", query="SELECT f FROM Funzionario f")
public class Funzionario {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String password;
    private String nome;
    private String cognome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    

	public boolean checkPassword(Funzionario funzionario) {
		String passwordDaVerificare = funzionario.getPassword();
		return this.password.equals(passwordDaVerificare);
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
		Funzionario other = (Funzionario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Funzionario [id = " + id + ", nome = " + nome + ", cognome = " + cognome + "]";
	}
	
}