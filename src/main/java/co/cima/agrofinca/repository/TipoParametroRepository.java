package co.cima.agrofinca.repository;

import co.cima.agrofinca.domain.TipoParametro;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TipoParametro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoParametroRepository extends JpaRepository<TipoParametro, Long> {}
