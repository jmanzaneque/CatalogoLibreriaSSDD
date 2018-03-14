package catalogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import catalogo.model.Editorial;


public interface EditorialRepositorio extends JpaRepository<Editorial, Long>{
	List<Editorial> findByNombre(String nombre);
	List<Editorial> findAllByOrderByNombreAsc();
	List<Editorial> findByEmail(String email);
	List<Editorial> findAllByOrderByEmailAsc();
	List<Editorial> findByTelefono(long telefono);
	List<Editorial> findAllByOrderByTelefonoAsc();
	List<Editorial> findByCPostal(long cPostal);
	List<Editorial> findAllByOrderByCPostalAsc();
	List<Editorial> findByNif(long nif);
	List<Editorial> findAllByOrderByNifAsc();
	
}
