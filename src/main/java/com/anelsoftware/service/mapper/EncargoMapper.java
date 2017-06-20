package com.anelsoftware.service.mapper;

import com.anelsoftware.domain.*;
import com.anelsoftware.service.dto.EncargoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Encargo and its DTO EncargoDTO.
 */
@Mapper(componentModel = "spring", uses = {ClienteMapper.class, })
public interface EncargoMapper extends EntityMapper <EncargoDTO, Encargo> {

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "cliente.nombre", target = "clienteNombre")
    EncargoDTO toDto(Encargo encargo); 
    @Mapping(target = "medidas", ignore = true)
    @Mapping(target = "pagos", ignore = true)
    @Mapping(target = "modelos", ignore = true)

    @Mapping(source = "clienteId", target = "cliente")
    Encargo toEntity(EncargoDTO encargoDTO); 
    default Encargo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Encargo encargo = new Encargo();
        encargo.setId(id);
        return encargo;
    }
}
