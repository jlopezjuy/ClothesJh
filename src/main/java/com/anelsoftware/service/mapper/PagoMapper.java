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
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Pago fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pago pago = new Pago();
        pago.setId(id);
        return pago;
    }
}
