package co.cima.agrofinca.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import co.cima.agrofinca.domain.enumeration.TIPODOCUMENTO;

import co.cima.agrofinca.domain.enumeration.GENERO;

/**
 * A Persona.
 */
@Entity
@Table(name = "persona")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public TIPODOCUMENTO getTipoDocumento() {
        return tipoDocumento;
    }

    public Persona tipoDocumento(TIPODOCUMENTO tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        return this;
    }

    public void setTipoDocumento(TIPODOCUMENTO tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Long getNumDocuemnto() {
        return numDocuemnto;
    }

    public Persona numDocuemnto(Long numDocuemnto) {
        this.numDocuemnto = numDocuemnto;
        return this;
    }

    public void setNumDocuemnto(Long numDocuemnto) {
        this.numDocuemnto = numDocuemnto;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public Persona primerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
        return this;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public Persona segundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
        return this;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public Persona primerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
        return this;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public Persona segundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
        return this;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Persona fechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return this;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public GENERO getGenero() {
        return genero;
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
        return 31;
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
