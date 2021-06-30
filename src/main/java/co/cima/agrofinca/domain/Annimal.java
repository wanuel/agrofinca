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
 * A Annimal.
 */
@Entity
@Table(name = "annimal")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Annimal implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  private Long id;

  @NotNull
  @Column(name = "nombre", nullable = false)
  private String nombre;

  @OneToMany(mappedBy = "animal")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @JsonIgnoreProperties(value = { "animal", "lote" }, allowSetters = true)
  private Set<AnimalLote> lotes = new HashSet<>();

  // jhipster-needle-entity-add-field - JHipster will add fields here
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Annimal id(Long id) {
    this.id = id;
    return this;
  }

  public String getNombre() {
    return this.nombre;
  }

  public Annimal nombre(String nombre) {
    this.nombre = nombre;
    return this;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Set<AnimalLote> getLotes() {
    return this.lotes;
  }

  public Annimal lotes(Set<AnimalLote> animalLotes) {
    this.setLotes(animalLotes);
    return this;
  }

  public Annimal addLotes(AnimalLote animalLote) {
    this.lotes.add(animalLote);
    animalLote.setAnimal(this);
    return this;
  }

  public Annimal removeLotes(AnimalLote animalLote) {
    this.lotes.remove(animalLote);
    animalLote.setAnimal(null);
    return this;
  }

  public void setLotes(Set<AnimalLote> animalLotes) {
    if (this.lotes != null) {
      this.lotes.forEach(i -> i.setAnimal(null));
    }
    if (animalLotes != null) {
      animalLotes.forEach(i -> i.setAnimal(this));
    }
    this.lotes = animalLotes;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Annimal)) {
      return false;
    }
    return id != null && id.equals(((Annimal) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "Annimal{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
