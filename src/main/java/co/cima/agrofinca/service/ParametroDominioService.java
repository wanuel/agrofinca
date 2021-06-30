package co.cima.agrofinca.service;

import co.cima.agrofinca.domain.ParametroDominio;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ParametroDominio}.
 */
public interface ParametroDominioService {
  /**
   * Save a parametroDominio.
   *
   * @param parametroDominio the entity to save.
   * @return the persisted entity.
   */
  ParametroDominio save(ParametroDominio parametroDominio);

  /**
   * Partially updates a parametroDominio.
   *
   * @param parametroDominio the entity to update partially.
   * @return the persisted entity.
   */
  Optional<ParametroDominio> partialUpdate(ParametroDominio parametroDominio);

  /**
   * Get all the parametroDominios.
   *
   * @return the list of entities.
   */
  List<ParametroDominio> findAll();

  /**
   * Get the "id" parametroDominio.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<ParametroDominio> findOne(Long id);

  /**
   * Delete the "id" parametroDominio.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);
}
