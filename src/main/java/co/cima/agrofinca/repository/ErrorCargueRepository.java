package co.cima.agrofinca.repository;

import co.cima.agrofinca.domain.ErrorCargue;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ErrorCargue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ErrorCargueRepository extends JpaRepository<ErrorCargue, Long> {}
