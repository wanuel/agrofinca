package co.cima.agrofinca.repository;

import co.cima.agrofinca.domain.AnimalCostos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AnimalCostos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnimalCostosRepository extends JpaRepository<AnimalCostos, Long> {}
