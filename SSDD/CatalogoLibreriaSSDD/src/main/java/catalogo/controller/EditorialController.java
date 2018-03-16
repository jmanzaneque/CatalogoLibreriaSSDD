package catalogo.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import catalogo.model.Editorial;
import catalogo.repository.EditorialRepositorio;
import catalogo.repository.LibroRepositorio;

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
	
	@RequestMapping(value="/irAContenidoEditorial")
	public String mostrarEditorial (@RequestParam long idEditorial, Model model) {
		
		Editorial editorial = repEditorial.getOne(idEditorial); //Cojo la editorial del repositorio de editoriales según su CLAVE PRIMARIA (id)
		model.addAttribute("editorial", editorial); //Lo añado al modelo
		
		return "contenidoEditorial"; //Devuelvo la plantilla correspondiente con la editorial
	}
	
	@RequestMapping(value="/consultaEditorial")
	public String consultaEditorial (@RequestParam String criterio, 
									@RequestParam String filtro, 
									Model model) {
		
		List<Editorial> resConsulta = null;
		Editorial resEditorial = null;
		
		switch(criterio) {
			case "nombre": 
				resEditorial = repEditorial.findByNombre(filtro);
				model.addAttribute("resultadoConsulta", resEditorial);
				break;
			case "email": 
				resEditorial = repEditorial.findByEmail(filtro);
				model.addAttribute("resultadoConsulta", resEditorial);
				break;
			case "telefono": 
				resEditorial = repEditorial.findByTelefono(Long.parseLong(filtro));
				model.addAttribute("resultadoConsulta", resEditorial);
				break;
			case "cPostal": 
				resConsulta = repEditorial.findByCPostal(Long.parseLong(filtro));
				model.addAttribute("resultadoConsulta", resConsulta);
				break;
			case "nif": 
				resEditorial = repEditorial.findByNif(Long.parseLong(filtro));
				model.addAttribute("resultadoConsulta", resEditorial);
				break;
		}
		
		model.addAttribute("titulo", "Resultados de la búsqueda");
			
		return "catalogoEditoriales";
		
	}
	

	@RequestMapping(value="/irAModificarEditorial")
	public String moEditorial (@RequestParam long idEditorial, Model model) {
		
		Editorial editorial = repEditorial.getOne(idEditorial); //Cojo la editorial del repositorio de editoriales según su CLAVE PRIMARIA (id)
		model.addAttribute("editorial", editorial); //Lo añado al modelo
		
		return "modificarEditorial"; //Devuelvo la plantilla correspondiente con la editorial
	}

	@RequestMapping(value="/modificarEditorialCompletado")
	public String modificarEditorial(@RequestParam(value="idEditorial") long idEditorial,	 
		@RequestParam(value="nombre") String nombre,
		@RequestParam(value="telefono") long telefono,
		@RequestParam(value="email") String email,
		@RequestParam(value="cPostal") long cPostal,
		@RequestParam(value="nif") long nif) {
		
		Editorial editorial = repEditorial.getOne(idEditorial);

		editorial.setNombre(nombre);
		editorial.setTelefono(telefono);
		editorial.setEmail(email);
		editorial.setcPostal(cPostal);
		editorial.setNif(nif);
		
		repEditorial.save(editorial);
		return "editorialModificada";
		
	}

	@RequestMapping(value="/ordenarEditoriales")
	public String ordenaEditoriales(@RequestParam String criterio, Model model) {
		List<Editorial> resOrden = null;
		String criterioMostrar = null;
		switch(criterio) {
		case "nombre":
			resOrden = repEditorial.findAllByOrderByNombreAsc();
			criterioMostrar = criterio;
			break;
		case "email":
			resOrden = repEditorial.findAllByOrderByEmailAsc();
			criterioMostrar = "e-mail";
			break;
		case "telefono":
			resOrden = repEditorial.findAllByOrderByTelefonoAsc();
			criterioMostrar = "teléfono";
			break;
		case "cPostal":
			resOrden = repEditorial.findAllByOrderByCPostalAsc();
			criterioMostrar = "Código Postal";
			break;
		case "nif":
			resOrden = repEditorial.findAllByOrderByNifAsc();
			criterioMostrar = "NIF";
			break;

		}
		
		model.addAttribute("resultadoConsulta", resOrden);
		model.addAttribute("titulo", "Editoriales ordenadas por " + criterioMostrar);
		
		return "catalogoEditoriales";
	}
	
	
	@RequestMapping(value="/añadirLibroEditorial")
	public void añadirLibroEditorial(Editorial editorial,@RequestParam String campo, String mod) {
		
	}
	
	
	@PostConstruct
	public void init() {
		repEditorial.save(new Editorial("Anaya",628015678,"anaya@anaya.es",45200,527897865));
		repEditorial.save(new Editorial("SM",628015678,"editorialSm@Sm.es",45200,582827865));

	}
	


}
