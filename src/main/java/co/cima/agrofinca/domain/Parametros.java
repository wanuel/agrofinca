package co.cima.agrofinca.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Parametros.
 */
@Entity
@Table(name = "parametros")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Parametros implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @OneToMany(mappedBy = "evento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AnimalCostos> costos = new HashSet<>();

    @OneToMany(mappedBy = "evento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AnimalEvento> eventos = new HashSet<>();

    @OneToMany(mappedBy = "tipo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Animal> animalesTipos = new HashSet<>();

    @OneToMany(mappedBy = "raza")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Animal> animalesRazas = new HashSet<>();

    @OneToMany(mappedBy = "evento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AnimalPeso> pesos = new HashSet<>();

    @OneToMany(mappedBy = "tipo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AnimalVacunas> vacunas = new HashSet<>();

    @OneToMany(mappedBy = "padre")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Parametros> parametros = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "parametros", allowSetters = true)
    private Parametros padre;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Parametros descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<AnimalCostos> getCostos() {
        return costos;
    }

    public Parametros costos(Set<AnimalCostos> animalCostos) {
        this.costos = animalCostos;
        return this;
    }

    public Parametros addCostos(AnimalCostos animalCostos) {
        this.costos.add(animalCostos);
        animalCostos.setEvento(this);
        return this;
    }

    public Parametros removeCostos(AnimalCostos animalCostos) {
        this.costos.remove(animalCostos);
        animalCostos.setEvento(null);
        return this;
    }

    public void setCostos(Set<AnimalCostos> animalCostos) {
        this.costos = animalCostos;
    }

    public Set<AnimalEvento> getEventos() {
        return eventos;
    }

    public Parametros eventos(Set<AnimalEvento> animalEventos) {
        this.eventos = animalEventos;
        return this;
    }

    public Parametros addEventos(AnimalEvento animalEvento) {
        this.eventos.add(animalEvento);
        animalEvento.setEvento(this);
        return this;
    }

    public Parametros removeEventos(AnimalEvento animalEvento) {
        this.eventos.remove(animalEvento);
        animalEvento.setEvento(null);
        return this;
    }

    public void setEventos(Set<AnimalEvento> animalEventos) {
        this.eventos = animalEventos;
    }

    public Set<Animal> getAnimalesTipos() {
        return animalesTipos;
    }

    public Parametros animalesTipos(Set<Animal> animales) {
        this.animalesTipos = animales;
        return this;
    }

    public Parametros addAnimalesTipos(Animal animal) {
        this.animalesTipos.add(animal);
        animal.setTipo(this);
        return this;
    }

    public Parametros removeAnimalesTipos(Animal animal) {
        this.animalesTipos.remove(animal);
        animal.setTipo(null);
        return this;
    }

    public void setAnimalesTipos(Set<Animal> animales) {
        this.animalesTipos = animales;
    }

    public Set<Animal> getAnimalesRazas() {
        return animalesRazas;
    }

    public Parametros animalesRazas(Set<Animal> animales) {
        this.animalesRazas = animales;
        return this;
    }

    public Parametros addAnimalesRazas(Animal animal) {
        this.animalesRazas.add(animal);
        animal.setRaza(this);
        return this;
    }

    public Parametros removeAnimalesRazas(Animal animal) {
        this.animalesRazas.remove(animal);
        animal.setRaza(null);
        return this;
    }

    public void setAnimalesRazas(Set<Animal> animales) {
        this.animalesRazas = animales;
    }

    public Set<AnimalPeso> getPesos() {
        return pesos;
    }

    public Parametros pesos(Set<AnimalPeso> animalPesos) {
        this.pesos = animalPesos;
        return this;
    }

    public Parametros addPesos(AnimalPeso animalPeso) {
        this.pesos.add(animalPeso);
        animalPeso.setEvento(this);
        return this;
    }

    public Parametros removePesos(AnimalPeso animalPeso) {
        this.pesos.remove(animalPeso);
        animalPeso.setEvento(null);
        return this;
    }

    public void setPesos(Set<AnimalPeso> animalPesos) {
        this.pesos = animalPesos;
    }

    public Set<AnimalVacunas> getVacunas() {
        return vacunas;
    }

    public Parametros vacunas(Set<AnimalVacunas> animalVacunas) {
        this.vacunas = animalVacunas;
        return this;
    }

    public Parametros addVacunas(AnimalVacunas animalVacunas) {
        this.vacunas.add(animalVacunas);
        animalVacunas.setTipo(this);
        return this;
    }

    public Parametros removeVacunas(AnimalVacunas animalVacunas) {
        this.vacunas.remove(animalVacunas);
        animalVacunas.setTipo(null);
        return this;
    }

    public void setVacunas(Set<AnimalVacunas> animalVacunas) {
        this.vacunas = animalVacunas;
    }

    public Set<Parametros> getParametros() {
        return parametros;
    }

    public Parametros parametros(Set<Parametros> parametros) {
        this.parametros = parametros;
        return this;
    }

    public Parametros addParametros(Parametros parametros) {
        this.parametros.add(parametros);
        parametros.setPadre(this);
        return this;
    }

    public Parametros removeParametros(Parametros parametros) {
        this.parametros.remove(parametros);
        parametros.setPadre(null);
        return this;
    }

    public void setParametros(Set<Parametros> parametros) {
        this.parametros = parametros;
    }

    public Parametros getPadre() {
        return padre;
    }

    public Parametros padre(Parametros parametros) {
        this.padre = parametros;
        return this;
    }

    public void setPadre(Parametros parametros) {
        this.padre = parametros;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Parametros)) {
            return false;
        }
        return id != null && id.equals(((Parametros) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Parametros{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
