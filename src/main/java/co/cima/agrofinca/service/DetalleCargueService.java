package co.cima.agrofinca.service;

import co.cima.agrofinca.domain.DetalleCargue;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DetalleCargue}.
 */
public interface DetalleCargueService {
  /**
   * Save a detalleCargue.
   *
   * @param detalleCargue the entity to save.
   * @return the persisted entity.
   */
  DetalleCargue save(DetalleCargue detalleCargue);

  /**
   * Partially updates a detalleCargue.
   *
   * @param detalleCargue the entity to update partially.
   * @return the persisted entity.
   */
  Optional<DetalleCargue> partialUpdate(DetalleCargue detalleCargue);

  /**
   * Get all the detalleCargues.
   *
   * @return the list of entities.
   */
  List<DetalleCargue> findAll();

  /**
   * Get the "id" detalleCargue.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<DetalleCargue> findOne(Long id);

  /**
   * Delete the "id" detalleCargue.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);
}
