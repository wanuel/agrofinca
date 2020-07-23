package co.cima.agrofinca.repository;

import co.cima.agrofinca.domain.PotreroActividadAnimal;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PotreroActividadAnimal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PotreroActividadAnimalRepository extends JpaRepository<PotreroActividadAnimal, Long> {
}
