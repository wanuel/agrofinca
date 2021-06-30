package co.cima.agrofinca.repository;

import co.cima.agrofinca.domain.Cargue;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Cargue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CargueRepository extends JpaRepository<Cargue, Long> {}
