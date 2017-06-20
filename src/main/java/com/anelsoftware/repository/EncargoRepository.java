package com.anelsoftware.repository;

import com.anelsoftware.domain.Encargo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Encargo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EncargoRepository extends JpaRepository<Encargo,Long> {
    
}
