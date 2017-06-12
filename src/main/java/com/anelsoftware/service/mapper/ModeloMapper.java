package com.anelsoftware.service.mapper;

import com.anelsoftware.domain.*;
import com.anelsoftware.service.dto.ModeloDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Modelo and its DTO ModeloDTO.
 */
@Mapper(componentModel = "spring", uses = {EncargoMapper.class, })
public interface ModeloMapper extends EntityMapper <ModeloDTO, Modelo> {

    @Mapping(source = "encargo.id", target = "encargoId")
    ModeloDTO toDto(Modelo modelo); 

    @Mapping(source = "encargoId", target = "encargo")
    Modelo toEntity(ModeloDTO modeloDTO); 
    default Modelo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Modelo modelo = new Modelo();
        modelo.setId(id);
        return modelo;
    }
}
