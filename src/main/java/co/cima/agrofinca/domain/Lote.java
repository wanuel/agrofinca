package co.cima.agrofinca.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import co.cima.agrofinca.domain.enumeration.TipoLoteEnum;

/**
 * A Lote.
 */
@Entity
@Table(name = "lote")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Lote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoLoteEnum tipo;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @NotNull
    @Column(name = "numero_animales", nullable = false)
    private Integer numeroAnimales;

    @OneToMany(mappedBy = "lote")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AnimalLote> animales = new HashSet<>();
    
    @OneToMany(mappedBy = "lote")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PotreroActividad> pastoreos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Lote nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoLoteEnum getTipo() {
        return tipo;
    }

    public Lote tipo(TipoLoteEnum tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoLoteEnum tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Lote fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getNumeroAnimales() {
        return numeroAnimales;
    }

    public Lote numeroAnimales(Integer numeroAnimales) {
        this.numeroAnimales = numeroAnimales;
        return this;
    }

    public void setNumeroAnimales(Integer numeroAnimales) {
        this.numeroAnimales = numeroAnimales;
    }

    public Set<AnimalLote> getAnimales() {
        return animales;
    }

    public Lote animales(Set<AnimalLote> animalLotes) {
        this.animales = animalLotes;
        return this;
    }

    public Lote addAnimales(AnimalLote animalLote) {
        this.animales.add(animalLote);
        animalLote.setLote(this);
        return this;
    }

    public Lote removeAnimales(AnimalLote animalLote) {
        this.animales.remove(animalLote);
        animalLote.setLote(null);
        return this;
    }

    public void setAnimales(Set<AnimalLote> animalLotes) {
        this.animales = animalLotes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
    
    public Set<PotreroActividad> getPastoreos() {
		return pastoreos;
	}

	public void setPastoreos(Set<PotreroActividad> pastoreos) {
		this.pastoreos = pastoreos;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lote)) {
            return false;
        }
        return id != null && id.equals(((Lote) o).id);
    }

    

	@Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Lote{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", numeroAnimales=" + getNumeroAnimales() +
            "}";
    }
}
