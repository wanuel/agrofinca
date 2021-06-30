package co.cima.agrofinca.service.impl;

import co.cima.agrofinca.domain.PotreroActividadAnimal;
import co.cima.agrofinca.repository.PotreroActividadAnimalRepository;
import co.cima.agrofinca.service.PotreroActividadAnimalService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PotreroActividadAnimal}.
 */
@Service
@Transactional
public class PotreroActividadAnimalServiceImpl implements PotreroActividadAnimalService {

  private final Logger log = LoggerFactory.getLogger(PotreroActividadAnimalServiceImpl.class);

  private final PotreroActividadAnimalRepository potreroActividadAnimalRepository;

  public PotreroActividadAnimalServiceImpl(PotreroActividadAnimalRepository potreroActividadAnimalRepository) {
    this.potreroActividadAnimalRepository = potreroActividadAnimalRepository;
  }

  @Override
  public PotreroActividadAnimal save(PotreroActividadAnimal potreroActividadAnimal) {
    log.debug("Request to save PotreroActividadAnimal : {}", potreroActividadAnimal);
    return potreroActividadAnimalRepository.save(potreroActividadAnimal);
  }

  @Override
  public Optional<PotreroActividadAnimal> partialUpdate(PotreroActividadAnimal potreroActividadAnimal) {
    log.debug("Request to partially update PotreroActividadAnimal : {}", potreroActividadAnimal);

    return potreroActividadAnimalRepository
      .findById(potreroActividadAnimal.getId())
      .map(
        existingPotreroActividadAnimal -> {
          return existingPotreroActividadAnimal;
        }
      )
      .map(potreroActividadAnimalRepository::save);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<PotreroActividadAnimal> findAll(Pageable pageable) {
    log.debug("Request to get all PotreroActividadAnimals");
    return potreroActividadAnimalRepository.findAll(pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<PotreroActividadAnimal> findOne(Long id) {
    log.debug("Request to get PotreroActividadAnimal : {}", id);
    return potreroActividadAnimalRepository.findById(id);
  }

  @Override
  public void delete(Long id) {
    log.debug("Request to delete PotreroActividadAnimal : {}", id);
    potreroActividadAnimalRepository.deleteById(id);
  }
}
