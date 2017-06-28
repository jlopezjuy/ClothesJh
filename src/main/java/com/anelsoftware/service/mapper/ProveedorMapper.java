package com.anelsoftware.service.mapper;

import com.anelsoftware.domain.*;
import com.anelsoftware.service.dto.ProveedorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Proveedor and its DTO ProveedorDTO.
 */
@Mapper(componentModel = "spring", uses = {RubroMapper.class, })
public interface ProveedorMapper extends EntityMapper <ProveedorDTO, Proveedor> {

    @Mapping(source = "rubro.id", target = "rubroId")
    @Mapping(source = "rubro.nombre", target = "rubroNombre")
    ProveedorDTO toDto(Proveedor proveedor); 
    @Mapping(target = "productos", ignore = true)

    @Mapping(source = "rubroId", target = "rubro")
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
