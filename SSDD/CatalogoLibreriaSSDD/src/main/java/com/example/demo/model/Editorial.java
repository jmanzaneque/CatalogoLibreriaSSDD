package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Editorial {
	@Id
	private long nif;
	
	
	private String nombre, email;
	private long telefono, cPostal;
	
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
