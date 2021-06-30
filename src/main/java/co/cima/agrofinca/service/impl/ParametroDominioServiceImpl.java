package co.cima.agrofinca.service.impl;

import co.cima.agrofinca.domain.ParametroDominio;
import co.cima.agrofinca.repository.ParametroDominioRepository;
import co.cima.agrofinca.service.ParametroDominioService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ParametroDominio}.
 */
@Service
@Transactional
public class ParametroDominioServiceImpl implements ParametroDominioService {

  private final Logger log = LoggerFactory.getLogger(ParametroDominioServiceImpl.class);

  private final ParametroDominioRepository parametroDominioRepository;

  public ParametroDominioServiceImpl(ParametroDominioRepository parametroDominioRepository) {
    this.parametroDominioRepository = parametroDominioRepository;
  }

  @Override
  public ParametroDominio save(ParametroDominio parametroDominio) {
    log.debug("Request to save ParametroDominio : {}", parametroDominio);
    return parametroDominioRepository.save(parametroDominio);
  }

  @Override
  public Optional<ParametroDominio> partialUpdate(ParametroDominio parametroDominio) {
    log.debug("Request to partially update ParametroDominio : {}", parametroDominio);

    return parametroDominioRepository
      .findById(parametroDominio.getId())
      .map(
        existingParametroDominio -> {
          if (parametroDominio.getPadoId() != null) {
            existingParametroDominio.setPadoId(parametroDominio.getPadoId());
          }
          if (parametroDominio.getPadoDescripcion() != null) {
            existingParametroDominio.setPadoDescripcion(parametroDominio.getPadoDescripcion());
          }

          return existingParametroDominio;
        }
      )
      .map(parametroDominioRepository::save);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ParametroDominio> findAll() {
    log.debug("Request to get all ParametroDominios");
    return parametroDominioRepository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<ParametroDominio> findOne(Long id) {
    log.debug("Request to get ParametroDominio : {}", id);
    return parametroDominioRepository.findById(id);
  }

  @Override
  public void delete(Long id) {
    log.debug("Request to delete ParametroDominio : {}", id);
    parametroDominioRepository.deleteById(id);
  }
}
