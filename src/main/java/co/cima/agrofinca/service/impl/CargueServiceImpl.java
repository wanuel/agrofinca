package co.cima.agrofinca.service.impl;

import co.cima.agrofinca.domain.Cargue;
import co.cima.agrofinca.repository.CargueRepository;
import co.cima.agrofinca.service.CargueService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Cargue}.
 */
@Service
@Transactional
public class CargueServiceImpl implements CargueService {

  private final Logger log = LoggerFactory.getLogger(CargueServiceImpl.class);

  private final CargueRepository cargueRepository;

  public CargueServiceImpl(CargueRepository cargueRepository) {
    this.cargueRepository = cargueRepository;
  }

  @Override
  public Cargue save(Cargue cargue) {
    log.debug("Request to save Cargue : {}", cargue);
    return cargueRepository.save(cargue);
  }

  @Override
  public Optional<Cargue> partialUpdate(Cargue cargue) {
    log.debug("Request to partially update Cargue : {}", cargue);

    return cargueRepository
      .findById(cargue.getId())
      .map(
        existingCargue -> {
          if (cargue.getCargNroRegistros() != null) {
            existingCargue.setCargNroRegistros(cargue.getCargNroRegistros());
          }
          if (cargue.getCargJson() != null) {
            existingCargue.setCargJson(cargue.getCargJson());
          }
          if (cargue.getCargEntidad() != null) {
            existingCargue.setCargEntidad(cargue.getCargEntidad());
          }
          if (cargue.getCargNombreArchivo() != null) {
            existingCargue.setCargNombreArchivo(cargue.getCargNombreArchivo());
          }
          if (cargue.getCargEstado() != null) {
            existingCargue.setCargEstado(cargue.getCargEstado());
          }
          if (cargue.getCargTipo() != null) {
            existingCargue.setCargTipo(cargue.getCargTipo());
          }
          if (cargue.getCargEsReproceso() != null) {
            existingCargue.setCargEsReproceso(cargue.getCargEsReproceso());
          }
          if (cargue.getCargHash() != null) {
            existingCargue.setCargHash(cargue.getCargHash());
          }
          if (cargue.getUsuarioCreacion() != null) {
            existingCargue.setUsuarioCreacion(cargue.getUsuarioCreacion());
          }
          if (cargue.getFechaCreacion() != null) {
            existingCargue.setFechaCreacion(cargue.getFechaCreacion());
          }
          if (cargue.getUsuarioModificacion() != null) {
            existingCargue.setUsuarioModificacion(cargue.getUsuarioModificacion());
          }
          if (cargue.getFechaModificacion() != null) {
            existingCargue.setFechaModificacion(cargue.getFechaModificacion());
          }

          return existingCargue;
        }
      )
      .map(cargueRepository::save);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Cargue> findAll() {
    log.debug("Request to get all Cargues");
    return cargueRepository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Cargue> findOne(Long id) {
    log.debug("Request to get Cargue : {}", id);
    return cargueRepository.findById(id);
  }

  @Override
  public void delete(Long id) {
    log.debug("Request to delete Cargue : {}", id);
    cargueRepository.deleteById(id);
  }
}
