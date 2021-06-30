package co.cima.agrofinca.repository;

import co.cima.agrofinca.domain.PotreroActividad;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PotreroActividad entity.
 */
@Repository
public interface PotreroActividadRepository extends JpaRepository<PotreroActividad, Long> {
  @Query(
    value = "select distinct potreroActividad from PotreroActividad potreroActividad left join fetch potreroActividad.animals",
    countQuery = "select count(distinct potreroActividad) from PotreroActividad potreroActividad"
  )
  Page<PotreroActividad> findAllWithEagerRelationships(Pageable pageable);

  @Query("select distinct potreroActividad from PotreroActividad potreroActividad left join fetch potreroActividad.animals")
  List<PotreroActividad> findAllWithEagerRelationships();

  @Query(
    "select potreroActividad from PotreroActividad potreroActividad left join fetch potreroActividad.animals where potreroActividad.id =:id"
  )
  Optional<PotreroActividad> findOneWithEagerRelationships(@Param("id") Long id);
}
