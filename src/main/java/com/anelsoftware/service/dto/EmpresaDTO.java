package com.anelsoftware.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Empresa entity.
 */
public class EmpresaDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private String domicilio;

    @NotNull
    private String telefono;

    @NotNull
    private String categoria;

    private Long userId;

    private String userEmail;

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

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmpresaDTO empresaDTO = (EmpresaDTO) o;
        if(empresaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), empresaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmpresaDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", domicilio='" + getDomicilio() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", categoria='" + getCategoria() + "'" +
            "}";
    }
}
