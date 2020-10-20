package co.cima.agrofinca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import co.cima.agrofinca.domain.Potrero;
import co.cima.agrofinca.domain.vo.ListVO;

/**
 * Spring Data  repository for the Potrero entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PotreroRepository extends JpaRepository<Potrero, Long> {
	
	
	@Query("select new co.cima.agrofinca.domain.vo.ListVO(p.id, p.nombre) from Potrero p order by p.finca.id asc, p.nombre asc ")
    List<ListVO> findListVO();
}
