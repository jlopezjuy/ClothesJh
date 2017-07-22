package com.anelsoftware.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Class Cliente.
 * @author anelsoftware
 */
@ApiModel(description = "Class Cliente. @author anelsoftware")
@Entity
@Table(name = "cliente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "celular")
    private String celular;

    @NotNull
    @Column(name = "telefono", nullable = false)
    private String telefono;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "domicilio", nullable = false)
    private String domicilio;

    @NotNull
    @Column(name = "colegio", nullable = false)
    private String colegio;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Encargo> encargos = new HashSet<>();

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FacturaPresupuesto> facturaPresupuestos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Cliente nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Cliente apellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCelular() {
        return celular;
    }

    public Cliente celular(String celular) {
        this.celular = celular;
        return this;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTelefono() {
        return telefono;
    }

    public Cliente telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public Cliente email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public Cliente domicilio(String domicilio) {
        this.domicilio = domicilio;
        return this;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getColegio() {
        return colegio;
    }

    public Cliente colegio(String colegio) {
        this.colegio = colegio;
        return this;
    }

    public void setColegio(String colegio) {
        this.colegio = colegio;
    }

    public Set<Encargo> getEncargos() {
        return encargos;
    }

    public Cliente encargos(Set<Encargo> encargos) {
        this.encargos = encargos;
        return this;
    }

    public Cliente addEncargo(Encargo encargo) {
        this.encargos.add(encargo);
        encargo.setCliente(this);
        return this;
    }

    public Cliente removeEncargo(Encargo encargo) {
        this.encargos.remove(encargo);
        encargo.setCliente(null);
        return this;
    }

    public void setEncargos(Set<Encargo> encargos) {
        this.encargos = encargos;
    }

    public Set<FacturaPresupuesto> getFacturaPresupuestos() {
        return facturaPresupuestos;
    }

    public Cliente facturaPresupuestos(Set<FacturaPresupuesto> facturaPresupuestos) {
        this.facturaPresupuestos = facturaPresupuestos;
        return this;
    }

    public Cliente addFacturaPresupuesto(FacturaPresupuesto facturaPresupuesto) {
        this.facturaPresupuestos.add(facturaPresupuesto);
        facturaPresupuesto.setCliente(this);
        return this;
    }

    public Cliente removeFacturaPresupuesto(FacturaPresupuesto facturaPresupuesto) {
        this.facturaPresupuestos.remove(facturaPresupuesto);
        facturaPresupuesto.setCliente(null);
        return this;
    }

    public void setFacturaPresupuestos(Set<FacturaPresupuesto> facturaPresupuestos) {
        this.facturaPresupuestos = facturaPresupuestos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cliente cliente = (Cliente) o;
        if (cliente.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cliente.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", celular='" + getCelular() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", email='" + getEmail() + "'" +
            ", domicilio='" + getDomicilio() + "'" +
            ", colegio='" + getColegio() + "'" +
            "}";
    }
}
