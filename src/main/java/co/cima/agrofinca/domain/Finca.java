package co.cima.agrofinca.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Finca.
 */
@Entity
@Table(name = "finca")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Finca implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  private Long id;

  @NotNull
  @Column(name = "nombre", nullable = false)
  private String nombre;

  @Column(name = "area", precision = 21, scale = 2)
  private BigDecimal area;

  @OneToMany(mappedBy = "finca")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @JsonIgnoreProperties(value = { "actividades", "finca" }, allowSetters = true)
  private Set<Potrero> potreros = new HashSet<>();

  // jhipster-needle-entity-add-field - JHipster will add fields here
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Finca id(Long id) {
    this.id = id;
    return this;
  }

  public String getNombre() {
    return this.nombre;
  }

  public Finca nombre(String nombre) {
    this.nombre = nombre;
    return this;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public BigDecimal getArea() {
    return this.area;
  }

  public Finca area(BigDecimal area) {
    this.area = area;
    return this;
  }

  public void setArea(BigDecimal area) {
    this.area = area;
  }

  public Set<Potrero> getPotreros() {
    return this.potreros;
  }

  public Finca potreros(Set<Potrero> potreros) {
    this.setPotreros(potreros);
    return this;
  }

  public Finca addPotreros(Potrero potrero) {
    this.potreros.add(potrero);
    potrero.setFinca(this);
    return this;
  }

  public Finca removePotreros(Potrero potrero) {
    this.potreros.remove(potrero);
    potrero.setFinca(null);
    return this;
  }

  public void setPotreros(Set<Potrero> potreros) {
    if (this.potreros != null) {
      this.potreros.forEach(i -> i.setFinca(null));
    }
    if (potreros != null) {
      potreros.forEach(i -> i.setFinca(this));
    }
    this.potreros = potreros;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Finca)) {
      return false;
    }
    return id != null && id.equals(((Finca) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "Finca{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", area=" + getArea() +
            "}";
    }
}
