package co.cima.agrofinca.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ParametroDominio.
 */
@Entity
@Table(name = "parametro_dominio")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ParametroDominio implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  private Long id;

  @NotNull
  @Column(name = "pado_id", nullable = false)
  private Integer padoId;

  @NotNull
  @Column(name = "pado_descripcion", nullable = false)
  private String padoDescripcion;

  // jhipster-needle-entity-add-field - JHipster will add fields here
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ParametroDominio id(Long id) {
    this.id = id;
    return this;
  }

  public Integer getPadoId() {
    return this.padoId;
  }

  public ParametroDominio padoId(Integer padoId) {
    this.padoId = padoId;
    return this;
  }

  public void setPadoId(Integer padoId) {
    this.padoId = padoId;
  }

  public String getPadoDescripcion() {
    return this.padoDescripcion;
  }

  public ParametroDominio padoDescripcion(String padoDescripcion) {
    this.padoDescripcion = padoDescripcion;
    return this;
  }

  public void setPadoDescripcion(String padoDescripcion) {
    this.padoDescripcion = padoDescripcion;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ParametroDominio)) {
      return false;
    }
    return id != null && id.equals(((ParametroDominio) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "ParametroDominio{" +
            "id=" + getId() +
            ", padoId=" + getPadoId() +
            ", padoDescripcion='" + getPadoDescripcion() + "'" +
            "}";
    }
}
