package com.api.libreria.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="libros")
public class LibroModel {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)    
private Long isbn;

@Column
@NotBlank(message = "El titulo es obligatorio")
private String titulo;

@Column
@NotBlank(message = "El autor es obligatorio")
private String autor;

@Column
private String editorial;

@Column
private String aPublicacion;

@Column
@Min(value = 0, message = "El numero de ejemplares no puede ser negativo")
private int noEjemplares;

public Long getIsbn() {
    return isbn;
}
public void setIsbn(Long isbn) {
    this.isbn = isbn;
}
public String getTitulo() {
    return titulo;
}
public void setTitulo(String titulo) {
    this.titulo = titulo;
}
public String getAutor() {
    return autor;
}
public void setAutor(String autor) {
    this.autor = autor;
}
public String getEditorial() {
    return editorial;
}
public void setEditorial(String editorial) {
    this.editorial = editorial;
}
public String getaPublicacion() {
    return aPublicacion;
}
public void setaPublicacion(String aPublicacion) {
    this.aPublicacion = aPublicacion;
}
public int getNoEjemplares() {
    return noEjemplares;
}
public void setNoEjemplares(int noEjemplares) {
    this.noEjemplares = noEjemplares;
}







}
