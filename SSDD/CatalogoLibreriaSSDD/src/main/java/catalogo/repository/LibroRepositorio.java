package catalogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import catalogo.model.Editorial;
import catalogo.model.Libro;

public interface LibroRepositorio extends JpaRepository<Libro, Long> {

	Libro findById(long id);
	List<Libro> findAllByOrderById();
	
	List<Libro> findByAutores(String autores);
	
	Libro findByTitulo(String titulo);
	
	List<Libro> findByCategoria(String categoria);
	
	List <Libro> findByNPaginas(int nPaginas);

	
	List <Libro> findByPvp(float pvp);

	
	List <Libro> findByAnyoPublicacion(int anyoPublicacion);

	
	List <Libro> findByEditorial(Editorial editorial);


}
