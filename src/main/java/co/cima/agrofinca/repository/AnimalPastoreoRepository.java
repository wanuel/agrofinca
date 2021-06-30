package co.cima.agrofinca.repository;

import co.cima.agrofinca.domain.AnimalPastoreo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AnimalPastoreo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnimalPastoreoRepository extends JpaRepository<AnimalPastoreo, Long> {}
