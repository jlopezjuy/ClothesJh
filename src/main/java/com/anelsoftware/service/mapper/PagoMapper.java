package com.anelsoftware.service.mapper;

import com.anelsoftware.domain.*;
import com.anelsoftware.service.dto.PagoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Pago and its DTO PagoDTO.
 */
@Mapper(componentModel = "spring", uses = {EncargoMapper.class, })
public interface PagoMapper extends EntityMapper <PagoDTO, Pago> {

    @Mapping(source = "encargo.id", target = "encargoId")
    PagoDTO toDto(Pago pago); 

    @Mapping(source = "encargoId", target = "encargo")
    Pago toEntity(PagoDTO pagoDTO); 
    default Pago fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pago pago = new Pago();
        pago.setId(id);
        return pago;
    }
}
