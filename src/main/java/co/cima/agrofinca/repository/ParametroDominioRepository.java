package co.cima.agrofinca.repository;

import co.cima.agrofinca.domain.ParametroDominio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ParametroDominio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParametroDominioRepository extends JpaRepository<ParametroDominio, Long> {}
