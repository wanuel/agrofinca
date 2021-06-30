package co.cima.agrofinca.service;

import co.cima.agrofinca.domain.Persona;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Persona}.
 */
public interface PersonaService {
  /**
   * Save a persona.
   *
   * @param persona the entity to save.
   * @return the persisted entity.
   */
  Persona save(Persona persona);

  /**
   * Partially updates a persona.
   *
   * @param persona the entity to update partially.
   * @return the persisted entity.
   */
  Optional<Persona> partialUpdate(Persona persona);

  /**
   * Get all the personas.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<Persona> findAll(Pageable pageable);

  /**
   * Get the "id" persona.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<Persona> findOne(Long id);

  /**
   * Delete the "id" persona.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);
}
