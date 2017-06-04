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
    @Mapping(target = "pagos", ignore = true)
    @Mapping(source = "clienteId", target = "cliente")
    Encargo toEntity(EncargoDTO encargoDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Encargo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Encargo encargo = new Encargo();
        encargo.setId(id);
        return encargo;
    }
}
