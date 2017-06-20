package com.anelsoftware.service.mapper;

import com.anelsoftware.domain.*;
import com.anelsoftware.service.dto.ProductoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Producto and its DTO ProductoDTO.
 */
@Mapper(componentModel = "spring", uses = {ProveedorMapper.class, })
public interface ProductoMapper extends EntityMapper <ProductoDTO, Producto> {

    @Mapping(source = "proveedor.id", target = "proveedorId")
    @Mapping(source = "proveedor.nombre", target = "proveedorNombre")
    ProductoDTO toDto(Producto producto); 
    @Mapping(target = "detalleFactPres", ignore = true)

    @Mapping(source = "proveedorId", target = "proveedor")
    Producto toEntity(ProductoDTO productoDTO); 
    default Producto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Producto producto = new Producto();
        producto.setId(id);
        return producto;
    }
}
