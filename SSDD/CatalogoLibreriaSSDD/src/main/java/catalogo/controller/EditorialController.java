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
	
	@Autowired
	private LibroRepositorio repLibro;
	
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
				break;
			case "email": 
				resConsulta = repEditorial.findByEmail(filtro);
				break;
			case "telefono": 
				resConsulta = repEditorial.findByTelefono(Long.parseLong(filtro));
				break;
			case "cPostal": 
				resConsulta = repEditorial.findByCPostal(Long.parseLong(filtro));
				break;
			case "nif": 
				resConsulta = repEditorial.findByNif(Long.parseLong(filtro));
				break;
		}
		
		model.addAttribute("resultadoConsulta", resConsulta);
		
		return "catalogoEditoriales";
		
	}
	
	@PostConstruct
	public void init() {
		repEditorial.save(new Editorial("Anaya",628015678,"anaya@anaya.es",45200,527897865));
		repEditorial.save(new Editorial("SM",628015678,"editorialSm@Sm.es",45200,582827865));

	}
	


}
