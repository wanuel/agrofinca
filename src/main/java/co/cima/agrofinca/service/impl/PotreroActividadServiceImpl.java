package co.cima.agrofinca.service.impl;

import co.cima.agrofinca.domain.PotreroActividad;
import co.cima.agrofinca.repository.PotreroActividadRepository;
import co.cima.agrofinca.service.PotreroActividadService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PotreroActividad}.
 */
@Service
@Transactional
public class PotreroActividadServiceImpl implements PotreroActividadService {

  private final Logger log = LoggerFactory.getLogger(PotreroActividadServiceImpl.class);

  private final PotreroActividadRepository potreroActividadRepository;

  public PotreroActividadServiceImpl(PotreroActividadRepository potreroActividadRepository) {
    this.potreroActividadRepository = potreroActividadRepository;
  }

  @Override
  public PotreroActividad save(PotreroActividad potreroActividad) {
    log.debug("Request to save PotreroActividad : {}", potreroActividad);
    return potreroActividadRepository.save(potreroActividad);
  }

  @Override
  public Optional<PotreroActividad> partialUpdate(PotreroActividad potreroActividad) {
    log.debug("Request to partially update PotreroActividad : {}", potreroActividad);

    return potreroActividadRepository
      .findById(potreroActividad.getId())
      .map(
        existingPotreroActividad -> {
          if (potreroActividad.getFechaIngreso() != null) {
            existingPotreroActividad.setFechaIngreso(potreroActividad.getFechaIngreso());
          }
          if (potreroActividad.getFechaSalida() != null) {
            existingPotreroActividad.setFechaSalida(potreroActividad.getFechaSalida());
          }
          if (potreroActividad.getCantidadBovinos() != null) {
            existingPotreroActividad.setCantidadBovinos(potreroActividad.getCantidadBovinos());
          }
          if (potreroActividad.getCantidadEquinos() != null) {
            existingPotreroActividad.setCantidadEquinos(potreroActividad.getCantidadEquinos());
          }
          if (potreroActividad.getCantidadMulares() != null) {
            existingPotreroActividad.setCantidadMulares(potreroActividad.getCantidadMulares());
          }
          if (potreroActividad.getFechaLimpia() != null) {
            existingPotreroActividad.setFechaLimpia(potreroActividad.getFechaLimpia());
          }
          if (potreroActividad.getDiasDescanso() != null) {
            existingPotreroActividad.setDiasDescanso(potreroActividad.getDiasDescanso());
          }
          if (potreroActividad.getDiasCarga() != null) {
            existingPotreroActividad.setDiasCarga(potreroActividad.getDiasCarga());
          }
          if (potreroActividad.getOcupado() != null) {
            existingPotreroActividad.setOcupado(potreroActividad.getOcupado());
          }

          return existingPotreroActividad;
        }
      )
      .map(potreroActividadRepository::save);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<PotreroActividad> findAll(Pageable pageable) {
    log.debug("Request to get all PotreroActividads");
    return potreroActividadRepository.findAll(pageable);
  }

  public Page<PotreroActividad> findAllWithEagerRelationships(Pageable pageable) {
    return potreroActividadRepository.findAllWithEagerRelationships(pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<PotreroActividad> findOne(Long id) {
    log.debug("Request to get PotreroActividad : {}", id);
    return potreroActividadRepository.findOneWithEagerRelationships(id);
  }

  @Override
  public void delete(Long id) {
    log.debug("Request to delete PotreroActividad : {}", id);
    potreroActividadRepository.deleteById(id);
  }
}
