package com.anelsoftware.service.mapper;

import com.anelsoftware.domain.*;
import com.anelsoftware.service.dto.ProveedorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Proveedor and its DTO ProveedorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProveedorMapper extends EntityMapper <ProveedorDTO, Proveedor> {
    
    @Mapping(target = "productos", ignore = true)
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
