package co.cima.agrofinca.repository;

import co.cima.agrofinca.domain.PotreroActividad;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the PotreroActividad entity.
 */
@Repository
public interface PotreroActividadRepository extends JpaRepository<PotreroActividad, Long> {

    //@Query(value = "select distinct potreroActividad from PotreroActividad potreroActividad left join fetch potreroActividad.pastoreos",
	@Query(value = "select distinct potreroActividad from PotreroActividad potreroActividad ",
        countQuery = "select count(distinct potreroActividad) from PotreroActividad potreroActividad")
    Page<PotreroActividad> findAllWithEagerRelationships(Pageable pageable);

    //@Query("select distinct potreroActividad from PotreroActividad potreroActividad left join fetch potreroActividad.pastoreos")
	@Query("select distinct potreroActividad from PotreroActividad potreroActividad ")
    List<PotreroActividad> findAllWithEagerRelationships();

    //@Query("select potreroActividad from PotreroActividad potreroActividad left join fetch potreroActividad.pastoreos where potreroActividad.id =:id")
	@Query("select potreroActividad from PotreroActividad potreroActividad  where potreroActividad.id =:id")
    Optional<PotreroActividad> findOneWithEagerRelationships(@Param("id") Long id);
}
