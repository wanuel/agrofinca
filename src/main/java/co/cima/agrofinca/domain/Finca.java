package co.cima.agrofinca.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * A Finca.
 */
@Entity
@Table(name = "finca")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Finca implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "area", precision = 21, scale = 2)
    private BigDecimal area;

    @OneToMany(mappedBy = "finca")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Potrero> potreros = new HashSet<>();

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

    public Finca nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getArea() {
        return area;
    }

    public Finca area(BigDecimal area) {
        this.area = area;
        return this;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public Set<Potrero> getPotreros() {
        return potreros;
    }

    public Finca potreros(Set<Potrero> potreros) {
        this.potreros = potreros;
        return this;
    }

    public Finca addPotreros(Potrero potrero) {
        this.potreros.add(potrero);
        potrero.setFinca(this);
        return this;
    }

    public Finca removePotreros(Potrero potrero) {
        this.potreros.remove(potrero);
        potrero.setFinca(null);
        return this;
    }

    public void setPotreros(Set<Potrero> potreros) {
        this.potreros = potreros;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Finca)) {
            return false;
        }
        return id != null && id.equals(((Finca) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Finca{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", area=" + getArea() +
            "}";
    }
}
