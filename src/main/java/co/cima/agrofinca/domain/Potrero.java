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
 * A Potrero.
 */
@Entity
@Table(name = "potrero")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Potrero implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  private Long id;

  @NotNull
  @Column(name = "nombre", nullable = false)
  private String nombre;

  @Column(name = "descripcion")
  private String descripcion;

  @Column(name = "pasto")
  private String pasto;

  @Column(name = "area", precision = 21, scale = 2)
  private BigDecimal area;

  @OneToMany(mappedBy = "potrero")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @JsonIgnoreProperties(value = { "animals", "potrero" }, allowSetters = true)
  private Set<PotreroActividad> actividades = new HashSet<>();

  @ManyToOne
  @JsonIgnoreProperties(value = { "potreros" }, allowSetters = true)
  private Finca finca;

  // jhipster-needle-entity-add-field - JHipster will add fields here
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Potrero id(Long id) {
    this.id = id;
    return this;
  }

  public String getNombre() {
    return this.nombre;
  }

  public Potrero nombre(String nombre) {
    this.nombre = nombre;
    return this;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDescripcion() {
    return this.descripcion;
  }

  public Potrero descripcion(String descripcion) {
    this.descripcion = descripcion;
    return this;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getPasto() {
    return this.pasto;
  }

  public Potrero pasto(String pasto) {
    this.pasto = pasto;
    return this;
  }

  public void setPasto(String pasto) {
    this.pasto = pasto;
  }

  public BigDecimal getArea() {
    return this.area;
  }

  public Potrero area(BigDecimal area) {
    this.area = area;
    return this;
  }

  public void setArea(BigDecimal area) {
    this.area = area;
  }

  public Set<PotreroActividad> getActividades() {
    return this.actividades;
  }

  public Potrero actividades(Set<PotreroActividad> potreroActividads) {
    this.setActividades(potreroActividads);
    return this;
  }

  public Potrero addActividades(PotreroActividad potreroActividad) {
    this.actividades.add(potreroActividad);
    potreroActividad.setPotrero(this);
    return this;
  }

  public Potrero removeActividades(PotreroActividad potreroActividad) {
    this.actividades.remove(potreroActividad);
    potreroActividad.setPotrero(null);
    return this;
  }

  public void setActividades(Set<PotreroActividad> potreroActividads) {
    if (this.actividades != null) {
      this.actividades.forEach(i -> i.setPotrero(null));
    }
    if (potreroActividads != null) {
      potreroActividads.forEach(i -> i.setPotrero(this));
    }
    this.actividades = potreroActividads;
  }

  public Finca getFinca() {
    return this.finca;
  }

  public Potrero finca(Finca finca) {
    this.setFinca(finca);
    return this;
  }

  public void setFinca(Finca finca) {
    this.finca = finca;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Potrero)) {
      return false;
    }
    return id != null && id.equals(((Potrero) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "Potrero{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", pasto='" + getPasto() + "'" +
            ", area=" + getArea() +
            "}";
    }
}
