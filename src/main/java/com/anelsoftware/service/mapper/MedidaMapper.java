package com.anelsoftware.service.mapper;

import com.anelsoftware.domain.*;
import com.anelsoftware.service.dto.MedidaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Medida and its DTO MedidaDTO.
 */
@Mapper(componentModel = "spring", uses = {EncargoMapper.class, })
public interface MedidaMapper extends EntityMapper <MedidaDTO, Medida> {

    @Mapping(source = "encargo.id", target = "encargoId")
    MedidaDTO toDto(Medida medida); 

    @Mapping(source = "encargoId", target = "encargo")
    Medida toEntity(MedidaDTO medidaDTO); 
    default Medida fromId(Long id) {
        if (id == null) {
            return null;
        }
        Medida medida = new Medida();
        medida.setId(id);
        return medida;
    }
}
