package co.cima.agrofinca.service.impl;

import co.cima.agrofinca.domain.Animal;
import co.cima.agrofinca.repository.AnimalRepository;
import co.cima.agrofinca.service.AnimalService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Animal}.
 */
@Service
@Transactional
public class AnimalServiceImpl implements AnimalService {

  private final Logger log = LoggerFactory.getLogger(AnimalServiceImpl.class);

  private final AnimalRepository animalRepository;

  public AnimalServiceImpl(AnimalRepository animalRepository) {
    this.animalRepository = animalRepository;
  }

  @Override
  public Animal save(Animal animal) {
    log.debug("Request to save Animal : {}", animal);
    return animalRepository.save(animal);
  }

  @Override
  public Optional<Animal> partialUpdate(Animal animal) {
    log.debug("Request to partially update Animal : {}", animal);

    return animalRepository
      .findById(animal.getId())
      .map(
        existingAnimal -> {
          if (animal.getNombre() != null) {
            existingAnimal.setNombre(animal.getNombre());
          }
          if (animal.getCaracterizacion() != null) {
            existingAnimal.setCaracterizacion(animal.getCaracterizacion());
          }
          if (animal.getHierro() != null) {
            existingAnimal.setHierro(animal.getHierro());
          }
          if (animal.getFechaNacimiento() != null) {
            existingAnimal.setFechaNacimiento(animal.getFechaNacimiento());
          }
          if (animal.getFechaCompra() != null) {
            existingAnimal.setFechaCompra(animal.getFechaCompra());
          }
          if (animal.getSexo() != null) {
            existingAnimal.setSexo(animal.getSexo());
          }
          if (animal.getCastrado() != null) {
            existingAnimal.setCastrado(animal.getCastrado());
          }
          if (animal.getFechaCastracion() != null) {
            existingAnimal.setFechaCastracion(animal.getFechaCastracion());
          }
          if (animal.getEstado() != null) {
            existingAnimal.setEstado(animal.getEstado());
          }

          return existingAnimal;
        }
      )
      .map(animalRepository::save);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Animal> findAll(Pageable pageable) {
    log.debug("Request to get all Animals");
    return animalRepository.findAll(pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Animal> findOne(Long id) {
    log.debug("Request to get Animal : {}", id);
    return animalRepository.findById(id);
  }

  @Override
  public void delete(Long id) {
    log.debug("Request to delete Animal : {}", id);
    animalRepository.deleteById(id);
  }
}
