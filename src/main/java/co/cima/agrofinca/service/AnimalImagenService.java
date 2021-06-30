package co.cima.agrofinca.service;

import co.cima.agrofinca.domain.AnimalImagen;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link AnimalImagen}.
 */
public interface AnimalImagenService {
  /**
   * Save a animalImagen.
   *
   * @param animalImagen the entity to save.
   * @return the persisted entity.
   */
  AnimalImagen save(AnimalImagen animalImagen);

  /**
   * Partially updates a animalImagen.
   *
   * @param animalImagen the entity to update partially.
   * @return the persisted entity.
   */
  Optional<AnimalImagen> partialUpdate(AnimalImagen animalImagen);

  /**
   * Get all the animalImagens.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<AnimalImagen> findAll(Pageable pageable);

  /**
   * Get the "id" animalImagen.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<AnimalImagen> findOne(Long id);

  /**
   * Delete the "id" animalImagen.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);
}
