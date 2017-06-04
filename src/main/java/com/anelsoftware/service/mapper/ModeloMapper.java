package com.anelsoftware.service.mapper;

import com.anelsoftware.domain.*;
import com.anelsoftware.service.dto.ModeloDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Modelo and its DTO ModeloDTO.
 */
@Mapper(componentModel = "spring", uses = {ClienteMapper.class, })
public interface ModeloMapper extends EntityMapper <ModeloDTO, Modelo> {
    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "cliente.nombre", target = "clienteNombre")
    ModeloDTO toDto(Modelo modelo); 
    @Mapping(source = "clienteId", target = "cliente")
    Modelo toEntity(ModeloDTO modeloDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Modelo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Modelo modelo = new Modelo();
        modelo.setId(id);
        return modelo;
    }
}
