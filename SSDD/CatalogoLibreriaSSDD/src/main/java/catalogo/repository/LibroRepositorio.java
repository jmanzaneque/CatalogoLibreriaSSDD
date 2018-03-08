package catalogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import catalogo.model.Libro;

public interface LibroRepositorio extends JpaRepository<Libro, Long> {

	Libro findById(String id);
	
	List<Libro> findAllByOrderById();
}
