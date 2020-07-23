package co.cima.agrofinca.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A PotreroActividadAnimal.
 */
@Entity
@Table(name = "potrero_actividad_animal")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PotreroActividadAnimal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "potreroActividadAnimals", allowSetters = true)
    private Animal animal;

    @ManyToOne
    @JsonIgnoreProperties(value = "potreroActividadPastoreos", allowSetters = true)
    private PotreroActividad  potreroActividad;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Animal getAnimal() {
        return animal;
    }

    public PotreroActividadAnimal animal(Animal animal) {
        this.animal = animal;
        return this;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }


	public PotreroActividad getPotreroActividad() {
		return potreroActividad;
	}

	public void setPotreroActividad(PotreroActividad potreroActividad) {
		this.potreroActividad = potreroActividad;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PotreroActividadAnimal)) {
            return false;
        }
        return id != null && id.equals(((PotreroActividadAnimal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PotreroActividadAnimal{" +
            "id=" + getId() +
            "}";
    }
}
