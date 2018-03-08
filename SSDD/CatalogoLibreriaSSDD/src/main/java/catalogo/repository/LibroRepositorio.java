package catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import catalogo.model.Libro;

public interface LibroRepositorio extends JpaRepository<Libro, Long> {

}
