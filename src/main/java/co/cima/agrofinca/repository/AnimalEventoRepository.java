package co.cima.agrofinca.repository;

import co.cima.agrofinca.domain.AnimalEvento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AnimalEvento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnimalEventoRepository extends JpaRepository<AnimalEvento, Long> {}
