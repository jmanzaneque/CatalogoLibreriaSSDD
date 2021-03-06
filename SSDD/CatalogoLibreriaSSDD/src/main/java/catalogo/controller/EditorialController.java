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
			@RequestParam(value="nif") String nif,
			Model model) {
		
		Editorial newEditorial = new Editorial(nombre, telefono, email, cPostal, nif);
		repEditorial.save(newEditorial);
		model.addAttribute("repEditoriales", repEditorial);
		return "editorialRegistrada";
	}
	@RequestMapping("/mostrarEditoriales")
	public String mostrarEditoriales(Model model) {

		model.addAttribute("totalEditoriales",repEditorial.findAll());
		return "mostrarEditoriales";
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
		
		List<Editorial> resConsulta = null;	//Para consultas que devuelven varias editoriales
		Editorial resEditorial = null;		//Para consultas que devuelven una única editorial
		
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
		@RequestParam(value="nif") String nif) {
		
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
	public String ordenaEditoriales(@RequestParam String criterio,@RequestParam String orden, Model model) {
		List<Editorial> resOrden = null;
		String criterioMostrar = null;
		switch(criterio) {
		case "nombre":
			if(orden.equalsIgnoreCase("Ascendente")) {
				resOrden = repEditorial.findAllByOrderByNombreAsc();
			}else if(orden.equalsIgnoreCase("Descendente")){
				resOrden = repEditorial.findAllByOrderByNombreDesc();
				
			}
			criterioMostrar = "Nombre";
			break;
		case "email":
			if(orden.equalsIgnoreCase("Ascendente")) {
				resOrden = repEditorial.findAllByOrderByEmailAsc();
			}else if(orden.equalsIgnoreCase("Descendente")){
				resOrden = repEditorial.findAllByOrderByCPostalDesc();
			}
			criterioMostrar = "E-mail";
			break;
		case "telefono":
			if(orden.equalsIgnoreCase("Ascendente")) {
				resOrden = repEditorial.findAllByOrderByTelefonoAsc();
			}else if(orden.equalsIgnoreCase("Descendente")){
				resOrden = repEditorial.findAllByOrderByTelefonoDesc();
			}
			criterioMostrar = "Teléfono";
			break;
		case "cPostal":
			if(orden.equalsIgnoreCase("Ascendente")) {
				resOrden = repEditorial.findAllByOrderByCPostalAsc();
			}else if(orden.equalsIgnoreCase("Descendente")){
				resOrden = repEditorial.findAllByOrderByCPostalDesc();;
			}
			criterioMostrar = "Código Postal";
			break;
		case "nif":
			if(orden.equalsIgnoreCase("Ascendente")) {
				resOrden = repEditorial.findAllByOrderByNifAsc();
			}else if(orden.equalsIgnoreCase("Descendente")){
				resOrden = repEditorial.findAllByOrderByNifDesc();
			}
			criterioMostrar = "NIF";
			break;

		}
		
		model.addAttribute("resultadoConsulta", resOrden);
		model.addAttribute("titulo", "Editoriales ordenadas por " + criterioMostrar + " " + orden);
		
		return "catalogoEditoriales";
	}
	
	
	@RequestMapping(value="/añadirLibroEditorial")
	public void añadirLibroEditorial(Editorial editorial,@RequestParam String campo, String mod) {
		
	}
	
	
	


}
