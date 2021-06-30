package co.cima.agrofinca.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AnimalImagen.
 */
@Entity
@Table(name = "animal_imagen")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AnimalImagen implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  private Long id;

  @NotNull
  @Column(name = "fecha", nullable = false)
  private LocalDate fecha;

  @Column(name = "nota")
  private String nota;

  @Lob
  @Column(name = "imagen", nullable = false)
  private byte[] imagen;

  @Column(name = "imagen_content_type", nullable = false)
  private String imagenContentType;

  @ManyToOne
  @JsonIgnoreProperties(value = { "imagenes", "vacunas", "pesos", "eventos", "costos", "tipo", "raza", "potreros" }, allowSetters = true)
  private Animal animal;

  // jhipster-needle-entity-add-field - JHipster will add fields here
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public AnimalImagen id(Long id) {
    this.id = id;
    return this;
  }

  public LocalDate getFecha() {
    return this.fecha;
  }

  public AnimalImagen fecha(LocalDate fecha) {
    this.fecha = fecha;
    return this;
  }

  public void setFecha(LocalDate fecha) {
    this.fecha = fecha;
  }

  public String getNota() {
    return this.nota;
  }

  public AnimalImagen nota(String nota) {
    this.nota = nota;
    return this;
  }

  public void setNota(String nota) {
    this.nota = nota;
  }

  public byte[] getImagen() {
    return this.imagen;
  }

  public AnimalImagen imagen(byte[] imagen) {
    this.imagen = imagen;
    return this;
  }

  public void setImagen(byte[] imagen) {
    this.imagen = imagen;
  }

  public String getImagenContentType() {
    return this.imagenContentType;
  }

  public AnimalImagen imagenContentType(String imagenContentType) {
    this.imagenContentType = imagenContentType;
    return this;
  }

  public void setImagenContentType(String imagenContentType) {
    this.imagenContentType = imagenContentType;
  }

  public Animal getAnimal() {
    return this.animal;
  }

  public AnimalImagen animal(Animal animal) {
    this.setAnimal(animal);
    return this;
  }

  public void setAnimal(Animal animal) {
    this.animal = animal;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AnimalImagen)) {
      return false;
    }
    return id != null && id.equals(((AnimalImagen) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "AnimalImagen{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", nota='" + getNota() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", imagenContentType='" + getImagenContentType() + "'" +
            "}";
    }
}
