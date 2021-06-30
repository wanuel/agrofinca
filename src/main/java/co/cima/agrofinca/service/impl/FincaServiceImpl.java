package co.cima.agrofinca.service.impl;

import co.cima.agrofinca.domain.Finca;
import co.cima.agrofinca.repository.FincaRepository;
import co.cima.agrofinca.service.FincaService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Finca}.
 */
@Service
@Transactional
public class FincaServiceImpl implements FincaService {

  private final Logger log = LoggerFactory.getLogger(FincaServiceImpl.class);

  private final FincaRepository fincaRepository;

  public FincaServiceImpl(FincaRepository fincaRepository) {
    this.fincaRepository = fincaRepository;
  }

  @Override
  public Finca save(Finca finca) {
    log.debug("Request to save Finca : {}", finca);
    return fincaRepository.save(finca);
  }

  @Override
  public Optional<Finca> partialUpdate(Finca finca) {
    log.debug("Request to partially update Finca : {}", finca);

    return fincaRepository
      .findById(finca.getId())
      .map(
        existingFinca -> {
          if (finca.getNombre() != null) {
            existingFinca.setNombre(finca.getNombre());
          }
          if (finca.getArea() != null) {
            existingFinca.setArea(finca.getArea());
          }

          return existingFinca;
        }
      )
      .map(fincaRepository::save);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Finca> findAll(Pageable pageable) {
    log.debug("Request to get all Fincas");
    return fincaRepository.findAll(pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Finca> findOne(Long id) {
    log.debug("Request to get Finca : {}", id);
    return fincaRepository.findById(id);
  }

  @Override
  public void delete(Long id) {
    log.debug("Request to delete Finca : {}", id);
    fincaRepository.deleteById(id);
  }
}
