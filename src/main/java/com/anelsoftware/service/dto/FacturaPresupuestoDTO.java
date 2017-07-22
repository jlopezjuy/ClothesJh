package com.anelsoftware.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.anelsoftware.domain.enumeration.FormaPago;

/**
 * A DTO for the FacturaPresupuesto entity.
 */
public class FacturaPresupuestoDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate fecha;

    @NotNull
    private FormaPago formaPago;

    private BigDecimal importeTotal;

    private Long clienteId;

    private String clienteNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FacturaPresupuestoDTO facturaPresupuestoDTO = (FacturaPresupuestoDTO) o;
        if(facturaPresupuestoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), facturaPresupuestoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FacturaPresupuestoDTO{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", formaPago='" + getFormaPago() + "'" +
            ", importeTotal='" + getImporteTotal() + "'" +
            "}";
    }
}
