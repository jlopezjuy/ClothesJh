package com.anelsoftware.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Modelo entity.
 */
public class ModeloDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    @Lob
    private byte[] imagen;
    private String imagenContentType;

    @NotNull
    private String nombreModelo;

    @NotNull
    private String colorVestido;

    private Boolean bordado;

    private String descripcion;

    private String observacion;

    private Long encargoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getNombreModelo() {
        return nombreModelo;
    }

    public void setNombreModelo(String nombreModelo) {
        this.nombreModelo = nombreModelo;
    }

    public String getColorVestido() {
        return colorVestido;
    }

    public void setColorVestido(String colorVestido) {
        this.colorVestido = colorVestido;
    }

    public Boolean isBordado() {
        return bordado;
    }

    public void setBordado(Boolean bordado) {
        this.bordado = bordado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

        ModeloDTO modeloDTO = (ModeloDTO) o;
        if(modeloDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), modeloDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ModeloDTO{" +
            "id=" + getId() +
            ", imagen='" + getImagen() + "'" +
            ", nombreModelo='" + getNombreModelo() + "'" +
            ", colorVestido='" + getColorVestido() + "'" +
            ", bordado='" + isBordado() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", observacion='" + getObservacion() + "'" +
            "}";
    }
}
