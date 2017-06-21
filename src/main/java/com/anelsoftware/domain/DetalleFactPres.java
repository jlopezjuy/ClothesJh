package com.anelsoftware.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DetalleFactPres.
 */
@Entity
@Table(name = "detalle_fact_pres")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DetalleFactPres extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "predio", precision=10, scale=2)
    private BigDecimal predio;

    @ManyToOne
    private FacturaPresupuesto facturaPresupuesto;

    @ManyToOne
    private Producto producto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public DetalleFactPres cantidad(Integer cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPredio() {
        return predio;
    }

    public DetalleFactPres predio(BigDecimal predio) {
        this.predio = predio;
        return this;
    }

    public void setPredio(BigDecimal predio) {
        this.predio = predio;
    }

    public FacturaPresupuesto getFacturaPresupuesto() {
        return facturaPresupuesto;
    }

    public DetalleFactPres facturaPresupuesto(FacturaPresupuesto facturaPresupuesto) {
        this.facturaPresupuesto = facturaPresupuesto;
        return this;
    }

    public void setFacturaPresupuesto(FacturaPresupuesto facturaPresupuesto) {
        this.facturaPresupuesto = facturaPresupuesto;
    }

    public Producto getProducto() {
        return producto;
    }

    public DetalleFactPres producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DetalleFactPres detalleFactPres = (DetalleFactPres) o;
        if (detalleFactPres.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), detalleFactPres.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DetalleFactPres{" +
            "id=" + getId() +
            ", cantidad='" + getCantidad() + "'" +
            ", predio='" + getPredio() + "'" +
            "}";
    }
}
