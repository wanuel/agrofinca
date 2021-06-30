package co.cima.agrofinca.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Cargue.
 */
@Entity
@Table(name = "cargue")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cargue implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  private Long id;

  @NotNull
  @Column(name = "carg_nro_registros", nullable = false)
  private Integer cargNroRegistros;

  @NotNull
  @Column(name = "carg_json", nullable = false)
  private String cargJson;

  @NotNull
  @Column(name = "carg_entidad", nullable = false)
  private Integer cargEntidad;

  @NotNull
  @Column(name = "carg_nombre_archivo", nullable = false)
  private String cargNombreArchivo;

  @NotNull
  @Column(name = "carg_estado", nullable = false)
  private String cargEstado;

  @NotNull
  @Column(name = "carg_tipo", nullable = false)
  private String cargTipo;

  @NotNull
  @Column(name = "carg_es_reproceso", nullable = false)
  private Integer cargEsReproceso;

  @NotNull
  @Column(name = "carg_hash", nullable = false)
  private String cargHash;

  @NotNull
  @Column(name = "usuario_creacion", nullable = false)
  private String usuarioCreacion;

  @NotNull
  @Column(name = "fecha_creacion", nullable = false)
  private ZonedDateTime fechaCreacion;

  @Column(name = "usuario_modificacion")
  private String usuarioModificacion;

  @Column(name = "fecha_modificacion")
  private ZonedDateTime fechaModificacion;

  @JsonIgnoreProperties(value = { "decaId" }, allowSetters = true)
  @OneToOne
  @JoinColumn(unique = true)
  private DetalleCargue cargId;

  @OneToOne
  @JoinColumn(unique = true)
  private ErrorCargue cargId;

  // jhipster-needle-entity-add-field - JHipster will add fields here
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Cargue id(Long id) {
    this.id = id;
    return this;
  }

  public Integer getCargNroRegistros() {
    return this.cargNroRegistros;
  }

  public Cargue cargNroRegistros(Integer cargNroRegistros) {
    this.cargNroRegistros = cargNroRegistros;
    return this;
  }

  public void setCargNroRegistros(Integer cargNroRegistros) {
    this.cargNroRegistros = cargNroRegistros;
  }

  public String getCargJson() {
    return this.cargJson;
  }

  public Cargue cargJson(String cargJson) {
    this.cargJson = cargJson;
    return this;
  }

  public void setCargJson(String cargJson) {
    this.cargJson = cargJson;
  }

  public Integer getCargEntidad() {
    return this.cargEntidad;
  }

  public Cargue cargEntidad(Integer cargEntidad) {
    this.cargEntidad = cargEntidad;
    return this;
  }

  public void setCargEntidad(Integer cargEntidad) {
    this.cargEntidad = cargEntidad;
  }

  public String getCargNombreArchivo() {
    return this.cargNombreArchivo;
  }

  public Cargue cargNombreArchivo(String cargNombreArchivo) {
    this.cargNombreArchivo = cargNombreArchivo;
    return this;
  }

  public void setCargNombreArchivo(String cargNombreArchivo) {
    this.cargNombreArchivo = cargNombreArchivo;
  }

  public String getCargEstado() {
    return this.cargEstado;
  }

  public Cargue cargEstado(String cargEstado) {
    this.cargEstado = cargEstado;
    return this;
  }

  public void setCargEstado(String cargEstado) {
    this.cargEstado = cargEstado;
  }

  public String getCargTipo() {
    return this.cargTipo;
  }

  public Cargue cargTipo(String cargTipo) {
    this.cargTipo = cargTipo;
    return this;
  }

  public void setCargTipo(String cargTipo) {
    this.cargTipo = cargTipo;
  }

  public Integer getCargEsReproceso() {
    return this.cargEsReproceso;
  }

  public Cargue cargEsReproceso(Integer cargEsReproceso) {
    this.cargEsReproceso = cargEsReproceso;
    return this;
  }

  public void setCargEsReproceso(Integer cargEsReproceso) {
    this.cargEsReproceso = cargEsReproceso;
  }

  public String getCargHash() {
    return this.cargHash;
  }

  public Cargue cargHash(String cargHash) {
    this.cargHash = cargHash;
    return this;
  }

  public void setCargHash(String cargHash) {
    this.cargHash = cargHash;
  }

  public String getUsuarioCreacion() {
    return this.usuarioCreacion;
  }

  public Cargue usuarioCreacion(String usuarioCreacion) {
    this.usuarioCreacion = usuarioCreacion;
    return this;
  }

  public void setUsuarioCreacion(String usuarioCreacion) {
    this.usuarioCreacion = usuarioCreacion;
  }

  public ZonedDateTime getFechaCreacion() {
    return this.fechaCreacion;
  }

  public Cargue fechaCreacion(ZonedDateTime fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
    return this;
  }

  public void setFechaCreacion(ZonedDateTime fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public String getUsuarioModificacion() {
    return this.usuarioModificacion;
  }

  public Cargue usuarioModificacion(String usuarioModificacion) {
    this.usuarioModificacion = usuarioModificacion;
    return this;
  }

  public void setUsuarioModificacion(String usuarioModificacion) {
    this.usuarioModificacion = usuarioModificacion;
  }

  public ZonedDateTime getFechaModificacion() {
    return this.fechaModificacion;
  }

  public Cargue fechaModificacion(ZonedDateTime fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
    return this;
  }

  public void setFechaModificacion(ZonedDateTime fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

  public DetalleCargue getCargId() {
    return this.cargId;
  }

  public Cargue cargId(DetalleCargue detalleCargue) {
    this.setCargId(detalleCargue);
    return this;
  }

  public void setCargId(DetalleCargue detalleCargue) {
    this.cargId = detalleCargue;
  }

  public ErrorCargue getCargId() {
    return this.cargId;
  }

  public Cargue cargId(ErrorCargue errorCargue) {
    this.setCargId(errorCargue);
    return this;
  }

  public void setCargId(ErrorCargue errorCargue) {
    this.cargId = errorCargue;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Cargue)) {
      return false;
    }
    return id != null && id.equals(((Cargue) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "Cargue{" +
            "id=" + getId() +
            ", cargNroRegistros=" + getCargNroRegistros() +
            ", cargJson='" + getCargJson() + "'" +
            ", cargEntidad=" + getCargEntidad() +
            ", cargNombreArchivo='" + getCargNombreArchivo() + "'" +
            ", cargEstado='" + getCargEstado() + "'" +
            ", cargTipo='" + getCargTipo() + "'" +
            ", cargEsReproceso=" + getCargEsReproceso() +
            ", cargHash='" + getCargHash() + "'" +
            ", usuarioCreacion='" + getUsuarioCreacion() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", usuarioModificacion='" + getUsuarioModificacion() + "'" +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            "}";
    }
}
