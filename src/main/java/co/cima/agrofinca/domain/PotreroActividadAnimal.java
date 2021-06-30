package co.cima.agrofinca.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PotreroActividadAnimal.
 */
@Entity
@Table(name = "potrero_actividad_animal")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PotreroActividadAnimal implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  private Long id;

  @ManyToOne
  @JsonIgnoreProperties(value = { "imagenes", "vacunas", "pesos", "eventos", "costos", "tipo", "raza", "potreros" }, allowSetters = true)
  private Animal animalId;

  @ManyToOne
  @JsonIgnoreProperties(value = { "animals", "potrero" }, allowSetters = true)
  private PotreroActividad potreroActividadId;

  // jhipster-needle-entity-add-field - JHipster will add fields here
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public PotreroActividadAnimal id(Long id) {
    this.id = id;
    return this;
  }

  public Animal getAnimalId() {
    return this.animalId;
  }

  public PotreroActividadAnimal animalId(Animal animal) {
    this.setAnimalId(animal);
    return this;
  }

  public void setAnimalId(Animal animal) {
    this.animalId = animal;
  }

  public PotreroActividad getPotreroActividadId() {
    return this.potreroActividadId;
  }

  public PotreroActividadAnimal potreroActividadId(PotreroActividad potreroActividad) {
    this.setPotreroActividadId(potreroActividad);
    return this;
  }

  public void setPotreroActividadId(PotreroActividad potreroActividad) {
    this.potreroActividadId = potreroActividad;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PotreroActividadAnimal)) {
      return false;
    }
    return id != null && id.equals(((PotreroActividadAnimal) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "PotreroActividadAnimal{" +
            "id=" + getId() +
            "}";
    }
}
