package com.anelsoftware.service.mapper;

import com.anelsoftware.domain.*;
import com.anelsoftware.service.dto.DetalleFactPresDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DetalleFactPres and its DTO DetalleFactPresDTO.
 */
@Mapper(componentModel = "spring", uses = {FacturaPresupuestoMapper.class, ProductoMapper.class, })
public interface DetalleFactPresMapper extends EntityMapper <DetalleFactPresDTO, DetalleFactPres> {

    @Mapping(source = "facturaPresupuesto.id", target = "facturaPresupuestoId")

    @Mapping(source = "producto.id", target = "productoId")
    DetalleFactPresDTO toDto(DetalleFactPres detalleFactPres); 

    @Mapping(source = "facturaPresupuestoId", target = "facturaPresupuesto")

    @Mapping(source = "productoId", target = "producto")
    DetalleFactPres toEntity(DetalleFactPresDTO detalleFactPresDTO); 
    default DetalleFactPres fromId(Long id) {
        if (id == null) {
            return null;
        }
        DetalleFactPres detalleFactPres = new DetalleFactPres();
        detalleFactPres.setId(id);
        return detalleFactPres;
    }
}
