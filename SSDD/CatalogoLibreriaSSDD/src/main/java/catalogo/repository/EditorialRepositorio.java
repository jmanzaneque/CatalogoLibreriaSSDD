package catalogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import catalogo.model.Editorial;

public interface EditorialRepositorio extends JpaRepository<Editorial, Long>{
	Editorial findById(String id);
	List<Editorial> findAllByOrderById();
}
