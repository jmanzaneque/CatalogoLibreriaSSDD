package catalogo.controller;

import java.util.List;

import javax.annotation.PostConstruct;

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

		model.addAttribute("totalLibros", repLibro.findAll());
		model.addAttribute("totalEditoriales",repEditorial.findAll());
		return "catalogo";
	}
	
	@RequestMapping("/nuevoLibro")
	public String insertar(Model model) {	
		List<Editorial> editoriales = repEditorial.findAllByOrderByNombreAsc();
		model.addAttribute("totalEditoriales",editoriales);		
		return("registrarLibro");
	}
	
	@RequestMapping(value="/registroLibroCompletado")
	public String registroLibro(@RequestParam String nombre, Libro libro, Model model) {
		Editorial editorial = repEditorial.findByNombre(nombre);
		libro.setEditorial(editorial);
		repLibro.save(libro);
		
		editorial.getLibros().add(libro);
		repEditorial.save(editorial);
		return "libroRegistrado";
	}
	
	@RequestMapping(value="/irAContenidoLibro")
	public String mostrarLibro (@RequestParam long id, Model model) {
		
		Libro libro = repLibro.getOne(id); //Cojo el libro del repositorio de libros según su CLAVE PRIMARIA (id)
		model.addAttribute("libro", libro); //Lo añado al modelo
		
		return "contenidoLibro"; //Devuelvo la plantilla correspondiente con el libro
	}
	@RequestMapping(value="/irAModificarLibro")
	public String moLibro (@RequestParam long idLibro, Model model) {
		
		Libro libro = repLibro.getOne(idLibro); //Cojo el libro del repositorio de editoriales según su CLAVE PRIMARIA (id)
		model.addAttribute("libro", libro); //Lo añado al modelo
		
		return "modificarLibro"; //Devuelvo la plantilla correspondiente con el libro
	}
	@RequestMapping(value="/modificarLibroCompletado")
	public String modificarLibro(Libro libro,String campo, 
		@RequestParam(value="autores") String autores,
		@RequestParam(value="titulo") String titulo,
		@RequestParam(value="categoria") String categoria,
		@RequestParam(value="nPaginas") int nPaginas,
		@RequestParam(value="pvp") float pvp,
		@RequestParam(value="anyoPublicacion") int anyoPublicacion) {
		
		libro.setAutores(autores);
		libro.setTitulo(titulo);
		libro.setCategoria(categoria);
		libro.setnPaginas(nPaginas);
		libro.setPvp(pvp);
		libro.setAnyoPublicacion(anyoPublicacion);
		return "libroModificado";
		
	}
	@PostConstruct
	public void init() {
		Editorial plaza = new Editorial("Plaza",622754789,"plaza@plaza.es",45200,527896235);
		repEditorial.save(plaza);
		Editorial castro = new Editorial("Castro",62802348,"castroSm@castro.es",45200,582832265);
		repEditorial.save(castro);
		repLibro.save(new Libro("Paco León", "En busca del arca", "Aventura",122, 33, 2007, plaza));
		repLibro.save(new Libro("Marco Galán", "Sueños rotos", "Aventura",155, 32, 2004, castro));

	}
	

	
	
}