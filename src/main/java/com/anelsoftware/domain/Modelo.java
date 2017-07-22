package com.anelsoftware.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Class Modelo.
 * @author anelsoftware
 */
@ApiModel(description = "Class Modelo. @author anelsoftware")
@Entity
@Table(name = "modelo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "modelo")
public class Modelo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Lob
    @Column(name = "imagen", nullable = false)
    private byte[] imagen;

    @Column(name = "imagen_content_type", nullable = false)
    private String imagenContentType;

    @NotNull
    @Column(name = "nombre_modelo", nullable = false)
    private String nombreModelo;

    @NotNull
    @Column(name = "color_vestido", nullable = false)
    private String colorVestido;

    @Column(name = "bordado")
    private Boolean bordado;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "observacion")
    private String observacion;

    @ManyToOne(optional = false)
    @NotNull
    private Encargo encargo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public Modelo imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public Modelo imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public String getNombreModelo() {
        return nombreModelo;
    }

    public Modelo nombreModelo(String nombreModelo) {
        this.nombreModelo = nombreModelo;
        return this;
    }

    public void setNombreModelo(String nombreModelo) {
        this.nombreModelo = nombreModelo;
    }

    public String getColorVestido() {
        return colorVestido;
    }

    public Modelo colorVestido(String colorVestido) {
        this.colorVestido = colorVestido;
        return this;
    }

    public void setColorVestido(String colorVestido) {
        this.colorVestido = colorVestido;
    }

    public Boolean isBordado() {
        return bordado;
    }

    public Modelo bordado(Boolean bordado) {
        this.bordado = bordado;
        return this;
    }

    public void setBordado(Boolean bordado) {
        this.bordado = bordado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Modelo descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservacion() {
        return observacion;
    }

    public Modelo observacion(String observacion) {
        this.observacion = observacion;
        return this;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Encargo getEncargo() {
        return encargo;
    }

    public Modelo encargo(Encargo encargo) {
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
        Modelo modelo = (Modelo) o;
        if (modelo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), modelo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Modelo{" +
            "id=" + getId() +
            ", imagen='" + getImagen() + "'" +
            ", imagenContentType='" + imagenContentType + "'" +
            ", nombreModelo='" + getNombreModelo() + "'" +
            ", colorVestido='" + getColorVestido() + "'" +
            ", bordado='" + isBordado() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", observacion='" + getObservacion() + "'" +
            "}";
    }
}
