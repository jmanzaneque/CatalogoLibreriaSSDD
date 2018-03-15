package catalogo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import catalogo.model.Editorial;
import catalogo.model.Libro;
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
	@RequestMapping(value="/nuevoLibro")
	public String registraLibro(
			@RequestParam(value="autores") String autores,
			@RequestParam(value="titulo") String titulo,
			@RequestParam(value="categoria") String categoria,
			@RequestParam(value="nPaginas") int nPaginas,
			@RequestParam(value="pvp") float pvp,
			@RequestParam(value="anyoPublicacion") int anyoPublicacion,
			@RequestParam(value="editorial") Editorial editorial,
			Model model) {
		
		Libro newLibro = new Libro(autores, titulo, categoria, nPaginas, pvp, anyoPublicacion, editorial);
		repLibro.save(newLibro);
		model.addAttribute("repLibro", repEditorial);
		return "LibroRegistrada";
	}
	
}