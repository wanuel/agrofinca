package co.cima.agrofinca.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AnimalLote.
 */
@Entity
@Table(name = "animal_lote")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AnimalLote implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  private Long id;

  @NotNull
  @Column(name = "fecha_entrada", nullable = false)
  private LocalDate fechaEntrada;

  @Column(name = "fecha_salida")
  private LocalDate fechaSalida;

  @ManyToOne
  @JsonIgnoreProperties(value = { "lotes" }, allowSetters = true)
  private Annimal animal;

  @ManyToOne
  @JsonIgnoreProperties(value = { "animales" }, allowSetters = true)
  private Lote lote;

  // jhipster-needle-entity-add-field - JHipster will add fields here
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public AnimalLote id(Long id) {
    this.id = id;
    return this;
  }

  public LocalDate getFechaEntrada() {
    return this.fechaEntrada;
  }

  public AnimalLote fechaEntrada(LocalDate fechaEntrada) {
    this.fechaEntrada = fechaEntrada;
    return this;
  }

  public void setFechaEntrada(LocalDate fechaEntrada) {
    this.fechaEntrada = fechaEntrada;
  }

  public LocalDate getFechaSalida() {
    return this.fechaSalida;
  }

  public AnimalLote fechaSalida(LocalDate fechaSalida) {
    this.fechaSalida = fechaSalida;
    return this;
  }

  public void setFechaSalida(LocalDate fechaSalida) {
    this.fechaSalida = fechaSalida;
  }

  public Annimal getAnimal() {
    return this.animal;
  }

  public AnimalLote animal(Annimal annimal) {
    this.setAnimal(annimal);
    return this;
  }

  public void setAnimal(Annimal annimal) {
    this.animal = annimal;
  }

  public Lote getLote() {
    return this.lote;
  }

  public AnimalLote lote(Lote lote) {
    this.setLote(lote);
    return this;
  }

  public void setLote(Lote lote) {
    this.lote = lote;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AnimalLote)) {
      return false;
    }
    return id != null && id.equals(((AnimalLote) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "AnimalLote{" +
            "id=" + getId() +
            ", fechaEntrada='" + getFechaEntrada() + "'" +
            ", fechaSalida='" + getFechaSalida() + "'" +
            "}";
    }
}
