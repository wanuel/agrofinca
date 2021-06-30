package co.cima.agrofinca.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Parametros.
 */
@Entity
@Table(name = "parametros")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Parametros implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  private Long id;

  @NotNull
  @Column(name = "descripcion", nullable = false)
  private String descripcion;

  @OneToMany(mappedBy = "evento")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @JsonIgnoreProperties(value = { "animal", "evento" }, allowSetters = true)
  private Set<AnimalCostos> costos = new HashSet<>();

  @OneToMany(mappedBy = "evento")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @JsonIgnoreProperties(value = { "animal", "evento" }, allowSetters = true)
  private Set<AnimalEvento> eventos = new HashSet<>();

  @OneToMany(mappedBy = "tipo")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @JsonIgnoreProperties(value = { "imagenes", "vacunas", "pesos", "eventos", "costos", "tipo", "raza", "potreros" }, allowSetters = true)
  private Set<Animal> animalesTipos = new HashSet<>();

  @OneToMany(mappedBy = "raza")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @JsonIgnoreProperties(value = { "imagenes", "vacunas", "pesos", "eventos", "costos", "tipo", "raza", "potreros" }, allowSetters = true)
  private Set<Animal> animalesRazas = new HashSet<>();

  @OneToMany(mappedBy = "evento")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @JsonIgnoreProperties(value = { "animal", "evento" }, allowSetters = true)
  private Set<AnimalPeso> pesos = new HashSet<>();

  @OneToMany(mappedBy = "tipo")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @JsonIgnoreProperties(value = { "animal", "tipo" }, allowSetters = true)
  private Set<AnimalVacunas> vacunas = new HashSet<>();

  @OneToMany(mappedBy = "padre")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @JsonIgnoreProperties(
    value = { "costos", "eventos", "animalesTipos", "animalesRazas", "pesos", "vacunas", "parametros", "padre" },
    allowSetters = true
  )
  private Set<Parametros> parametros = new HashSet<>();

  @ManyToOne
  @JsonIgnoreProperties(
    value = { "costos", "eventos", "animalesTipos", "animalesRazas", "pesos", "vacunas", "parametros", "padre" },
    allowSetters = true
  )
  private Parametros padre;

  // jhipster-needle-entity-add-field - JHipster will add fields here
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Parametros id(Long id) {
    this.id = id;
    return this;
  }

  public String getDescripcion() {
    return this.descripcion;
  }

  public Parametros descripcion(String descripcion) {
    this.descripcion = descripcion;
    return this;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Set<AnimalCostos> getCostos() {
    return this.costos;
  }

  public Parametros costos(Set<AnimalCostos> animalCostos) {
    this.setCostos(animalCostos);
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
    if (this.costos != null) {
      this.costos.forEach(i -> i.setEvento(null));
    }
    if (animalCostos != null) {
      animalCostos.forEach(i -> i.setEvento(this));
    }
    this.costos = animalCostos;
  }

  public Set<AnimalEvento> getEventos() {
    return this.eventos;
  }

  public Parametros eventos(Set<AnimalEvento> animalEventos) {
    this.setEventos(animalEventos);
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
    if (this.eventos != null) {
      this.eventos.forEach(i -> i.setEvento(null));
    }
    if (animalEventos != null) {
      animalEventos.forEach(i -> i.setEvento(this));
    }
    this.eventos = animalEventos;
  }

  public Set<Animal> getAnimalesTipos() {
    return this.animalesTipos;
  }

  public Parametros animalesTipos(Set<Animal> animals) {
    this.setAnimalesTipos(animals);
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

  public void setAnimalesTipos(Set<Animal> animals) {
    if (this.animalesTipos != null) {
      this.animalesTipos.forEach(i -> i.setTipo(null));
    }
    if (animals != null) {
      animals.forEach(i -> i.setTipo(this));
    }
    this.animalesTipos = animals;
  }

  public Set<Animal> getAnimalesRazas() {
    return this.animalesRazas;
  }

  public Parametros animalesRazas(Set<Animal> animals) {
    this.setAnimalesRazas(animals);
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

  public void setAnimalesRazas(Set<Animal> animals) {
    if (this.animalesRazas != null) {
      this.animalesRazas.forEach(i -> i.setRaza(null));
    }
    if (animals != null) {
      animals.forEach(i -> i.setRaza(this));
    }
    this.animalesRazas = animals;
  }

  public Set<AnimalPeso> getPesos() {
    return this.pesos;
  }

  public Parametros pesos(Set<AnimalPeso> animalPesos) {
    this.setPesos(animalPesos);
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
    if (this.pesos != null) {
      this.pesos.forEach(i -> i.setEvento(null));
    }
    if (animalPesos != null) {
      animalPesos.forEach(i -> i.setEvento(this));
    }
    this.pesos = animalPesos;
  }

  public Set<AnimalVacunas> getVacunas() {
    return this.vacunas;
  }

  public Parametros vacunas(Set<AnimalVacunas> animalVacunas) {
    this.setVacunas(animalVacunas);
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
    if (this.vacunas != null) {
      this.vacunas.forEach(i -> i.setTipo(null));
    }
    if (animalVacunas != null) {
      animalVacunas.forEach(i -> i.setTipo(this));
    }
    this.vacunas = animalVacunas;
  }

  public Set<Parametros> getParametros() {
    return this.parametros;
  }

  public Parametros parametros(Set<Parametros> parametros) {
    this.setParametros(parametros);
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
    if (this.parametros != null) {
      this.parametros.forEach(i -> i.setPadre(null));
    }
    if (parametros != null) {
      parametros.forEach(i -> i.setPadre(this));
    }
    this.parametros = parametros;
  }

  public Parametros getPadre() {
    return this.padre;
  }

  public Parametros padre(Parametros parametros) {
    this.setPadre(parametros);
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
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
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
