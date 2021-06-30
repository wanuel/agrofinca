package co.cima.agrofinca.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DetalleCargue.
 */
@Entity
@Table(name = "detalle_cargue")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DetalleCargue implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  private Long id;

  @NotNull
  @Column(name = "deca_cup", nullable = false)
  private String decaCup;

  @NotNull
  @Column(name = "deca_estado", nullable = false)
  private String decaEstado;

  @NotNull
  @Column(name = "deca_json", nullable = false)
  private String decaJSON;

  @OneToOne
  @JoinColumn(unique = true)
  private ErrorCargue decaId;

  // jhipster-needle-entity-add-field - JHipster will add fields here
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public DetalleCargue id(Long id) {
    this.id = id;
    return this;
  }

  public String getDecaCup() {
    return this.decaCup;
  }

  public DetalleCargue decaCup(String decaCup) {
    this.decaCup = decaCup;
    return this;
  }

  public void setDecaCup(String decaCup) {
    this.decaCup = decaCup;
  }

  public String getDecaEstado() {
    return this.decaEstado;
  }

  public DetalleCargue decaEstado(String decaEstado) {
    this.decaEstado = decaEstado;
    return this;
  }

  public void setDecaEstado(String decaEstado) {
    this.decaEstado = decaEstado;
  }

  public String getDecaJSON() {
    return this.decaJSON;
  }

  public DetalleCargue decaJSON(String decaJSON) {
    this.decaJSON = decaJSON;
    return this;
  }

  public void setDecaJSON(String decaJSON) {
    this.decaJSON = decaJSON;
  }

  public ErrorCargue getDecaId() {
    return this.decaId;
  }

  public DetalleCargue decaId(ErrorCargue errorCargue) {
    this.setDecaId(errorCargue);
    return this;
  }

  public void setDecaId(ErrorCargue errorCargue) {
    this.decaId = errorCargue;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DetalleCargue)) {
      return false;
    }
    return id != null && id.equals(((DetalleCargue) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "DetalleCargue{" +
            "id=" + getId() +
            ", decaCup='" + getDecaCup() + "'" +
            ", decaEstado='" + getDecaEstado() + "'" +
            ", decaJSON='" + getDecaJSON() + "'" +
            "}";
    }
}
