package com.anelsoftware.repository;

import com.anelsoftware.domain.Encargo;
import com.anelsoftware.domain.ModeloEncargo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ModeloEncargo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModeloEncargoRepository extends JpaRepository<ModeloEncargo,Long> {

    Page<ModeloEncargo> findAllByEncargo(Pageable pageable, Encargo encargo);
}
