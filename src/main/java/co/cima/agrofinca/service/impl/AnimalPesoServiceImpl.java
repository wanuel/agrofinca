package co.cima.agrofinca.service.impl;

import co.cima.agrofinca.domain.AnimalPeso;
import co.cima.agrofinca.repository.AnimalPesoRepository;
import co.cima.agrofinca.service.AnimalPesoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AnimalPeso}.
 */
@Service
@Transactional
public class AnimalPesoServiceImpl implements AnimalPesoService {

  private final Logger log = LoggerFactory.getLogger(AnimalPesoServiceImpl.class);

  private final AnimalPesoRepository animalPesoRepository;

  public AnimalPesoServiceImpl(AnimalPesoRepository animalPesoRepository) {
    this.animalPesoRepository = animalPesoRepository;
  }

  @Override
  public AnimalPeso save(AnimalPeso animalPeso) {
    log.debug("Request to save AnimalPeso : {}", animalPeso);
    return animalPesoRepository.save(animalPeso);
  }

  @Override
  public Optional<AnimalPeso> partialUpdate(AnimalPeso animalPeso) {
    log.debug("Request to partially update AnimalPeso : {}", animalPeso);

    return animalPesoRepository
      .findById(animalPeso.getId())
      .map(
        existingAnimalPeso -> {
          if (animalPeso.getFecha() != null) {
            existingAnimalPeso.setFecha(animalPeso.getFecha());
          }
          if (animalPeso.getPeso() != null) {
            existingAnimalPeso.setPeso(animalPeso.getPeso());
          }

          return existingAnimalPeso;
        }
      )
      .map(animalPesoRepository::save);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<AnimalPeso> findAll(Pageable pageable) {
    log.debug("Request to get all AnimalPesos");
    return animalPesoRepository.findAll(pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<AnimalPeso> findOne(Long id) {
    log.debug("Request to get AnimalPeso : {}", id);
    return animalPesoRepository.findById(id);
  }

  @Override
  public void delete(Long id) {
    log.debug("Request to delete AnimalPeso : {}", id);
    animalPesoRepository.deleteById(id);
  }
}
