package co.cima.agrofinca.repository;

import co.cima.agrofinca.domain.DetalleCargue;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DetalleCargue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetalleCargueRepository extends JpaRepository<DetalleCargue, Long> {}
