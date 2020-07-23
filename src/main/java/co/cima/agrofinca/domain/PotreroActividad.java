package co.cima.agrofinca.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import co.cima.agrofinca.domain.enumeration.SINO;

/**
 * A PotreroActividad.
 */
@Entity
@Table(name = "potrero_actividad")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PotreroActividad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @Column(name = "fecha_salida")
    private LocalDate fechaSalida;

    @NotNull
    @Column(name = "cantidad_bovinos", nullable = false)
    private Integer cantidadBovinos;

    @NotNull
    @Column(name = "cantidad_equinos", nullable = false)
    private Integer cantidadEquinos;

    @NotNull
    @Column(name = "cantidad_mulares", nullable = false)
    private Integer cantidadMulares;

    @Column(name = "fecha_limpia")
    private LocalDate fechaLimpia;

    @Column(name = "dias_descanso")
    private Integer diasDescanso;

    @Column(name = "dias_carga")
    private Integer diasCarga;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "ocupado", nullable = false)
    private SINO ocupado;

//    @ManyToMany
//    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//    @JoinTable(name = "potrero_actividad_animal",
//               joinColumns = @JoinColumn(name = "potrero_actividad_id", referencedColumnName = "id"),
//               inverseJoinColumns = @JoinColumn(name = "animal_id", referencedColumnName = "id"))
//    private Set<Animal> animals = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "actividades", allowSetters = true)
    private Potrero potrero;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "potreroActividad")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PotreroActividadAnimal> pastoreos = new HashSet<>();
    
    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public PotreroActividad fechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
        return this;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public PotreroActividad fechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
        return this;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Integer getCantidadBovinos() {
        return cantidadBovinos;
    }

    public PotreroActividad cantidadBovinos(Integer cantidadBovinos) {
        this.cantidadBovinos = cantidadBovinos;
        return this;
    }

    public void setCantidadBovinos(Integer cantidadBovinos) {
        this.cantidadBovinos = cantidadBovinos;
    }

    public Integer getCantidadEquinos() {
        return cantidadEquinos;
    }

    public PotreroActividad cantidadEquinos(Integer cantidadEquinos) {
        this.cantidadEquinos = cantidadEquinos;
        return this;
    }

    public void setCantidadEquinos(Integer cantidadEquinos) {
        this.cantidadEquinos = cantidadEquinos;
    }

    public Integer getCantidadMulares() {
        return cantidadMulares;
    }

    public PotreroActividad cantidadMulares(Integer cantidadMulares) {
        this.cantidadMulares = cantidadMulares;
        return this;
    }

    public void setCantidadMulares(Integer cantidadMulares) {
        this.cantidadMulares = cantidadMulares;
    }

    public LocalDate getFechaLimpia() {
        return fechaLimpia;
    }

    public PotreroActividad fechaLimpia(LocalDate fechaLimpia) {
        this.fechaLimpia = fechaLimpia;
        return this;
    }

    public void setFechaLimpia(LocalDate fechaLimpia) {
        this.fechaLimpia = fechaLimpia;
    }

    public Integer getDiasDescanso() {
        return diasDescanso;
    }

    public PotreroActividad diasDescanso(Integer diasDescanso) {
        this.diasDescanso = diasDescanso;
        return this;
    }

    public void setDiasDescanso(Integer diasDescanso) {
        this.diasDescanso = diasDescanso;
    }

    public Integer getDiasCarga() {
        return diasCarga;
    }

    public PotreroActividad diasCarga(Integer diasCarga) {
        this.diasCarga = diasCarga;
        return this;
    }

    public void setDiasCarga(Integer diasCarga) {
        this.diasCarga = diasCarga;
    }

    public SINO getOcupado() {
        return ocupado;
    }

    public PotreroActividad ocupado(SINO ocupado) {
        this.ocupado = ocupado;
        return this;
    }

    public void setOcupado(SINO ocupado) {
        this.ocupado = ocupado;
    }

    

    public Set<PotreroActividadAnimal> getPastoreos() {
		return pastoreos;
	}

	public void setPastoreos(Set<PotreroActividadAnimal> pastoreos) {
		this.pastoreos = pastoreos;
	}

	public Potrero getPotrero() {
        return potrero;
    }

    public PotreroActividad potrero(Potrero potrero) {
        this.potrero = potrero;
        return this;
    }

    public void setPotrero(Potrero potrero) {
        this.potrero = potrero;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PotreroActividad)) {
            return false;
        }
        return id != null && id.equals(((PotreroActividad) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PotreroActividad{" +
            "id=" + getId() +
            ", fechaIngreso='" + getFechaIngreso() + "'" +
            ", fechaSalida='" + getFechaSalida() + "'" +
            ", cantidadBovinos=" + getCantidadBovinos() +
            ", cantidadEquinos=" + getCantidadEquinos() +
            ", cantidadMulares=" + getCantidadMulares() +
            ", fechaLimpia='" + getFechaLimpia() + "'" +
            ", diasDescanso=" + getDiasDescanso() +
            ", diasCarga=" + getDiasCarga() +
            ", ocupado='" + getOcupado() + "'" +
            "}";
    }
}
