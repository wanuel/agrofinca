package co.cima.agrofinca.service.impl;

import co.cima.agrofinca.domain.TipoParametro;
import co.cima.agrofinca.repository.TipoParametroRepository;
import co.cima.agrofinca.service.TipoParametroService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TipoParametro}.
 */
@Service
@Transactional
public class TipoParametroServiceImpl implements TipoParametroService {

  private final Logger log = LoggerFactory.getLogger(TipoParametroServiceImpl.class);

  private final TipoParametroRepository tipoParametroRepository;

  public TipoParametroServiceImpl(TipoParametroRepository tipoParametroRepository) {
    this.tipoParametroRepository = tipoParametroRepository;
  }

  @Override
  public TipoParametro save(TipoParametro tipoParametro) {
    log.debug("Request to save TipoParametro : {}", tipoParametro);
    return tipoParametroRepository.save(tipoParametro);
  }

  @Override
  public Optional<TipoParametro> partialUpdate(TipoParametro tipoParametro) {
    log.debug("Request to partially update TipoParametro : {}", tipoParametro);

    return tipoParametroRepository
      .findById(tipoParametro.getId())
      .map(
        existingTipoParametro -> {
          if (tipoParametro.getTipaDescripcion() != null) {
            existingTipoParametro.setTipaDescripcion(tipoParametro.getTipaDescripcion());
          }

          return existingTipoParametro;
        }
      )
      .map(tipoParametroRepository::save);
  }

  @Override
  @Transactional(readOnly = true)
  public List<TipoParametro> findAll() {
    log.debug("Request to get all TipoParametros");
    return tipoParametroRepository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<TipoParametro> findOne(Long id) {
    log.debug("Request to get TipoParametro : {}", id);
    return tipoParametroRepository.findById(id);
  }

  @Override
  public void delete(Long id) {
    log.debug("Request to delete TipoParametro : {}", id);
    tipoParametroRepository.deleteById(id);
  }
}
