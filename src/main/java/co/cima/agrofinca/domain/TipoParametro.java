package co.cima.agrofinca.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TipoParametro.
 */
@Entity
@Table(name = "tipo_parametro")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TipoParametro implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  private Long id;

  @NotNull
  @Column(name = "tipa_descripcion", nullable = false)
  private String tipaDescripcion;

  @OneToOne
  @JoinColumn(unique = true)
  private ParametroDominio tipaId;

  // jhipster-needle-entity-add-field - JHipster will add fields here
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public TipoParametro id(Long id) {
    this.id = id;
    return this;
  }

  public String getTipaDescripcion() {
    return this.tipaDescripcion;
  }

  public TipoParametro tipaDescripcion(String tipaDescripcion) {
    this.tipaDescripcion = tipaDescripcion;
    return this;
  }

  public void setTipaDescripcion(String tipaDescripcion) {
    this.tipaDescripcion = tipaDescripcion;
  }

  public ParametroDominio getTipaId() {
    return this.tipaId;
  }

  public TipoParametro tipaId(ParametroDominio parametroDominio) {
    this.setTipaId(parametroDominio);
    return this;
  }

  public void setTipaId(ParametroDominio parametroDominio) {
    this.tipaId = parametroDominio;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TipoParametro)) {
      return false;
    }
    return id != null && id.equals(((TipoParametro) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "TipoParametro{" +
            "id=" + getId() +
            ", tipaDescripcion='" + getTipaDescripcion() + "'" +
            "}";
    }
}
