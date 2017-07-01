package com.anelsoftware.service.mapper;

import com.anelsoftware.domain.*;
import com.anelsoftware.service.dto.RubroDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Rubro and its DTO RubroDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RubroMapper extends EntityMapper <RubroDTO, Rubro> {
    
    @Mapping(target = "proveedors", ignore = true)
    Rubro toEntity(RubroDTO rubroDTO); 
    default Rubro fromId(Long id) {
        if (id == null) {
            return null;
        }
        Rubro rubro = new Rubro();
        rubro.setId(id);
        return rubro;
    }
}
