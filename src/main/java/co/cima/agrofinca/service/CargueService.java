package co.cima.agrofinca.service;

import co.cima.agrofinca.domain.Cargue;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Cargue}.
 */
public interface CargueService {
  /**
   * Save a cargue.
   *
   * @param cargue the entity to save.
   * @return the persisted entity.
   */
  Cargue save(Cargue cargue);

  /**
   * Partially updates a cargue.
   *
   * @param cargue the entity to update partially.
   * @return the persisted entity.
   */
  Optional<Cargue> partialUpdate(Cargue cargue);

  /**
   * Get all the cargues.
   *
   * @return the list of entities.
   */
  List<Cargue> findAll();

  /**
   * Get the "id" cargue.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<Cargue> findOne(Long id);

  /**
   * Delete the "id" cargue.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);
}
