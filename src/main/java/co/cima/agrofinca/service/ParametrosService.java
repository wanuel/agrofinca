package co.cima.agrofinca.service;

import co.cima.agrofinca.domain.Parametros;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Parametros}.
 */
public interface ParametrosService {
  /**
   * Save a parametros.
   *
   * @param parametros the entity to save.
   * @return the persisted entity.
   */
  Parametros save(Parametros parametros);

  /**
   * Partially updates a parametros.
   *
   * @param parametros the entity to update partially.
   * @return the persisted entity.
   */
  Optional<Parametros> partialUpdate(Parametros parametros);

  /**
   * Get all the parametros.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<Parametros> findAll(Pageable pageable);

  /**
   * Get the "id" parametros.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<Parametros> findOne(Long id);

  /**
   * Delete the "id" parametros.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);
}
