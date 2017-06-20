package com.anelsoftware.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.anelsoftware.domain.enumeration.TipoFalda;

import com.anelsoftware.domain.enumeration.TipoMedida;

/**
 * Class Medida.
 * @author anelsoftware
 */
@ApiModel(description = "Class Medida. @author anelsoftware")
@Entity
@Table(name = "medida")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Medida implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contorno_busto")
    private Double contornoBusto;

    @Column(name = "ancho_pecho")
    private Double anchoPecho;

    @Column(name = "alto_busto")
    private Double altoBusto;

    @Column(name = "bajo_busto")
    private Double bajoBusto;

    @Column(name = "altura_pinza")
    private Double alturaPinza;

    @Column(name = "separacion_busto")
    private Double separacionBusto;

    @Column(name = "talle_deltantero")
    private Double talleDeltantero;

    @Column(name = "talle_espalda")
    private Double talleEspalda;

    @Column(name = "largo_corset")
    private Double largoCorset;

    @Column(name = "costado")
    private Double costado;

    @Column(name = "hombro")
    private Double hombro;

    @Column(name = "ancho_hombro")
    private Double anchoHombro;

    @Column(name = "largo_manga")
    private Double largoManga;

    @Column(name = "sisa_delantero")
    private Double sisaDelantero;

    @Column(name = "sisa_espalda")
    private Double sisaEspalda;

    @Column(name = "contorno_cintura")
    private Double contornoCintura;

    @Column(name = "ante_cadera")
    private Double anteCadera;

    @Column(name = "contorno_cadera")
    private Double contornoCadera;

    @Column(name = "posicion_cadera")
    private Double posicionCadera;

    @Column(name = "largo_falda")
    private Double largoFalda;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_falda")
    private TipoFalda tipoFalda;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_medida", nullable = false)
    private TipoMedida tipoMedida;

    @NotNull
    @Column(name = "fecha_medida", nullable = false)
    private LocalDate fechaMedida;

    @Column(name = "ancho_espalda")
    private Double anchoEspalda;

    @Column(name = "ancho_manga")
    private Double anchoManga;

    @Column(name = "tiro_pantalon")
    private Double tiroPantalon;

    @Column(name = "ancho_pinza_pantalon")
    private Double anchoPinzaPantalon;

    @Column(name = "ancho_rodilla_pantalon")
    private Double anchoRodillaPantalon;

    @Column(name = "bota_pantalon")
    private Double botaPantalon;

    @Column(name = "largo_pantalon")
    private Double largoPantalon;

    @ManyToOne(optional = false)
    @NotNull
    private Encargo encargo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getContornoBusto() {
        return contornoBusto;
    }

    public Medida contornoBusto(Double contornoBusto) {
        this.contornoBusto = contornoBusto;
        return this;
    }

    public void setContornoBusto(Double contornoBusto) {
        this.contornoBusto = contornoBusto;
    }

    public Double getAnchoPecho() {
        return anchoPecho;
    }

    public Medida anchoPecho(Double anchoPecho) {
        this.anchoPecho = anchoPecho;
        return this;
    }

    public void setAnchoPecho(Double anchoPecho) {
        this.anchoPecho = anchoPecho;
    }

    public Double getAltoBusto() {
        return altoBusto;
    }

    public Medida altoBusto(Double altoBusto) {
        this.altoBusto = altoBusto;
        return this;
    }

    public void setAltoBusto(Double altoBusto) {
        this.altoBusto = altoBusto;
    }

    public Double getBajoBusto() {
        return bajoBusto;
    }

    public Medida bajoBusto(Double bajoBusto) {
        this.bajoBusto = bajoBusto;
        return this;
    }

    public void setBajoBusto(Double bajoBusto) {
        this.bajoBusto = bajoBusto;
    }

    public Double getAlturaPinza() {
        return alturaPinza;
    }

    public Medida alturaPinza(Double alturaPinza) {
        this.alturaPinza = alturaPinza;
        return this;
    }

    public void setAlturaPinza(Double alturaPinza) {
        this.alturaPinza = alturaPinza;
    }

    public Double getSeparacionBusto() {
        return separacionBusto;
    }

    public Medida separacionBusto(Double separacionBusto) {
        this.separacionBusto = separacionBusto;
        return this;
    }

    public void setSeparacionBusto(Double separacionBusto) {
        this.separacionBusto = separacionBusto;
    }

    public Double getTalleDeltantero() {
        return talleDeltantero;
    }

    public Medida talleDeltantero(Double talleDeltantero) {
        this.talleDeltantero = talleDeltantero;
        return this;
    }

    public void setTalleDeltantero(Double talleDeltantero) {
        this.talleDeltantero = talleDeltantero;
    }

    public Double getTalleEspalda() {
        return talleEspalda;
    }

    public Medida talleEspalda(Double talleEspalda) {
        this.talleEspalda = talleEspalda;
        return this;
    }

    public void setTalleEspalda(Double talleEspalda) {
        this.talleEspalda = talleEspalda;
    }

    public Double getLargoCorset() {
        return largoCorset;
    }

    public Medida largoCorset(Double largoCorset) {
        this.largoCorset = largoCorset;
        return this;
    }

    public void setLargoCorset(Double largoCorset) {
        this.largoCorset = largoCorset;
    }

    public Double getCostado() {
        return costado;
    }

    public Medida costado(Double costado) {
        this.costado = costado;
        return this;
    }

    public void setCostado(Double costado) {
        this.costado = costado;
    }

    public Double getHombro() {
        return hombro;
    }

    public Medida hombro(Double hombro) {
        this.hombro = hombro;
        return this;
    }

    public void setHombro(Double hombro) {
        this.hombro = hombro;
    }

    public Double getAnchoHombro() {
        return anchoHombro;
    }

    public Medida anchoHombro(Double anchoHombro) {
        this.anchoHombro = anchoHombro;
        return this;
    }

    public void setAnchoHombro(Double anchoHombro) {
        this.anchoHombro = anchoHombro;
    }

    public Double getLargoManga() {
        return largoManga;
    }

    public Medida largoManga(Double largoManga) {
        this.largoManga = largoManga;
        return this;
    }

    public void setLargoManga(Double largoManga) {
        this.largoManga = largoManga;
    }

    public Double getSisaDelantero() {
        return sisaDelantero;
    }

    public Medida sisaDelantero(Double sisaDelantero) {
        this.sisaDelantero = sisaDelantero;
        return this;
    }

    public void setSisaDelantero(Double sisaDelantero) {
        this.sisaDelantero = sisaDelantero;
    }

    public Double getSisaEspalda() {
        return sisaEspalda;
    }

    public Medida sisaEspalda(Double sisaEspalda) {
        this.sisaEspalda = sisaEspalda;
        return this;
    }

    public void setSisaEspalda(Double sisaEspalda) {
        this.sisaEspalda = sisaEspalda;
    }

    public Double getContornoCintura() {
        return contornoCintura;
    }

    public Medida contornoCintura(Double contornoCintura) {
        this.contornoCintura = contornoCintura;
        return this;
    }

    public void setContornoCintura(Double contornoCintura) {
        this.contornoCintura = contornoCintura;
    }

    public Double getAnteCadera() {
        return anteCadera;
    }

    public Medida anteCadera(Double anteCadera) {
        this.anteCadera = anteCadera;
        return this;
    }

    public void setAnteCadera(Double anteCadera) {
        this.anteCadera = anteCadera;
    }

    public Double getContornoCadera() {
        return contornoCadera;
    }

    public Medida contornoCadera(Double contornoCadera) {
        this.contornoCadera = contornoCadera;
        return this;
    }

    public void setContornoCadera(Double contornoCadera) {
        this.contornoCadera = contornoCadera;
    }

    public Double getPosicionCadera() {
        return posicionCadera;
    }

    public Medida posicionCadera(Double posicionCadera) {
        this.posicionCadera = posicionCadera;
        return this;
    }

    public void setPosicionCadera(Double posicionCadera) {
        this.posicionCadera = posicionCadera;
    }

    public Double getLargoFalda() {
        return largoFalda;
    }

    public Medida largoFalda(Double largoFalda) {
        this.largoFalda = largoFalda;
        return this;
    }

    public void setLargoFalda(Double largoFalda) {
        this.largoFalda = largoFalda;
    }

    public TipoFalda getTipoFalda() {
        return tipoFalda;
    }

    public Medida tipoFalda(TipoFalda tipoFalda) {
        this.tipoFalda = tipoFalda;
        return this;
    }

    public void setTipoFalda(TipoFalda tipoFalda) {
        this.tipoFalda = tipoFalda;
    }

    public TipoMedida getTipoMedida() {
        return tipoMedida;
    }

    public Medida tipoMedida(TipoMedida tipoMedida) {
        this.tipoMedida = tipoMedida;
        return this;
    }

    public void setTipoMedida(TipoMedida tipoMedida) {
        this.tipoMedida = tipoMedida;
    }

    public LocalDate getFechaMedida() {
        return fechaMedida;
    }

    public Medida fechaMedida(LocalDate fechaMedida) {
        this.fechaMedida = fechaMedida;
        return this;
    }

    public void setFechaMedida(LocalDate fechaMedida) {
        this.fechaMedida = fechaMedida;
    }

    public Double getAnchoEspalda() {
        return anchoEspalda;
    }

    public Medida anchoEspalda(Double anchoEspalda) {
        this.anchoEspalda = anchoEspalda;
        return this;
    }

    public void setAnchoEspalda(Double anchoEspalda) {
        this.anchoEspalda = anchoEspalda;
    }

    public Double getAnchoManga() {
        return anchoManga;
    }

    public Medida anchoManga(Double anchoManga) {
        this.anchoManga = anchoManga;
        return this;
    }

    public void setAnchoManga(Double anchoManga) {
        this.anchoManga = anchoManga;
    }

    public Double getTiroPantalon() {
        return tiroPantalon;
    }

    public Medida tiroPantalon(Double tiroPantalon) {
        this.tiroPantalon = tiroPantalon;
        return this;
    }

    public void setTiroPantalon(Double tiroPantalon) {
        this.tiroPantalon = tiroPantalon;
    }

    public Double getAnchoPinzaPantalon() {
        return anchoPinzaPantalon;
    }

    public Medida anchoPinzaPantalon(Double anchoPinzaPantalon) {
        this.anchoPinzaPantalon = anchoPinzaPantalon;
        return this;
    }

    public void setAnchoPinzaPantalon(Double anchoPinzaPantalon) {
        this.anchoPinzaPantalon = anchoPinzaPantalon;
    }

    public Double getAnchoRodillaPantalon() {
        return anchoRodillaPantalon;
    }

    public Medida anchoRodillaPantalon(Double anchoRodillaPantalon) {
        this.anchoRodillaPantalon = anchoRodillaPantalon;
        return this;
    }

    public void setAnchoRodillaPantalon(Double anchoRodillaPantalon) {
        this.anchoRodillaPantalon = anchoRodillaPantalon;
    }

    public Double getBotaPantalon() {
        return botaPantalon;
    }

    public Medida botaPantalon(Double botaPantalon) {
        this.botaPantalon = botaPantalon;
        return this;
    }

    public void setBotaPantalon(Double botaPantalon) {
        this.botaPantalon = botaPantalon;
    }

    public Double getLargoPantalon() {
        return largoPantalon;
    }

    public Medida largoPantalon(Double largoPantalon) {
        this.largoPantalon = largoPantalon;
        return this;
    }

    public void setLargoPantalon(Double largoPantalon) {
        this.largoPantalon = largoPantalon;
    }

    public Encargo getEncargo() {
        return encargo;
    }

    public Medida encargo(Encargo encargo) {
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
        Medida medida = (Medida) o;
        if (medida.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medida.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Medida{" +
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
            ", tipoMedida='" + getTipoMedida() + "'" +
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
