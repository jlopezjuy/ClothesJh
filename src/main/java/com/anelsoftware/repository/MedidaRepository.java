package com.anelsoftware.repository;

import com.anelsoftware.domain.Cliente;
import com.anelsoftware.domain.Medida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Medida entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedidaRepository extends JpaRepository<Medida,Long> {

    Page<Medida> findAllByClienteId(Pageable pageable, Long clienteId);

    Page<Medida> findAllByCliente(Pageable pageable, Cliente cliente);
}
