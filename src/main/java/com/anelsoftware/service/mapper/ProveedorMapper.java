package com.anelsoftware.service.mapper;

import com.anelsoftware.domain.*;
import com.anelsoftware.service.dto.ProveedorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Proveedor and its DTO ProveedorDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductoMapper.class, })
public interface ProveedorMapper extends EntityMapper <ProveedorDTO, Proveedor> {

    @Mapping(source = "producto.id", target = "productoId")
    ProveedorDTO toDto(Proveedor proveedor); 

    @Mapping(source = "productoId", target = "producto")
    Proveedor toEntity(ProveedorDTO proveedorDTO); 
    default Proveedor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Proveedor proveedor = new Proveedor();
        proveedor.setId(id);
        return proveedor;
    }
}
