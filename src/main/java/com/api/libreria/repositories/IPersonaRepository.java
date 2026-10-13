package com.api.libreria.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.libreria.models.PersonaModel;

@Repository
public interface IPersonaRepository extends CrudRepository<PersonaModel, Long> {
    
}
