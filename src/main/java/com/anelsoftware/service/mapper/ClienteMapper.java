package com.anelsoftware.service.mapper;

import com.anelsoftware.domain.*;
import com.anelsoftware.service.dto.ClienteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cliente and its DTO ClienteDTO.
 */
@Mapper(componentModel = "spring", uses = {EmpresaMapper.class, })
public interface ClienteMapper extends EntityMapper <ClienteDTO, Cliente> {
    @Mapping(source = "empresa.id", target = "empresaId")
    @Mapping(source = "empresa.nombre", target = "empresaNombre")
    ClienteDTO toDto(Cliente cliente); 
    @Mapping(target = "modelos", ignore = true)
    @Mapping(target = "medidas", ignore = true)
    @Mapping(target = "encargos", ignore = true)
    @Mapping(source = "empresaId", target = "empresa")
    Cliente toEntity(ClienteDTO clienteDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Cliente fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cliente cliente = new Cliente();
        cliente.setId(id);
        return cliente;
    }
}
