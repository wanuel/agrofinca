package co.cima.agrofinca.repository;

import co.cima.agrofinca.domain.Potrero;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Potrero entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PotreroRepository extends JpaRepository<Potrero, Long> {}
