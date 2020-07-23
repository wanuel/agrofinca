package co.cima.agrofinca.repository;

import co.cima.agrofinca.domain.AnimalVacunas;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AnimalVacunas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnimalVacunasRepository extends JpaRepository<AnimalVacunas, Long> {
}
