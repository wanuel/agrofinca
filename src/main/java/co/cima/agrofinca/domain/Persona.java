package co.cima.agrofinca.domain;

import co.cima.agrofinca.domain.enumeration.GENERO;
import co.cima.agrofinca.domain.enumeration.TIPODOCUMENTO;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Persona.
 */
@Entity
@Table(name = "persona")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Persona implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  private Long id;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "tipo_documento", nullable = false)
  private TIPODOCUMENTO tipoDocumento;

  @Column(name = "num_docuemnto")
  private Long numDocuemnto;

  @NotNull
  @Column(name = "primer_nombre", nullable = false)
  private String primerNombre;

  @Column(name = "segundo_nombre")
  private String segundoNombre;

  @Column(name = "primer_apellido")
  private String primerApellido;

  @Column(name = "segundo_apellido")
  private String segundoApellido;

  @Column(name = "fecha_nacimiento")
  private LocalDate fechaNacimiento;

  @Enumerated(EnumType.STRING)
  @Column(name = "genero")
  private GENERO genero;

  // jhipster-needle-entity-add-field - JHipster will add fields here
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Persona id(Long id) {
    this.id = id;
    return this;
  }

  public TIPODOCUMENTO getTipoDocumento() {
    return this.tipoDocumento;
  }

  public Persona tipoDocumento(TIPODOCUMENTO tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
    return this;
  }

  public void setTipoDocumento(TIPODOCUMENTO tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public Long getNumDocuemnto() {
    return this.numDocuemnto;
  }

  public Persona numDocuemnto(Long numDocuemnto) {
    this.numDocuemnto = numDocuemnto;
    return this;
  }

  public void setNumDocuemnto(Long numDocuemnto) {
    this.numDocuemnto = numDocuemnto;
  }

  public String getPrimerNombre() {
    return this.primerNombre;
  }

  public Persona primerNombre(String primerNombre) {
    this.primerNombre = primerNombre;
    return this;
  }

  public void setPrimerNombre(String primerNombre) {
    this.primerNombre = primerNombre;
  }

  public String getSegundoNombre() {
    return this.segundoNombre;
  }

  public Persona segundoNombre(String segundoNombre) {
    this.segundoNombre = segundoNombre;
    return this;
  }

  public void setSegundoNombre(String segundoNombre) {
    this.segundoNombre = segundoNombre;
  }

  public String getPrimerApellido() {
    return this.primerApellido;
  }

  public Persona primerApellido(String primerApellido) {
    this.primerApellido = primerApellido;
    return this;
  }

  public void setPrimerApellido(String primerApellido) {
    this.primerApellido = primerApellido;
  }

  public String getSegundoApellido() {
    return this.segundoApellido;
  }

  public Persona segundoApellido(String segundoApellido) {
    this.segundoApellido = segundoApellido;
    return this;
  }

  public void setSegundoApellido(String segundoApellido) {
    this.segundoApellido = segundoApellido;
  }

  public LocalDate getFechaNacimiento() {
    return this.fechaNacimiento;
  }

  public Persona fechaNacimiento(LocalDate fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
    return this;
  }

  public void setFechaNacimiento(LocalDate fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
  }

  public GENERO getGenero() {
    return this.genero;
  }

  public Persona genero(GENERO genero) {
    this.genero = genero;
    return this;
  }

  public void setGenero(GENERO genero) {
    this.genero = genero;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Persona)) {
      return false;
    }
    return id != null && id.equals(((Persona) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "Persona{" +
            "id=" + getId() +
            ", tipoDocumento='" + getTipoDocumento() + "'" +
            ", numDocuemnto=" + getNumDocuemnto() +
            ", primerNombre='" + getPrimerNombre() + "'" +
            ", segundoNombre='" + getSegundoNombre() + "'" +
            ", primerApellido='" + getPrimerApellido() + "'" +
            ", segundoApellido='" + getSegundoApellido() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", genero='" + getGenero() + "'" +
            "}";
    }
}
