package com.anelsoftware.repository;

import com.anelsoftware.domain.Rubro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Rubro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RubroRepository extends JpaRepository<Rubro,Long> {

    @Query("select u from Rubro u order by u.nombre")
    Page<Rubro> findAllOrderByNombre(Pageable pageable);
}
