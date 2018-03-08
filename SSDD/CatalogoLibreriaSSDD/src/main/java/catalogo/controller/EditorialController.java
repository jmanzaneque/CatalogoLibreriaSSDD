package catalogo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import catalogo.model.Editorial;
import catalogo.repository.EditorialRepositorio;

@Controller
public class EditorialController {
	
	@Autowired
	private EditorialRepositorio repEditorial;
	
	@RequestMapping(value="/registroEditorialCompletado")
	public String registraEditorial(
			@RequestParam(value="nombre") String nombre,
			@RequestParam(value="telefono") long telefono,
			@RequestParam(value="email") String email,
			@RequestParam(value="cPostal") long cPostal,
			@RequestParam(value="nif") long nif,
			Model model) {
		
		Editorial newEditorial = new Editorial(nombre, telefono, email, cPostal, nif);
		repEditorial.save(newEditorial);
		model.addAttribute("repEditoriales", repEditorial);
		return "editorialRegistrada";
	}
	


}
