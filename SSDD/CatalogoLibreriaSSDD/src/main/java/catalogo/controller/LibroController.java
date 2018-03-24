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
	
	@RequestMapping("/mostrarLibros")
	public String mostrarLibros(Model model) {
		model.addAttribute("totalLibros", repLibro.findAll());
		return "mostrarLibros";
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
	
	@RequestMapping(value="/consultaLibro")
	public String consultaEditorial (@RequestParam String criterio, 
									@RequestParam String filtro, 
									Model model) {
		List<Libro> resConsulta = null;
		Libro resLibro = null;
		switch(criterio) {
			case "autores": 
				resConsulta = repLibro.findByAutores(filtro);
				model.addAttribute("resultadoConsulta", resConsulta);
				break;
			case "titulo": 
				resLibro = repLibro.findByTitulo(filtro);
				model.addAttribute("resultadoConsulta", resLibro);
				break;
			case "categoria": 
				resConsulta = repLibro.findByCategoria(filtro);
				model.addAttribute("resultadoConsulta", resConsulta);
				break;
			case "nPaginas": 
				resConsulta = repLibro.findByNPaginas(Integer.parseInt(filtro));
				model.addAttribute("resultadoConsulta", resConsulta);
				break;
			case "pvp": 
				resConsulta = repLibro.findByPvp(Long.parseLong(filtro));
				model.addAttribute("resultadoConsulta", resConsulta);
				break;
			case "anyoPublicacion": 
				resConsulta = repLibro.findByAnyoPublicacion(Integer.parseInt(filtro));
				model.addAttribute("resultadoConsulta", resConsulta);
				break;
		}
		model.addAttribute("titulo", "Resultados de la búsqueda");
		return "catalogoLibros";
	}
	@RequestMapping(value="/irAModificarLibro")
	public String moLibro (@RequestParam long id, Model model) {
		List<Editorial> editoriales = repEditorial.findAllByOrderByNombreAsc(); //cojo las editoriales para realizar el desplegable
		model.addAttribute("totalEditoriales",editoriales);	
		Libro libro = repLibro.getOne(id); //Cojo el libro del repositorio de editoriales según su CLAVE PRIMARIA (id)
		model.addAttribute("libro", libro); //Lo añado al modelo
		
		return "modificarLibro"; //Devuelvo la plantilla correspondiente con el libro
	}
	
	@RequestMapping(value="/modificarLibroCompletado")
	public String modificarLibro(@RequestParam(value="id") long id,	
		@RequestParam(value="autores") String autores,
		@RequestParam(value="titulo") String titulo,
		@RequestParam(value="categoria") String categoria,
		@RequestParam(value="nPaginas") int nPaginas,
		@RequestParam(value="pvp") float pvp,
		@RequestParam(value="anyoPublicacion") int anyoPublicacion,
		@RequestParam(value="nombre") String nombreEditorial) //cojo el nombre de la editorial de las opciones al modificarlo en Modificar Libro
		{
		Editorial editorial =repEditorial.findByNombre(nombreEditorial); //busco la editorial con ese nombre
		Libro libro = repLibro.getOne(id);
		
		Editorial antiguaEditorial = libro.getEditorial();	//Cogemos la antigua editorial del libro
		if (editorial != antiguaEditorial) {				//Si se ha modificado, quitamos del listado de libros de la antigua editorial el libro
			antiguaEditorial.getLibros().remove(libro);		
			repEditorial.save(antiguaEditorial);
		}
		
		libro.setAutores(autores);
		libro.setTitulo(titulo);
		libro.setCategoria(categoria);
		libro.setnPaginas(nPaginas);
		libro.setPvp(pvp);
		libro.setAnyoPublicacion(anyoPublicacion);
		libro.setEditorial(editorial);
		repEditorial.save(editorial); 	//Actualiza la editorial
		repLibro.save(libro);
		
		
		return "libroModificado";	
	}

	
	
	@RequestMapping(value="/ordenarLibros")
	public String ordenarLibros(@RequestParam String criterio,@RequestParam String orden, Model model) {
		List<Libro> resOrden = null;
		String criterioMostrar = null;
		switch(criterio) {
		case "autores":
			if(orden.equalsIgnoreCase("Ascendente")) {
				resOrden = repLibro.findAllByOrderByAutoresAsc();
			}else if(orden.equalsIgnoreCase("Descendente")){
				resOrden = repLibro.findAllByOrderByAutoresDesc();
			}
			criterioMostrar = "Autores";
			break;
		case "titulo":
			if(orden.equalsIgnoreCase("Ascendente")) {
				resOrden = repLibro.findAllByOrderByTituloAsc();
			}else if(orden.equalsIgnoreCase("Descendente")){
				resOrden = repLibro.findAllByOrderByTituloDesc();
			}
			criterioMostrar = "Titulo";
			break;
		case "categoria":
			if(orden.equalsIgnoreCase("Ascendente")) {
				resOrden = repLibro.findAllByOrderByCategoriaAsc();
			}else if(orden.equalsIgnoreCase("Descendente")){
				resOrden = repLibro.findAllByOrderByCategoriaDesc();
			}
			criterioMostrar = "Categoria";
			break;
		case "nPaginas":
			if(orden.equalsIgnoreCase("Ascendente")) {
				resOrden = repLibro.findAllByOrderByNPaginasAsc();
			}else if(orden.equalsIgnoreCase("Descendente")){
				resOrden = repLibro.findAllByOrderByNPaginasDesc();
			}
			criterioMostrar = "Número de Páginas";
			break;
		case "pvp":
			if(orden.equalsIgnoreCase("Ascendente")) {
				resOrden = repLibro.findAllByOrderByPvpAsc();
			}else if(orden.equalsIgnoreCase("Descendente")){
				resOrden = repLibro.findAllByOrderByPvpDesc();
			}
			criterioMostrar = "Precio";
			break;
		case "anyoPublicacion":
			if(orden.equalsIgnoreCase("Ascendente")) {
				resOrden = repLibro.findAllByOrderByAnyoPublicacionAsc();
			}else if(orden.equalsIgnoreCase("Descendente")){
				resOrden = repLibro.findAllByOrderByAnyoPublicacionDesc();
			}
			criterioMostrar = "Año de Publicación";
			break;
		}
		model.addAttribute("resultadoConsulta", resOrden);
		model.addAttribute("titulo", "Libros ordenadas por " + criterioMostrar + " " + orden);
		
		return "catalogoLibros";
		
	}

	@RequestMapping("/nuevoLibro")
	public String insertar(Model model) {	
		List<Editorial> editoriales = repEditorial.findAllByOrderByNombreAsc();
		model.addAttribute("totalEditoriales",editoriales);		
		return("registrarLibro");
	}
	
	@PostConstruct
	public void init() {
		Editorial plaza = new Editorial("Plaza",622754789,"plaza@plaza.es",45200,"B27896235");
		repEditorial.save(plaza);
		Editorial castro = new Editorial("Castro",62802348,"castroSm@castro.es",45210,"58283226A");
		repEditorial.save(castro);
		repLibro.save(new Libro("Paco León", "En busca del arca", "Aventura",122, 33, 2007, plaza));
		repLibro.save(new Libro("Marco Galán", "Sueños rotos", "Aventura",155, 32, 2004, castro));

	}
	

	
	
}