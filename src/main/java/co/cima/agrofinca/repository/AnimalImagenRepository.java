package co.cima.agrofinca.repository;

import co.cima.agrofinca.domain.AnimalImagen;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AnimalImagen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnimalImagenRepository extends JpaRepository<AnimalImagen, Long> {}
