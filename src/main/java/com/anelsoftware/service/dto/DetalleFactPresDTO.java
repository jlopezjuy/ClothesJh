package com.anelsoftware.service.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the DetalleFactPres entity.
 */
public class DetalleFactPresDTO implements Serializable {

    private Long id;

    private Integer cantidad;

    private BigDecimal predio;

    private Long facturaPresupuestoId;

    private Long productoId;

    private String productoNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPredio() {
        return predio;
    }

    public void setPredio(BigDecimal predio) {
        this.predio = predio;
    }

    public Long getFacturaPresupuestoId() {
        return facturaPresupuestoId;
    }

    public void setFacturaPresupuestoId(Long facturaPresupuestoId) {
        this.facturaPresupuestoId = facturaPresupuestoId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getProductoNombre() {
        return productoNombre;
    }

    public void setProductoNombre(String productoNombre) {
        this.productoNombre = productoNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DetalleFactPresDTO detalleFactPresDTO = (DetalleFactPresDTO) o;
        if(detalleFactPresDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), detalleFactPresDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DetalleFactPresDTO{" +
            "id=" + getId() +
            ", cantidad='" + getCantidad() + "'" +
            ", predio='" + getPredio() + "'" +
            "}";
    }
}
