package co.cima.agrofinca.repository;

import co.cima.agrofinca.domain.Parametros;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Parametros entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParametrosRepository extends JpaRepository<Parametros, Long> {}
