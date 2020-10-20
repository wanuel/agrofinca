package co.cima.agrofinca.repository;

import co.cima.agrofinca.domain.Animal;
import co.cima.agrofinca.domain.vo.ListVO;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Animal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
	
	@Query("select new co.cima.agrofinca.domain.vo.ListVO(a.id, a.nombre) from Animal a order by a.id asc, a.nombre asc ")
    List<ListVO> findListVO();
}
