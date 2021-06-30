package co.cima.agrofinca.repository;

import co.cima.agrofinca.domain.Finca;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Finca entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FincaRepository extends JpaRepository<Finca, Long> {}
