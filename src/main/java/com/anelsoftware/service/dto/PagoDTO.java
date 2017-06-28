package com.anelsoftware.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Pago entity.
 */
public class PagoDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private LocalDate fechaPago;

    @NotNull
    private Double importe;

    @NotNull
    private String detalle;

    @NotNull
    private Integer numeroRecibo;

    private Long encargoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Integer getNumeroRecibo() {
        return numeroRecibo;
    }

    public void setNumeroRecibo(Integer numeroRecibo) {
        this.numeroRecibo = numeroRecibo;
    }

    public Long getEncargoId() {
        return encargoId;
    }

    public void setEncargoId(Long encargoId) {
        this.encargoId = encargoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PagoDTO pagoDTO = (PagoDTO) o;
        if(pagoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pagoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PagoDTO{" +
            "id=" + getId() +
            ", fechaPago='" + getFechaPago() + "'" +
            ", importe='" + getImporte() + "'" +
            ", detalle='" + getDetalle() + "'" +
            ", numeroRecibo='" + getNumeroRecibo() + "'" +
            "}";
    }
}
