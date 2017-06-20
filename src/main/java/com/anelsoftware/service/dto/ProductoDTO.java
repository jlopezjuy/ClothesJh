package com.anelsoftware.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import com.anelsoftware.domain.enumeration.Talla;
import com.anelsoftware.domain.enumeration.TipoProducto;
import com.anelsoftware.domain.enumeration.Ubicacion;

/**
 * A DTO for the Producto entity.
 */
public class ProductoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    private String descripcion;

    @NotNull
    private Integer cantidad;

    private String codigoOrigen;

    @NotNull
    private BigDecimal precioCompra;

    @NotNull
    private BigDecimal precioVenta;

    private BigDecimal margenGanancia;

    private Talla talle;

    @NotNull
    private TipoProducto categoria;

    private Ubicacion ubicacion;

    @Lob
    private byte[] imagen;
    private String imagenContentType;

    private Long proveedorId;

    private String proveedorNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getCodigoOrigen() {
        return codigoOrigen;
    }

    public void setCodigoOrigen(String codigoOrigen) {
        this.codigoOrigen = codigoOrigen;
    }

    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public BigDecimal getMargenGanancia() {
        return margenGanancia;
    }

    public void setMargenGanancia(BigDecimal margenGanancia) {
        this.margenGanancia = margenGanancia;
    }

    public Talla getTalle() {
        return talle;
    }

    public void setTalle(Talla talle) {
        this.talle = talle;
    }

    public TipoProducto getCategoria() {
        return categoria;
    }

    public void setCategoria(TipoProducto categoria) {
        this.categoria = categoria;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public Long getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Long proveedorId) {
        this.proveedorId = proveedorId;
    }

    public String getProveedorNombre() {
        return proveedorNombre;
    }

    public void setProveedorNombre(String proveedorNombre) {
        this.proveedorNombre = proveedorNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductoDTO productoDTO = (ProductoDTO) o;
        if(productoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductoDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", cantidad='" + getCantidad() + "'" +
            ", codigoOrigen='" + getCodigoOrigen() + "'" +
            ", precioCompra='" + getPrecioCompra() + "'" +
            ", precioVenta='" + getPrecioVenta() + "'" +
            ", margenGanancia='" + getMargenGanancia() + "'" +
            ", talle='" + getTalle() + "'" +
            ", categoria='" + getCategoria() + "'" +
            ", ubicacion='" + getUbicacion() + "'" +
            ", imagen='" + getImagen() + "'" +
            "}";
    }
}
