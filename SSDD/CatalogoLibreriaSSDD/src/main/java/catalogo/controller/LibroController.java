package catalogo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import catalogo.repository.EditorialRepositorio;
import catalogo.repository.LibroRepositorio;

@Controller
public class LibroController {


	@Autowired
	private EditorialRepositorio repEditorial;
	
	@Autowired
	private LibroRepositorio repLibro;
	
	/*controlador del catálogo que con localhost:8080 va a / que me lleva a la dinámica del catálogo*/
	@RequestMapping("/")
	public String tablon(Model model) {

		//model.addAttribute("libro", repLibro.findAll());
		model.addAttribute("totalEditoriales",repEditorial.findAll());

		return "catalogo";
	}
	
	@RequestMapping("/nuevoLibro")
		public void insertar(Model model) {
		
	}
}