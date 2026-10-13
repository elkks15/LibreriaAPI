package com.api.libreria.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.libreria.models.LibroModel;

@Repository
public interface ILibroRepository extends CrudRepository<LibroModel, Long> {

}
