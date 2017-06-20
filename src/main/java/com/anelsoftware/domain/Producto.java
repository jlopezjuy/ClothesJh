package com.anelsoftware.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.anelsoftware.domain.enumeration.Talla;

import com.anelsoftware.domain.enumeration.TipoProducto;

import com.anelsoftware.domain.enumeration.Ubicacion;

/**
 * A Producto.
 */
@Entity
@Table(name = "producto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @NotNull
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "codigo_origen")
    private String codigoOrigen;

    @NotNull
    @Column(name = "precio_compra", precision=10, scale=2, nullable = false)
    private BigDecimal precioCompra;

    @NotNull
    @Column(name = "precio_venta", precision=10, scale=2, nullable = false)
    private BigDecimal precioVenta;

    @Column(name = "margen_ganancia", precision=10, scale=2)
    private BigDecimal margenGanancia;

    @Enumerated(EnumType.STRING)
    @Column(name = "talle")
    private Talla talle;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private TipoProducto categoria;

    @Enumerated(EnumType.STRING)
    @Column(name = "ubicacion")
    private Ubicacion ubicacion;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    @Column(name = "imagen_content_type")
    private String imagenContentType;

    @OneToMany(mappedBy = "producto")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DetalleFactPres> detalleFactPres = new HashSet<>();

    @OneToMany(mappedBy = "producto")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Proveedor> proveedors = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Producto nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Producto descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public Producto cantidad(Integer cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getCodigoOrigen() {
        return codigoOrigen;
    }

    public Producto codigoOrigen(String codigoOrigen) {
        this.codigoOrigen = codigoOrigen;
        return this;
    }

    public void setCodigoOrigen(String codigoOrigen) {
        this.codigoOrigen = codigoOrigen;
    }

    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }

    public Producto precioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
        return this;
    }

    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public Producto precioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
        return this;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public BigDecimal getMargenGanancia() {
        return margenGanancia;
    }

    public Producto margenGanancia(BigDecimal margenGanancia) {
        this.margenGanancia = margenGanancia;
        return this;
    }

    public void setMargenGanancia(BigDecimal margenGanancia) {
        this.margenGanancia = margenGanancia;
    }

    public Talla getTalle() {
        return talle;
    }

    public Producto talle(Talla talle) {
        this.talle = talle;
        return this;
    }

    public void setTalle(Talla talle) {
        this.talle = talle;
    }

    public TipoProducto getCategoria() {
        return categoria;
    }

    public Producto categoria(TipoProducto categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(TipoProducto categoria) {
        this.categoria = categoria;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public Producto ubicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
        return this;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public Producto imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public Producto imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public Set<DetalleFactPres> getDetalleFactPres() {
        return detalleFactPres;
    }

    public Producto detalleFactPres(Set<DetalleFactPres> detalleFactPres) {
        this.detalleFactPres = detalleFactPres;
        return this;
    }

    public Producto addDetalleFactPres(DetalleFactPres detalleFactPres) {
        this.detalleFactPres.add(detalleFactPres);
        detalleFactPres.setProducto(this);
        return this;
    }

    public Producto removeDetalleFactPres(DetalleFactPres detalleFactPres) {
        this.detalleFactPres.remove(detalleFactPres);
        detalleFactPres.setProducto(null);
        return this;
    }

    public void setDetalleFactPres(Set<DetalleFactPres> detalleFactPres) {
        this.detalleFactPres = detalleFactPres;
    }

    public Set<Proveedor> getProveedors() {
        return proveedors;
    }

    public Producto proveedors(Set<Proveedor> proveedors) {
        this.proveedors = proveedors;
        return this;
    }

    public Producto addProveedor(Proveedor proveedor) {
        this.proveedors.add(proveedor);
        proveedor.setProducto(this);
        return this;
    }

    public Producto removeProveedor(Proveedor proveedor) {
        this.proveedors.remove(proveedor);
        proveedor.setProducto(null);
        return this;
    }

    public void setProveedors(Set<Proveedor> proveedors) {
        this.proveedors = proveedors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Producto producto = (Producto) o;
        if (producto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), producto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Producto{" +
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
            ", imagenContentType='" + imagenContentType + "'" +
            "}";
    }
}
