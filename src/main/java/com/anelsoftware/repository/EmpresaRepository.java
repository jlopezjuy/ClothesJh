package com.anelsoftware.repository;

import com.anelsoftware.domain.Empresa;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Empresa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmpresaRepository extends JpaRepository<Empresa,Long> {

    @Query("select empresa from Empresa empresa where empresa.user.login = ?#{principal.username}")
    List<Empresa> findByUserIsCurrentUser();

}
