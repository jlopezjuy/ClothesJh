package com.anelsoftware.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.anelsoftware.domain.enumeration.TipoFalda;

/**
 * A DTO for the Medida entity.
 */
public class MedidaDTO implements Serializable {

    private Long id;

    private Double contornoBusto;

    private Double anchoPecho;

    private Double altoBusto;

    private Double bajoBusto;

    private Double alturaPinza;

    private Double separacionBusto;

    private Double talleDeltantero;

    private Double talleEspalda;

    private Double largoCorset;

    private Double costado;

    private Double hombro;

    private Double anchoHombro;

    private Double largoManga;

    private Double sisaDelantero;

    private Double sisaEspalda;

    private Double contornoCintura;

    private Double anteCadera;

    private Double contornoCadera;

    private Double posicionCadera;

    private Double largoFalda;

    private TipoFalda tipoFalda;

    @NotNull
    private LocalDate fechaMedida;

    private Double anchoEspalda;

    private Double anchoManga;

    private Double tiroPantalon;

    private Double anchoPinzaPantalon;

    private Double anchoRodillaPantalon;

    private Double botaPantalon;

    private Double largoPantalon;

    private Long clienteId;

    private String clienteNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getContornoBusto() {
        return contornoBusto;
    }

    public void setContornoBusto(Double contornoBusto) {
        this.contornoBusto = contornoBusto;
    }

    public Double getAnchoPecho() {
        return anchoPecho;
    }

    public void setAnchoPecho(Double anchoPecho) {
        this.anchoPecho = anchoPecho;
    }

    public Double getAltoBusto() {
        return altoBusto;
    }

    public void setAltoBusto(Double altoBusto) {
        this.altoBusto = altoBusto;
    }

    public Double getBajoBusto() {
        return bajoBusto;
    }

    public void setBajoBusto(Double bajoBusto) {
        this.bajoBusto = bajoBusto;
    }

    public Double getAlturaPinza() {
        return alturaPinza;
    }

    public void setAlturaPinza(Double alturaPinza) {
        this.alturaPinza = alturaPinza;
    }

    public Double getSeparacionBusto() {
        return separacionBusto;
    }

    public void setSeparacionBusto(Double separacionBusto) {
        this.separacionBusto = separacionBusto;
    }

    public Double getTalleDeltantero() {
        return talleDeltantero;
    }

    public void setTalleDeltantero(Double talleDeltantero) {
        this.talleDeltantero = talleDeltantero;
    }

    public Double getTalleEspalda() {
        return talleEspalda;
    }

    public void setTalleEspalda(Double talleEspalda) {
        this.talleEspalda = talleEspalda;
    }

    public Double getLargoCorset() {
        return largoCorset;
    }

    public void setLargoCorset(Double largoCorset) {
        this.largoCorset = largoCorset;
    }

    public Double getCostado() {
        return costado;
    }

    public void setCostado(Double costado) {
        this.costado = costado;
    }

    public Double getHombro() {
        return hombro;
    }

    public void setHombro(Double hombro) {
        this.hombro = hombro;
    }

    public Double getAnchoHombro() {
        return anchoHombro;
    }

    public void setAnchoHombro(Double anchoHombro) {
        this.anchoHombro = anchoHombro;
    }

    public Double getLargoManga() {
        return largoManga;
    }

    public void setLargoManga(Double largoManga) {
        this.largoManga = largoManga;
    }

    public Double getSisaDelantero() {
        return sisaDelantero;
    }

    public void setSisaDelantero(Double sisaDelantero) {
        this.sisaDelantero = sisaDelantero;
    }

    public Double getSisaEspalda() {
        return sisaEspalda;
    }

    public void setSisaEspalda(Double sisaEspalda) {
        this.sisaEspalda = sisaEspalda;
    }

    public Double getContornoCintura() {
        return contornoCintura;
    }

    public void setContornoCintura(Double contornoCintura) {
        this.contornoCintura = contornoCintura;
    }

    public Double getAnteCadera() {
        return anteCadera;
    }

    public void setAnteCadera(Double anteCadera) {
        this.anteCadera = anteCadera;
    }

    public Double getContornoCadera() {
        return contornoCadera;
    }

    public void setContornoCadera(Double contornoCadera) {
        this.contornoCadera = contornoCadera;
    }

    public Double getPosicionCadera() {
        return posicionCadera;
    }

    public void setPosicionCadera(Double posicionCadera) {
        this.posicionCadera = posicionCadera;
    }

    public Double getLargoFalda() {
        return largoFalda;
    }

    public void setLargoFalda(Double largoFalda) {
        this.largoFalda = largoFalda;
    }

    public TipoFalda getTipoFalda() {
        return tipoFalda;
    }

    public void setTipoFalda(TipoFalda tipoFalda) {
        this.tipoFalda = tipoFalda;
    }

    public LocalDate getFechaMedida() {
        return fechaMedida;
    }

    public void setFechaMedida(LocalDate fechaMedida) {
        this.fechaMedida = fechaMedida;
    }

    public Double getAnchoEspalda() {
        return anchoEspalda;
    }

    public void setAnchoEspalda(Double anchoEspalda) {
        this.anchoEspalda = anchoEspalda;
    }

    public Double getAnchoManga() {
        return anchoManga;
    }

    public void setAnchoManga(Double anchoManga) {
        this.anchoManga = anchoManga;
    }

    public Double getTiroPantalon() {
        return tiroPantalon;
    }

    public void setTiroPantalon(Double tiroPantalon) {
        this.tiroPantalon = tiroPantalon;
    }

    public Double getAnchoPinzaPantalon() {
        return anchoPinzaPantalon;
    }

    public void setAnchoPinzaPantalon(Double anchoPinzaPantalon) {
        this.anchoPinzaPantalon = anchoPinzaPantalon;
    }

    public Double getAnchoRodillaPantalon() {
        return anchoRodillaPantalon;
    }

    public void setAnchoRodillaPantalon(Double anchoRodillaPantalon) {
        this.anchoRodillaPantalon = anchoRodillaPantalon;
    }

    public Double getBotaPantalon() {
        return botaPantalon;
    }

    public void setBotaPantalon(Double botaPantalon) {
        this.botaPantalon = botaPantalon;
    }

    public Double getLargoPantalon() {
        return largoPantalon;
    }

    public void setLargoPantalon(Double largoPantalon) {
        this.largoPantalon = largoPantalon;
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

        MedidaDTO medidaDTO = (MedidaDTO) o;
        if(medidaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medidaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MedidaDTO{" +
            "id=" + getId() +
            ", contornoBusto='" + getContornoBusto() + "'" +
            ", anchoPecho='" + getAnchoPecho() + "'" +
            ", altoBusto='" + getAltoBusto() + "'" +
            ", bajoBusto='" + getBajoBusto() + "'" +
            ", alturaPinza='" + getAlturaPinza() + "'" +
            ", separacionBusto='" + getSeparacionBusto() + "'" +
            ", talleDeltantero='" + getTalleDeltantero() + "'" +
            ", talleEspalda='" + getTalleEspalda() + "'" +
            ", largoCorset='" + getLargoCorset() + "'" +
            ", costado='" + getCostado() + "'" +
            ", hombro='" + getHombro() + "'" +
            ", anchoHombro='" + getAnchoHombro() + "'" +
            ", largoManga='" + getLargoManga() + "'" +
            ", sisaDelantero='" + getSisaDelantero() + "'" +
            ", sisaEspalda='" + getSisaEspalda() + "'" +
            ", contornoCintura='" + getContornoCintura() + "'" +
            ", anteCadera='" + getAnteCadera() + "'" +
            ", contornoCadera='" + getContornoCadera() + "'" +
            ", posicionCadera='" + getPosicionCadera() + "'" +
            ", largoFalda='" + getLargoFalda() + "'" +
            ", tipoFalda='" + getTipoFalda() + "'" +
            ", fechaMedida='" + getFechaMedida() + "'" +
            ", anchoEspalda='" + getAnchoEspalda() + "'" +
            ", anchoManga='" + getAnchoManga() + "'" +
            ", tiroPantalon='" + getTiroPantalon() + "'" +
            ", anchoPinzaPantalon='" + getAnchoPinzaPantalon() + "'" +
            ", anchoRodillaPantalon='" + getAnchoRodillaPantalon() + "'" +
            ", botaPantalon='" + getBotaPantalon() + "'" +
            ", largoPantalon='" + getLargoPantalon() + "'" +
            "}";
    }
}
