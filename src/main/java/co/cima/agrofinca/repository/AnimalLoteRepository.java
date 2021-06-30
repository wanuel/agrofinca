package co.cima.agrofinca.repository;

import co.cima.agrofinca.domain.AnimalLote;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AnimalLote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnimalLoteRepository extends JpaRepository<AnimalLote, Long> {}
