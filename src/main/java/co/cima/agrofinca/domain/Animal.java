package co.cima.agrofinca.domain;

import co.cima.agrofinca.domain.enumeration.ESTADOANIMAL;
import co.cima.agrofinca.domain.enumeration.SEXO;
import co.cima.agrofinca.domain.enumeration.SINO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Animal.
 */
@Entity
@Table(name = "animal")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Animal implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  private Long id;

  @Column(name = "nombre")
  private String nombre;

  @Column(name = "caracterizacion")
  private String caracterizacion;

  @Enumerated(EnumType.STRING)
  @Column(name = "hierro")
  private SINO hierro;

  @Column(name = "fecha_nacimiento")
  private LocalDate fechaNacimiento;

  @Column(name = "fecha_compra")
  private LocalDate fechaCompra;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "sexo", nullable = false)
  private SEXO sexo;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "castrado", nullable = false)
  private SINO castrado;

  @Column(name = "fecha_castracion")
  private LocalDate fechaCastracion;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "estado", nullable = false)
  private ESTADOANIMAL estado;

  @OneToMany(mappedBy = "animal")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @JsonIgnoreProperties(value = { "animal" }, allowSetters = true)
  private Set<AnimalImagen> imagenes = new HashSet<>();

  @OneToMany(mappedBy = "animal")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @JsonIgnoreProperties(value = { "animal", "tipo" }, allowSetters = true)
  private Set<AnimalVacunas> vacunas = new HashSet<>();

  @OneToMany(mappedBy = "animal")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @JsonIgnoreProperties(value = { "animal", "evento" }, allowSetters = true)
  private Set<AnimalPeso> pesos = new HashSet<>();

  @OneToMany(mappedBy = "animal")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @JsonIgnoreProperties(value = { "animal", "evento" }, allowSetters = true)
  private Set<AnimalEvento> eventos = new HashSet<>();

  @OneToMany(mappedBy = "animal")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @JsonIgnoreProperties(value = { "animal", "evento" }, allowSetters = true)
  private Set<AnimalCostos> costos = new HashSet<>();

  @ManyToOne
  @JsonIgnoreProperties(
    value = { "costos", "eventos", "animalesTipos", "animalesRazas", "pesos", "vacunas", "parametros", "padre" },
    allowSetters = true
  )
  private Parametros tipo;

  @ManyToOne
  @JsonIgnoreProperties(
    value = { "costos", "eventos", "animalesTipos", "animalesRazas", "pesos", "vacunas", "parametros", "padre" },
    allowSetters = true
  )
  private Parametros raza;

  @ManyToMany(mappedBy = "animals")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @JsonIgnoreProperties(value = { "animals", "potrero" }, allowSetters = true)
  private Set<PotreroActividad> potreros = new HashSet<>();

  // jhipster-needle-entity-add-field - JHipster will add fields here
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Animal id(Long id) {
    this.id = id;
    return this;
  }

  public String getNombre() {
    return this.nombre;
  }

  public Animal nombre(String nombre) {
    this.nombre = nombre;
    return this;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getCaracterizacion() {
    return this.caracterizacion;
  }

  public Animal caracterizacion(String caracterizacion) {
    this.caracterizacion = caracterizacion;
    return this;
  }

  public void setCaracterizacion(String caracterizacion) {
    this.caracterizacion = caracterizacion;
  }

  public SINO getHierro() {
    return this.hierro;
  }

  public Animal hierro(SINO hierro) {
    this.hierro = hierro;
    return this;
  }

  public void setHierro(SINO hierro) {
    this.hierro = hierro;
  }

  public LocalDate getFechaNacimiento() {
    return this.fechaNacimiento;
  }

  public Animal fechaNacimiento(LocalDate fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
    return this;
  }

  public void setFechaNacimiento(LocalDate fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
  }

  public LocalDate getFechaCompra() {
    return this.fechaCompra;
  }

  public Animal fechaCompra(LocalDate fechaCompra) {
    this.fechaCompra = fechaCompra;
    return this;
  }

  public void setFechaCompra(LocalDate fechaCompra) {
    this.fechaCompra = fechaCompra;
  }

  public SEXO getSexo() {
    return this.sexo;
  }

  public Animal sexo(SEXO sexo) {
    this.sexo = sexo;
    return this;
  }

  public void setSexo(SEXO sexo) {
    this.sexo = sexo;
  }

  public SINO getCastrado() {
    return this.castrado;
  }

  public Animal castrado(SINO castrado) {
    this.castrado = castrado;
    return this;
  }

  public void setCastrado(SINO castrado) {
    this.castrado = castrado;
  }

  public LocalDate getFechaCastracion() {
    return this.fechaCastracion;
  }

  public Animal fechaCastracion(LocalDate fechaCastracion) {
    this.fechaCastracion = fechaCastracion;
    return this;
  }

  public void setFechaCastracion(LocalDate fechaCastracion) {
    this.fechaCastracion = fechaCastracion;
  }

  public ESTADOANIMAL getEstado() {
    return this.estado;
  }

  public Animal estado(ESTADOANIMAL estado) {
    this.estado = estado;
    return this;
  }

  public void setEstado(ESTADOANIMAL estado) {
    this.estado = estado;
  }

  public Set<AnimalImagen> getImagenes() {
    return this.imagenes;
  }

  public Animal imagenes(Set<AnimalImagen> animalImagens) {
    this.setImagenes(animalImagens);
    return this;
  }

  public Animal addImagenes(AnimalImagen animalImagen) {
    this.imagenes.add(animalImagen);
    animalImagen.setAnimal(this);
    return this;
  }

  public Animal removeImagenes(AnimalImagen animalImagen) {
    this.imagenes.remove(animalImagen);
    animalImagen.setAnimal(null);
    return this;
  }

  public void setImagenes(Set<AnimalImagen> animalImagens) {
    if (this.imagenes != null) {
      this.imagenes.forEach(i -> i.setAnimal(null));
    }
    if (animalImagens != null) {
      animalImagens.forEach(i -> i.setAnimal(this));
    }
    this.imagenes = animalImagens;
  }

  public Set<AnimalVacunas> getVacunas() {
    return this.vacunas;
  }

  public Animal vacunas(Set<AnimalVacunas> animalVacunas) {
    this.setVacunas(animalVacunas);
    return this;
  }

  public Animal addVacunas(AnimalVacunas animalVacunas) {
    this.vacunas.add(animalVacunas);
    animalVacunas.setAnimal(this);
    return this;
  }

  public Animal removeVacunas(AnimalVacunas animalVacunas) {
    this.vacunas.remove(animalVacunas);
    animalVacunas.setAnimal(null);
    return this;
  }

  public void setVacunas(Set<AnimalVacunas> animalVacunas) {
    if (this.vacunas != null) {
      this.vacunas.forEach(i -> i.setAnimal(null));
    }
    if (animalVacunas != null) {
      animalVacunas.forEach(i -> i.setAnimal(this));
    }
    this.vacunas = animalVacunas;
  }

  public Set<AnimalPeso> getPesos() {
    return this.pesos;
  }

  public Animal pesos(Set<AnimalPeso> animalPesos) {
    this.setPesos(animalPesos);
    return this;
  }

  public Animal addPesos(AnimalPeso animalPeso) {
    this.pesos.add(animalPeso);
    animalPeso.setAnimal(this);
    return this;
  }

  public Animal removePesos(AnimalPeso animalPeso) {
    this.pesos.remove(animalPeso);
    animalPeso.setAnimal(null);
    return this;
  }

  public void setPesos(Set<AnimalPeso> animalPesos) {
    if (this.pesos != null) {
      this.pesos.forEach(i -> i.setAnimal(null));
    }
    if (animalPesos != null) {
      animalPesos.forEach(i -> i.setAnimal(this));
    }
    this.pesos = animalPesos;
  }

  public Set<AnimalEvento> getEventos() {
    return this.eventos;
  }

  public Animal eventos(Set<AnimalEvento> animalEventos) {
    this.setEventos(animalEventos);
    return this;
  }

  public Animal addEventos(AnimalEvento animalEvento) {
    this.eventos.add(animalEvento);
    animalEvento.setAnimal(this);
    return this;
  }

  public Animal removeEventos(AnimalEvento animalEvento) {
    this.eventos.remove(animalEvento);
    animalEvento.setAnimal(null);
    return this;
  }

  public void setEventos(Set<AnimalEvento> animalEventos) {
    if (this.eventos != null) {
      this.eventos.forEach(i -> i.setAnimal(null));
    }
    if (animalEventos != null) {
      animalEventos.forEach(i -> i.setAnimal(this));
    }
    this.eventos = animalEventos;
  }

  public Set<AnimalCostos> getCostos() {
    return this.costos;
  }

  public Animal costos(Set<AnimalCostos> animalCostos) {
    this.setCostos(animalCostos);
    return this;
  }

  public Animal addCostos(AnimalCostos animalCostos) {
    this.costos.add(animalCostos);
    animalCostos.setAnimal(this);
    return this;
  }

  public Animal removeCostos(AnimalCostos animalCostos) {
    this.costos.remove(animalCostos);
    animalCostos.setAnimal(null);
    return this;
  }

  public void setCostos(Set<AnimalCostos> animalCostos) {
    if (this.costos != null) {
      this.costos.forEach(i -> i.setAnimal(null));
    }
    if (animalCostos != null) {
      animalCostos.forEach(i -> i.setAnimal(this));
    }
    this.costos = animalCostos;
  }

  public Parametros getTipo() {
    return this.tipo;
  }

  public Animal tipo(Parametros parametros) {
    this.setTipo(parametros);
    return this;
  }

  public void setTipo(Parametros parametros) {
    this.tipo = parametros;
  }

  public Parametros getRaza() {
    return this.raza;
  }

  public Animal raza(Parametros parametros) {
    this.setRaza(parametros);
    return this;
  }

  public void setRaza(Parametros parametros) {
    this.raza = parametros;
  }

  public Set<PotreroActividad> getPotreros() {
    return this.potreros;
  }

  public Animal potreros(Set<PotreroActividad> potreroActividads) {
    this.setPotreros(potreroActividads);
    return this;
  }

  public Animal addPotrero(PotreroActividad potreroActividad) {
    this.potreros.add(potreroActividad);
    potreroActividad.getAnimals().add(this);
    return this;
  }

  public Animal removePotrero(PotreroActividad potreroActividad) {
    this.potreros.remove(potreroActividad);
    potreroActividad.getAnimals().remove(this);
    return this;
  }

  public void setPotreros(Set<PotreroActividad> potreroActividads) {
    if (this.potreros != null) {
      this.potreros.forEach(i -> i.removeAnimal(this));
    }
    if (potreroActividads != null) {
      potreroActividads.forEach(i -> i.addAnimal(this));
    }
    this.potreros = potreroActividads;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Animal)) {
      return false;
    }
    return id != null && id.equals(((Animal) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "Animal{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", caracterizacion='" + getCaracterizacion() + "'" +
            ", hierro='" + getHierro() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", fechaCompra='" + getFechaCompra() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", castrado='" + getCastrado() + "'" +
            ", fechaCastracion='" + getFechaCastracion() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
