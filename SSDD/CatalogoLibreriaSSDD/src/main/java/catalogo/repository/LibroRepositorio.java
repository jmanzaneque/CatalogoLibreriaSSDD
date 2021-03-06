package catalogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import catalogo.model.Libro;

public interface LibroRepositorio extends JpaRepository<Libro, Long> {

	Libro findById(long id);
	List<Libro> findAllByOrderById();
	
	List<Libro> findByAutores(String autores);
	List<Libro> findAllByOrderByAutoresAsc();
	List<Libro> findAllByOrderByAutoresDesc();
	
	Libro findByTitulo(String titulo);
	List<Libro> findAllByOrderByTituloAsc();
	List<Libro> findAllByOrderByTituloDesc();
	
	List<Libro> findByCategoria(String categoria);
	List<Libro> findAllByOrderByCategoriaAsc();
	List<Libro> findAllByOrderByCategoriaDesc();
	
	List <Libro> findByNPaginas(int nPaginas);
	List<Libro> findAllByOrderByNPaginasAsc();
	List<Libro> findAllByOrderByNPaginasDesc();

	
	List <Libro> findByPvp(float pvp);
	List<Libro> findAllByOrderByPvpAsc();
	List<Libro> findAllByOrderByPvpDesc();
	
	
	List <Libro> findByAnyoPublicacion(int anyoPublicacion);
	List<Libro> findAllByOrderByAnyoPublicacionAsc();
	List<Libro> findAllByOrderByAnyoPublicacionDesc();

	
/*	List <Libro> findByEditorial(Editorial editorial);
	List<Libro> findAllByOrderByEditorialAsc();*/


}
