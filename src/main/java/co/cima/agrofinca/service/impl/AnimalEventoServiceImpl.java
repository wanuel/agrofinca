package co.cima.agrofinca.service.impl;

import co.cima.agrofinca.domain.AnimalEvento;
import co.cima.agrofinca.repository.AnimalEventoRepository;
import co.cima.agrofinca.service.AnimalEventoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AnimalEvento}.
 */
@Service
@Transactional
public class AnimalEventoServiceImpl implements AnimalEventoService {

  private final Logger log = LoggerFactory.getLogger(AnimalEventoServiceImpl.class);

  private final AnimalEventoRepository animalEventoRepository;

  public AnimalEventoServiceImpl(AnimalEventoRepository animalEventoRepository) {
    this.animalEventoRepository = animalEventoRepository;
  }

  @Override
  public AnimalEvento save(AnimalEvento animalEvento) {
    log.debug("Request to save AnimalEvento : {}", animalEvento);
    return animalEventoRepository.save(animalEvento);
  }

  @Override
  public Optional<AnimalEvento> partialUpdate(AnimalEvento animalEvento) {
    log.debug("Request to partially update AnimalEvento : {}", animalEvento);

    return animalEventoRepository
      .findById(animalEvento.getId())
      .map(
        existingAnimalEvento -> {
          if (animalEvento.getFecha() != null) {
            existingAnimalEvento.setFecha(animalEvento.getFecha());
          }

          return existingAnimalEvento;
        }
      )
      .map(animalEventoRepository::save);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<AnimalEvento> findAll(Pageable pageable) {
    log.debug("Request to get all AnimalEventos");
    return animalEventoRepository.findAll(pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<AnimalEvento> findOne(Long id) {
    log.debug("Request to get AnimalEvento : {}", id);
    return animalEventoRepository.findById(id);
  }

  @Override
  public void delete(Long id) {
    log.debug("Request to delete AnimalEvento : {}", id);
    animalEventoRepository.deleteById(id);
  }
}
