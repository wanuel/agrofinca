package co.cima.agrofinca.service;

import co.cima.agrofinca.domain.Potrero;
import co.cima.agrofinca.domain.vo.ListVO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Potrero}.
 */
public interface PotreroService {

    /**
     * Save a potrero.
     *
     * @param potrero the entity to save.
     * @return the persisted entity.
     */
    Potrero save(Potrero potrero);

    /**
     * Get all the potreros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Potrero> findAll(Pageable pageable);


    /**
     * Get the "id" potrero.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Potrero> findOne(Long id);

    /**
     * Delete the "id" potrero.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	List<Potrero> findAll();

	List<ListVO> findListVO();
}
