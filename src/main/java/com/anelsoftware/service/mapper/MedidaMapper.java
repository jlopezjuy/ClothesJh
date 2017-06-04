package com.anelsoftware.service.mapper;

import com.anelsoftware.domain.*;
import com.anelsoftware.service.dto.MedidaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Medida and its DTO MedidaDTO.
 */
@Mapper(componentModel = "spring", uses = {ClienteMapper.class, })
public interface MedidaMapper extends EntityMapper <MedidaDTO, Medida> {
    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "cliente.nombre", target = "clienteNombre")
    MedidaDTO toDto(Medida medida); 
    @Mapping(source = "clienteId", target = "cliente")
    Medida toEntity(MedidaDTO medidaDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Medida fromId(Long id) {
        if (id == null) {
            return null;
        }
        Medida medida = new Medida();
        medida.setId(id);
        return medida;
    }
}
