package co.cima.agrofinca.service;

import co.cima.agrofinca.domain.Finca;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Finca}.
 */
public interface FincaService {
  /**
   * Save a finca.
   *
   * @param finca the entity to save.
   * @return the persisted entity.
   */
  Finca save(Finca finca);

  /**
   * Partially updates a finca.
   *
   * @param finca the entity to update partially.
   * @return the persisted entity.
   */
  Optional<Finca> partialUpdate(Finca finca);

  /**
   * Get all the fincas.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<Finca> findAll(Pageable pageable);

  /**
   * Get the "id" finca.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<Finca> findOne(Long id);

  /**
   * Delete the "id" finca.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);
}
