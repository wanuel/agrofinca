package co.cima.agrofinca.service.impl;

import co.cima.agrofinca.domain.Parametros;
import co.cima.agrofinca.repository.ParametrosRepository;
import co.cima.agrofinca.service.ParametrosService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Parametros}.
 */
@Service
@Transactional
public class ParametrosServiceImpl implements ParametrosService {

  private final Logger log = LoggerFactory.getLogger(ParametrosServiceImpl.class);

  private final ParametrosRepository parametrosRepository;

  public ParametrosServiceImpl(ParametrosRepository parametrosRepository) {
    this.parametrosRepository = parametrosRepository;
  }

  @Override
  public Parametros save(Parametros parametros) {
    log.debug("Request to save Parametros : {}", parametros);
    return parametrosRepository.save(parametros);
  }

  @Override
  public Optional<Parametros> partialUpdate(Parametros parametros) {
    log.debug("Request to partially update Parametros : {}", parametros);

    return parametrosRepository
      .findById(parametros.getId())
      .map(
        existingParametros -> {
          if (parametros.getDescripcion() != null) {
            existingParametros.setDescripcion(parametros.getDescripcion());
          }

          return existingParametros;
        }
      )
      .map(parametrosRepository::save);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Parametros> findAll(Pageable pageable) {
    log.debug("Request to get all Parametros");
    return parametrosRepository.findAll(pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Parametros> findOne(Long id) {
    log.debug("Request to get Parametros : {}", id);
    return parametrosRepository.findById(id);
  }

  @Override
  public void delete(Long id) {
    log.debug("Request to delete Parametros : {}", id);
    parametrosRepository.deleteById(id);
  }
}
