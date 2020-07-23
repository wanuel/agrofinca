package co.cima.agrofinca.repository;

import co.cima.agrofinca.domain.AnimalPeso;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AnimalPeso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnimalPesoRepository extends JpaRepository<AnimalPeso, Long> {
}
