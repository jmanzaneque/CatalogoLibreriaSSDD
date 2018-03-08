package catalogo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Editorial {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String nombre, email;
	private long telefono, cPostal, nif;
	
	public Editorial (String nombre, long telefono, String email, long cPostal, long nif) {
		this.nombre = nombre;
		this.telefono = telefono;
		this.email = email;
		this.cPostal = cPostal;
		this.nif = nif;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getNif() {
		return nif;
	}
	public void setNif(long nif) {
		this.nif = nif;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getTelefono() {
		return telefono;
	}
	public void setTelefono(long telefono) {
		this.telefono = telefono;
	}
	public long getcPostal() {
		return cPostal;
	}
	public void setcPostal(long cPostal) {
		this.cPostal = cPostal;
	}
	
	
}