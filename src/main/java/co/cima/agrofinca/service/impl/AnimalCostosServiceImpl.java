package co.cima.agrofinca.service.impl;

import co.cima.agrofinca.domain.AnimalCostos;
import co.cima.agrofinca.repository.AnimalCostosRepository;
import co.cima.agrofinca.service.AnimalCostosService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AnimalCostos}.
 */
@Service
@Transactional
public class AnimalCostosServiceImpl implements AnimalCostosService {

  private final Logger log = LoggerFactory.getLogger(AnimalCostosServiceImpl.class);

  private final AnimalCostosRepository animalCostosRepository;

  public AnimalCostosServiceImpl(AnimalCostosRepository animalCostosRepository) {
    this.animalCostosRepository = animalCostosRepository;
  }

  @Override
  public AnimalCostos save(AnimalCostos animalCostos) {
    log.debug("Request to save AnimalCostos : {}", animalCostos);
    return animalCostosRepository.save(animalCostos);
  }

  @Override
  public Optional<AnimalCostos> partialUpdate(AnimalCostos animalCostos) {
    log.debug("Request to partially update AnimalCostos : {}", animalCostos);

    return animalCostosRepository
      .findById(animalCostos.getId())
      .map(
        existingAnimalCostos -> {
          if (animalCostos.getFecha() != null) {
            existingAnimalCostos.setFecha(animalCostos.getFecha());
          }
          if (animalCostos.getValor() != null) {
            existingAnimalCostos.setValor(animalCostos.getValor());
          }

          return existingAnimalCostos;
        }
      )
      .map(animalCostosRepository::save);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<AnimalCostos> findAll(Pageable pageable) {
    log.debug("Request to get all AnimalCostos");
    return animalCostosRepository.findAll(pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<AnimalCostos> findOne(Long id) {
    log.debug("Request to get AnimalCostos : {}", id);
    return animalCostosRepository.findById(id);
  }

  @Override
  public void delete(Long id) {
    log.debug("Request to delete AnimalCostos : {}", id);
    animalCostosRepository.deleteById(id);
  }
}
