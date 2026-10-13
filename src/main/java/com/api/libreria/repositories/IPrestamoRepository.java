package com.api.libreria.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.libreria.models.PrestamoModel;

@Repository
public interface IPrestamoRepository extends CrudRepository<PrestamoModel, Long> {

}
