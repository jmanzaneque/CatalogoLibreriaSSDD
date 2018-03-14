package catalogo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import catalogo.repository.EditorialRepositorio;
import catalogo.repository.LibroRepositorio;


public class LibroController {


	@Autowired
	private EditorialRepositorio repositorioEditoriales;
	
	@Autowired
	private LibroRepositorio repositorioLibros;
	
	/*controlador del catálogo que con localhost:8080 va a / que me lleva a la dinámica del catálogo*/
	@RequestMapping("/")
	public String tablon(Model model) {

		model.addAttribute("libros", repositorioLibros.findAll());
		model.addAttribute("editoriales",repositorioEditoriales.findAll());

		return "catalogo";
	}
	@RequestMapping("/nuevoLibro")
		public String insertar(Model model)
	
	
	
}
