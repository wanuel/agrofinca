package co.cima.agrofinca.repository;

import co.cima.agrofinca.domain.Annimal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Annimal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnnimalRepository extends JpaRepository<Annimal, Long> {}
