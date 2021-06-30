package co.cima.agrofinca.service;

import co.cima.agrofinca.domain.ErrorCargue;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ErrorCargue}.
 */
public interface ErrorCargueService {
  /**
   * Save a errorCargue.
   *
   * @param errorCargue the entity to save.
   * @return the persisted entity.
   */
  ErrorCargue save(ErrorCargue errorCargue);

  /**
   * Partially updates a errorCargue.
   *
   * @param errorCargue the entity to update partially.
   * @return the persisted entity.
   */
  Optional<ErrorCargue> partialUpdate(ErrorCargue errorCargue);

  /**
   * Get all the errorCargues.
   *
   * @return the list of entities.
   */
  List<ErrorCargue> findAll();

  /**
   * Get the "id" errorCargue.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<ErrorCargue> findOne(Long id);

  /**
   * Delete the "id" errorCargue.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);
}
