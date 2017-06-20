package com.anelsoftware.service.mapper;

import com.anelsoftware.domain.*;
import com.anelsoftware.service.dto.FacturaPresupuestoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FacturaPresupuesto and its DTO FacturaPresupuestoDTO.
 */
@Mapper(componentModel = "spring", uses = {ClienteMapper.class, })
public interface FacturaPresupuestoMapper extends EntityMapper <FacturaPresupuestoDTO, FacturaPresupuesto> {

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "cliente.nombre", target = "clienteNombre")
    FacturaPresupuestoDTO toDto(FacturaPresupuesto facturaPresupuesto); 
    @Mapping(target = "detalleFactPres", ignore = true)

    @Mapping(source = "clienteId", target = "cliente")
    FacturaPresupuesto toEntity(FacturaPresupuestoDTO facturaPresupuestoDTO); 
    default FacturaPresupuesto fromId(Long id) {
        if (id == null) {
            return null;
        }
        FacturaPresupuesto facturaPresupuesto = new FacturaPresupuesto();
        facturaPresupuesto.setId(id);
        return facturaPresupuesto;
    }
}
