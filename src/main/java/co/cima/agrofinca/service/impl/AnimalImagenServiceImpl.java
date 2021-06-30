package co.cima.agrofinca.service.impl;

import co.cima.agrofinca.domain.AnimalImagen;
import co.cima.agrofinca.repository.AnimalImagenRepository;
import co.cima.agrofinca.service.AnimalImagenService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AnimalImagen}.
 */
@Service
@Transactional
public class AnimalImagenServiceImpl implements AnimalImagenService {

  private final Logger log = LoggerFactory.getLogger(AnimalImagenServiceImpl.class);

  private final AnimalImagenRepository animalImagenRepository;

  public AnimalImagenServiceImpl(AnimalImagenRepository animalImagenRepository) {
    this.animalImagenRepository = animalImagenRepository;
  }

  @Override
  public AnimalImagen save(AnimalImagen animalImagen) {
    log.debug("Request to save AnimalImagen : {}", animalImagen);
    return animalImagenRepository.save(animalImagen);
  }

  @Override
  public Optional<AnimalImagen> partialUpdate(AnimalImagen animalImagen) {
    log.debug("Request to partially update AnimalImagen : {}", animalImagen);

    return animalImagenRepository
      .findById(animalImagen.getId())
      .map(
        existingAnimalImagen -> {
          if (animalImagen.getFecha() != null) {
            existingAnimalImagen.setFecha(animalImagen.getFecha());
          }
          if (animalImagen.getNota() != null) {
            existingAnimalImagen.setNota(animalImagen.getNota());
          }
          if (animalImagen.getImagen() != null) {
            existingAnimalImagen.setImagen(animalImagen.getImagen());
          }
          if (animalImagen.getImagenContentType() != null) {
            existingAnimalImagen.setImagenContentType(animalImagen.getImagenContentType());
          }

          return existingAnimalImagen;
        }
      )
      .map(animalImagenRepository::save);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<AnimalImagen> findAll(Pageable pageable) {
    log.debug("Request to get all AnimalImagens");
    return animalImagenRepository.findAll(pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<AnimalImagen> findOne(Long id) {
    log.debug("Request to get AnimalImagen : {}", id);
    return animalImagenRepository.findById(id);
  }

  @Override
  public void delete(Long id) {
    log.debug("Request to delete AnimalImagen : {}", id);
    animalImagenRepository.deleteById(id);
  }
}
