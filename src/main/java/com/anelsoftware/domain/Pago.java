package com.anelsoftware.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Class Pago.
 * @author anelsoftware
 */
@ApiModel(description = "Class Pago. @author anelsoftware")
@Entity
@Table(name = "pago")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pago")
public class Pago extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_pago")
    private LocalDate fechaPago;

    @NotNull
    @Column(name = "importe", nullable = false)
    private Double importe;

    @NotNull
    @Column(name = "detalle", nullable = false)
    private String detalle;

    @NotNull
    @Column(name = "numero_recibo", nullable = false)
    private Integer numeroRecibo;

    @ManyToOne(optional = false)
    @NotNull
    private Encargo encargo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public Pago fechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
        return this;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Double getImporte() {
        return importe;
    }

    public Pago importe(Double importe) {
        this.importe = importe;
        return this;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public String getDetalle() {
        return detalle;
    }

    public Pago detalle(String detalle) {
        this.detalle = detalle;
        return this;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Integer getNumeroRecibo() {
        return numeroRecibo;
    }

    public Pago numeroRecibo(Integer numeroRecibo) {
        this.numeroRecibo = numeroRecibo;
        return this;
    }

    public void setNumeroRecibo(Integer numeroRecibo) {
        this.numeroRecibo = numeroRecibo;
    }

    public Encargo getEncargo() {
        return encargo;
    }

    public Pago encargo(Encargo encargo) {
        this.encargo = encargo;
        return this;
    }

    public void setEncargo(Encargo encargo) {
        this.encargo = encargo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pago pago = (Pago) o;
        if (pago.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pago.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pago{" +
            "id=" + getId() +
            ", fechaPago='" + getFechaPago() + "'" +
            ", importe='" + getImporte() + "'" +
            ", detalle='" + getDetalle() + "'" +
            ", numeroRecibo='" + getNumeroRecibo() + "'" +
            "}";
    }
}
