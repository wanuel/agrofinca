package co.cima.agrofinca.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AnimalPeso.
 */
@Entity
@Table(name = "animal_peso")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AnimalPeso implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  private Long id;

  @NotNull
  @Column(name = "fecha", nullable = false)
  private LocalDate fecha;

  @NotNull
  @Column(name = "peso", precision = 21, scale = 2, nullable = false)
  private BigDecimal peso;

  @ManyToOne
  @JsonIgnoreProperties(value = { "imagenes", "vacunas", "pesos", "eventos", "costos", "tipo", "raza", "potreros" }, allowSetters = true)
  private Animal animal;

  @ManyToOne
  @JsonIgnoreProperties(
    value = { "costos", "eventos", "animalesTipos", "animalesRazas", "pesos", "vacunas", "parametros", "padre" },
    allowSetters = true
  )
  private Parametros evento;

  // jhipster-needle-entity-add-field - JHipster will add fields here
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public AnimalPeso id(Long id) {
    this.id = id;
    return this;
  }

  public LocalDate getFecha() {
    return this.fecha;
  }

  public AnimalPeso fecha(LocalDate fecha) {
    this.fecha = fecha;
    return this;
  }

  public void setFecha(LocalDate fecha) {
    this.fecha = fecha;
  }

  public BigDecimal getPeso() {
    return this.peso;
  }

  public AnimalPeso peso(BigDecimal peso) {
    this.peso = peso;
    return this;
  }

  public void setPeso(BigDecimal peso) {
    this.peso = peso;
  }

  public Animal getAnimal() {
    return this.animal;
  }

  public AnimalPeso animal(Animal animal) {
    this.setAnimal(animal);
    return this;
  }

  public void setAnimal(Animal animal) {
    this.animal = animal;
  }

  public Parametros getEvento() {
    return this.evento;
  }

  public AnimalPeso evento(Parametros parametros) {
    this.setEvento(parametros);
    return this;
  }

  public void setEvento(Parametros parametros) {
    this.evento = parametros;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AnimalPeso)) {
      return false;
    }
    return id != null && id.equals(((AnimalPeso) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "AnimalPeso{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", peso=" + getPeso() +
            "}";
    }
}
