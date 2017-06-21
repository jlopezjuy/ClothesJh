package com.anelsoftware.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.anelsoftware.domain.enumeration.FormaPago;

/**
 * A FacturaPresupuesto.
 */
@Entity
@Table(name = "factura_presupuesto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FacturaPresupuesto extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pago", nullable = false)
    private FormaPago formaPago;

    @Column(name = "importe_total", precision=10, scale=2)
    private BigDecimal importeTotal;

    @OneToMany(mappedBy = "facturaPresupuesto")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DetalleFactPres> detalleFactPres = new HashSet<>();

    @ManyToOne
    private Cliente cliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public FacturaPresupuesto fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public FacturaPresupuesto formaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
        return this;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public FacturaPresupuesto importeTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
        return this;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }

    public Set<DetalleFactPres> getDetalleFactPres() {
        return detalleFactPres;
    }

    public FacturaPresupuesto detalleFactPres(Set<DetalleFactPres> detalleFactPres) {
        this.detalleFactPres = detalleFactPres;
        return this;
    }

    public FacturaPresupuesto addDetalleFactPres(DetalleFactPres detalleFactPres) {
        this.detalleFactPres.add(detalleFactPres);
        detalleFactPres.setFacturaPresupuesto(this);
        return this;
    }

    public FacturaPresupuesto removeDetalleFactPres(DetalleFactPres detalleFactPres) {
        this.detalleFactPres.remove(detalleFactPres);
        detalleFactPres.setFacturaPresupuesto(null);
        return this;
    }

    public void setDetalleFactPres(Set<DetalleFactPres> detalleFactPres) {
        this.detalleFactPres = detalleFactPres;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public FacturaPresupuesto cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FacturaPresupuesto facturaPresupuesto = (FacturaPresupuesto) o;
        if (facturaPresupuesto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), facturaPresupuesto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FacturaPresupuesto{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", formaPago='" + getFormaPago() + "'" +
            ", importeTotal='" + getImporteTotal() + "'" +
            "}";
    }
}
