package co.cima.agrofinca.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ErrorCargue.
 */
@Entity
@Table(name = "error_cargue")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ErrorCargue implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  private Long id;

  @NotNull
  @Column(name = "erca_error", nullable = false)
  private String ercaError;

  // jhipster-needle-entity-add-field - JHipster will add fields here
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ErrorCargue id(Long id) {
    this.id = id;
    return this;
  }

  public String getErcaError() {
    return this.ercaError;
  }

  public ErrorCargue ercaError(String ercaError) {
    this.ercaError = ercaError;
    return this;
  }

  public void setErcaError(String ercaError) {
    this.ercaError = ercaError;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ErrorCargue)) {
      return false;
    }
    return id != null && id.equals(((ErrorCargue) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "ErrorCargue{" +
            "id=" + getId() +
            ", ercaError='" + getErcaError() + "'" +
            "}";
    }
}
