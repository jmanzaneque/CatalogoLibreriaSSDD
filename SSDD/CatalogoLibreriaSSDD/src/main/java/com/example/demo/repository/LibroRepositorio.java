package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Libro;

public interface LibroRepositorio extends JpaRepository<Libro, Long> {

}
