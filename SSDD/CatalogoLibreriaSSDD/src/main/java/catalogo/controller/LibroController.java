package catalogo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import catalogo.repository.EditorialRepositorio;
import catalogo.repository.LibroRepositorio;


public class LibroController {


	@Autowired
	private EditorialRepositorio repEditorial;
	
	@Autowired
	private LibroRepositorio repLibro;
	
	/*controlador del catálogo que con localhost:8080 va a / que me lleva a la dinámica del catálogo*/
	@RequestMapping("/")
	public String tablon(Model model) {

		model.addAttribute("libro", repLibro.findAll());
		model.addAttribute("editorial",repEditorial.findAll());

		return "catalogo";
	}
<<<<<<< HEAD
	
	@RequestMapping("/nuevoLibro")
		public void insertar(Model model) {
		
	}
=======
	/*
	@RequestMapping("/nuevoLibro")
	public String insertar(Model model) {
>>>>>>> accfc9e98b50959f4bb86db13821c0553cd4115a
	
	
	
	}*/
}