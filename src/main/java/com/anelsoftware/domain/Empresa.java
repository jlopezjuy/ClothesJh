package com.anelsoftware.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Empresa.
 */
@Entity
@Table(name = "empresa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "domicilio", nullable = false)
    private String domicilio;

    @NotNull
    @Column(name = "telefono", nullable = false)
    private String telefono;

    @NotNull
    @Column(name = "categoria", nullable = false)
    private String categoria;

    @OneToMany(mappedBy = "empresa")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Cliente> clientes = new HashSet<>();

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Empresa nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public Empresa domicilio(String domicilio) {
        this.domicilio = domicilio;
        return this;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getTelefono() {
        return telefono;
    }

    public Empresa telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCategoria() {
        return categoria;
    }

    public Empresa categoria(String categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Set<Cliente> getClientes() {
        return clientes;
    }

    public Empresa clientes(Set<Cliente> clientes) {
        this.clientes = clientes;
        return this;
    }

    public Empresa addCliente(Cliente cliente) {
        this.clientes.add(cliente);
        cliente.setEmpresa(this);
        return this;
    }

    public Empresa removeCliente(Cliente cliente) {
        this.clientes.remove(cliente);
        cliente.setEmpresa(null);
        return this;
    }

    public void setClientes(Set<Cliente> clientes) {
        this.clientes = clientes;
    }

    public User getUser() {
        return user;
    }

    public Empresa user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Empresa empresa = (Empresa) o;
        if (empresa.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), empresa.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Empresa{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", domicilio='" + getDomicilio() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", categoria='" + getCategoria() + "'" +
            "}";
    }
}
