package catalogo.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Libro {
	
	@Id
	private long id;
	
	private String autores, titulo, categoria;
	private int nPaginas;
	private float pvp;
	private int anyoPublicacion;
	private Editorial editorial;
	
	public Libro (String autores, String titulo, String categoria, int nPaginas, float pvp, int anyoPublicacion, Editorial editorial) {
		this.autores = autores;
		this.titulo = titulo;
		this.categoria = categoria;
		this.nPaginas = nPaginas;
		this.pvp = pvp;
		this.anyoPublicacion = anyoPublicacion;
		this.editorial = editorial;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAutores() {
		return autores;
	}

	public void setAutores(String autores) {
		this.autores = autores;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public int getnPaginas() {
		return nPaginas;
	}

	public void setnPaginas(int nPaginas) {
		this.nPaginas = nPaginas;
	}

	public float getPvp() {
		return pvp;
	}

	public void setPvp(float pvp) {
		this.pvp = pvp;
	}

	public int getAnyoPublicacion() {
		return anyoPublicacion;
	}

	public void setAnyoPublicacion(int anyoPublicacion) {
		this.anyoPublicacion = anyoPublicacion;
	}

	public Editorial getEditorial() {
		return editorial;
	}

	public void setEditorial(Editorial editorial) {
		this.editorial = editorial;
	}
	
	
}
