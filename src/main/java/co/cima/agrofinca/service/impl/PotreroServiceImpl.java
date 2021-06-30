package co.cima.agrofinca.service.impl;

import co.cima.agrofinca.domain.Potrero;
import co.cima.agrofinca.repository.PotreroRepository;
import co.cima.agrofinca.service.PotreroService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Potrero}.
 */
@Service
@Transactional
public class PotreroServiceImpl implements PotreroService {

  private final Logger log = LoggerFactory.getLogger(PotreroServiceImpl.class);

  private final PotreroRepository potreroRepository;

  public PotreroServiceImpl(PotreroRepository potreroRepository) {
    this.potreroRepository = potreroRepository;
  }

  @Override
  public Potrero save(Potrero potrero) {
    log.debug("Request to save Potrero : {}", potrero);
    return potreroRepository.save(potrero);
  }

  @Override
  public Optional<Potrero> partialUpdate(Potrero potrero) {
    log.debug("Request to partially update Potrero : {}", potrero);

    return potreroRepository
      .findById(potrero.getId())
      .map(
        existingPotrero -> {
          if (potrero.getNombre() != null) {
            existingPotrero.setNombre(potrero.getNombre());
          }
          if (potrero.getDescripcion() != null) {
            existingPotrero.setDescripcion(potrero.getDescripcion());
          }
          if (potrero.getPasto() != null) {
            existingPotrero.setPasto(potrero.getPasto());
          }
          if (potrero.getArea() != null) {
            existingPotrero.setArea(potrero.getArea());
          }

          return existingPotrero;
        }
      )
      .map(potreroRepository::save);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Potrero> findAll(Pageable pageable) {
    log.debug("Request to get all Potreros");
    return potreroRepository.findAll(pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Potrero> findOne(Long id) {
    log.debug("Request to get Potrero : {}", id);
    return potreroRepository.findById(id);
  }

  @Override
  public void delete(Long id) {
    log.debug("Request to delete Potrero : {}", id);
    potreroRepository.deleteById(id);
  }
}
