package co.cima.agrofinca.service.impl;

import co.cima.agrofinca.domain.AnimalVacunas;
import co.cima.agrofinca.repository.AnimalVacunasRepository;
import co.cima.agrofinca.service.AnimalVacunasService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AnimalVacunas}.
 */
@Service
@Transactional
public class AnimalVacunasServiceImpl implements AnimalVacunasService {

  private final Logger log = LoggerFactory.getLogger(AnimalVacunasServiceImpl.class);

  private final AnimalVacunasRepository animalVacunasRepository;

  public AnimalVacunasServiceImpl(AnimalVacunasRepository animalVacunasRepository) {
    this.animalVacunasRepository = animalVacunasRepository;
  }

  @Override
  public AnimalVacunas save(AnimalVacunas animalVacunas) {
    log.debug("Request to save AnimalVacunas : {}", animalVacunas);
    return animalVacunasRepository.save(animalVacunas);
  }

  @Override
  public Optional<AnimalVacunas> partialUpdate(AnimalVacunas animalVacunas) {
    log.debug("Request to partially update AnimalVacunas : {}", animalVacunas);

    return animalVacunasRepository
      .findById(animalVacunas.getId())
      .map(
        existingAnimalVacunas -> {
          if (animalVacunas.getFecha() != null) {
            existingAnimalVacunas.setFecha(animalVacunas.getFecha());
          }
          if (animalVacunas.getNombre() != null) {
            existingAnimalVacunas.setNombre(animalVacunas.getNombre());
          }
          if (animalVacunas.getLaboratorio() != null) {
            existingAnimalVacunas.setLaboratorio(animalVacunas.getLaboratorio());
          }
          if (animalVacunas.getDosis() != null) {
            existingAnimalVacunas.setDosis(animalVacunas.getDosis());
          }

          return existingAnimalVacunas;
        }
      )
      .map(animalVacunasRepository::save);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<AnimalVacunas> findAll(Pageable pageable) {
    log.debug("Request to get all AnimalVacunas");
    return animalVacunasRepository.findAll(pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<AnimalVacunas> findOne(Long id) {
    log.debug("Request to get AnimalVacunas : {}", id);
    return animalVacunasRepository.findById(id);
  }

  @Override
  public void delete(Long id) {
    log.debug("Request to delete AnimalVacunas : {}", id);
    animalVacunasRepository.deleteById(id);
  }
}
