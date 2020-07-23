package co.cima.agrofinca.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A AnimalVacunas.
 */
@Entity
@Table(name = "animal_vacunas")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AnimalVacunas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "laboratorio")
    private String laboratorio;

    @NotNull
    @Column(name = "dosis", nullable = false)
    private String dosis;

    @ManyToOne
    @JsonIgnoreProperties(value = "vacunas", allowSetters = true)
    private Animal animal;

    @ManyToOne
    @JsonIgnoreProperties(value = "vacunas", allowSetters = true)
    private Parametros tipo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public AnimalVacunas fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public AnimalVacunas nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public AnimalVacunas laboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
        return this;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getDosis() {
        return dosis;
    }

    public AnimalVacunas dosis(String dosis) {
        this.dosis = dosis;
        return this;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public Animal getAnimal() {
        return animal;
    }

    public AnimalVacunas animal(Animal animal) {
        this.animal = animal;
        return this;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Parametros getTipo() {
        return tipo;
    }

    public AnimalVacunas tipo(Parametros parametros) {
        this.tipo = parametros;
        return this;
    }

    public void setTipo(Parametros parametros) {
        this.tipo = parametros;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnimalVacunas)) {
            return false;
        }
        return id != null && id.equals(((AnimalVacunas) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnimalVacunas{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", laboratorio='" + getLaboratorio() + "'" +
            ", dosis='" + getDosis() + "'" +
            "}";
    }
}
