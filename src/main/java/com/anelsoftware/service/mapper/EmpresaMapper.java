package com.anelsoftware.service.mapper;

import com.anelsoftware.domain.*;
import com.anelsoftware.service.dto.EmpresaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Empresa and its DTO EmpresaDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface EmpresaMapper extends EntityMapper <EmpresaDTO, Empresa> {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.email", target = "userEmail")
    EmpresaDTO toDto(Empresa empresa); 
    @Mapping(target = "clientes", ignore = true)
    @Mapping(source = "userId", target = "user")
    Empresa toEntity(EmpresaDTO empresaDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Empresa fromId(Long id) {
        if (id == null) {
            return null;
        }
        Empresa empresa = new Empresa();
        empresa.setId(id);
        return empresa;
    }
}
