package com.anelsoftware.service.mapper;

import com.anelsoftware.domain.*;
import com.anelsoftware.service.dto.ModeloEncargoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ModeloEncargo and its DTO ModeloEncargoDTO.
 */
@Mapper(componentModel = "spring", uses = {EncargoMapper.class, })
public interface ModeloEncargoMapper extends EntityMapper <ModeloEncargoDTO, ModeloEncargo> {

    @Mapping(source = "encargo.id", target = "encargoId")
    ModeloEncargoDTO toDto(ModeloEncargo modeloEncargo); 

    @Mapping(source = "encargoId", target = "encargo")
    ModeloEncargo toEntity(ModeloEncargoDTO modeloEncargoDTO); 
    default ModeloEncargo fromId(Long id) {
        if (id == null) {
            return null;
        }
        ModeloEncargo modeloEncargo = new ModeloEncargo();
        modeloEncargo.setId(id);
        return modeloEncargo;
    }
}
