package co.cima.agrofinca.service;

import co.cima.agrofinca.domain.TipoParametro;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TipoParametro}.
 */
public interface TipoParametroService {
  /**
   * Save a tipoParametro.
   *
   * @param tipoParametro the entity to save.
   * @return the persisted entity.
   */
  TipoParametro save(TipoParametro tipoParametro);

  /**
   * Partially updates a tipoParametro.
   *
   * @param tipoParametro the entity to update partially.
   * @return the persisted entity.
   */
  Optional<TipoParametro> partialUpdate(TipoParametro tipoParametro);

  /**
   * Get all the tipoParametros.
   *
   * @return the list of entities.
   */
  List<TipoParametro> findAll();

  /**
   * Get the "id" tipoParametro.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<TipoParametro> findOne(Long id);

  /**
   * Delete the "id" tipoParametro.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);
}
