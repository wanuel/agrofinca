package co.cima.agrofinca.service.impl;

import co.cima.agrofinca.domain.AnimalLote;
import co.cima.agrofinca.repository.AnimalLoteRepository;
import co.cima.agrofinca.service.AnimalLoteService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AnimalLote}.
 */
@Service
@Transactional
public class AnimalLoteServiceImpl implements AnimalLoteService {

  private final Logger log = LoggerFactory.getLogger(AnimalLoteServiceImpl.class);

  private final AnimalLoteRepository animalLoteRepository;

  public AnimalLoteServiceImpl(AnimalLoteRepository animalLoteRepository) {
    this.animalLoteRepository = animalLoteRepository;
  }

  @Override
  public AnimalLote save(AnimalLote animalLote) {
    log.debug("Request to save AnimalLote : {}", animalLote);
    return animalLoteRepository.save(animalLote);
  }

  @Override
  public Optional<AnimalLote> partialUpdate(AnimalLote animalLote) {
    log.debug("Request to partially update AnimalLote : {}", animalLote);

    return animalLoteRepository
      .findById(animalLote.getId())
      .map(
        existingAnimalLote -> {
          if (animalLote.getFechaEntrada() != null) {
            existingAnimalLote.setFechaEntrada(animalLote.getFechaEntrada());
          }
          if (animalLote.getFechaSalida() != null) {
            existingAnimalLote.setFechaSalida(animalLote.getFechaSalida());
          }

          return existingAnimalLote;
        }
      )
      .map(animalLoteRepository::save);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<AnimalLote> findAll(Pageable pageable) {
    log.debug("Request to get all AnimalLotes");
    return animalLoteRepository.findAll(pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<AnimalLote> findOne(Long id) {
    log.debug("Request to get AnimalLote : {}", id);
    return animalLoteRepository.findById(id);
  }

  @Override
  public void delete(Long id) {
    log.debug("Request to delete AnimalLote : {}", id);
    animalLoteRepository.deleteById(id);
  }
}
