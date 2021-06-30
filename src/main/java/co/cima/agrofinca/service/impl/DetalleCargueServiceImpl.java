package co.cima.agrofinca.service.impl;

import co.cima.agrofinca.domain.DetalleCargue;
import co.cima.agrofinca.repository.DetalleCargueRepository;
import co.cima.agrofinca.service.DetalleCargueService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DetalleCargue}.
 */
@Service
@Transactional
public class DetalleCargueServiceImpl implements DetalleCargueService {

  private final Logger log = LoggerFactory.getLogger(DetalleCargueServiceImpl.class);

  private final DetalleCargueRepository detalleCargueRepository;

  public DetalleCargueServiceImpl(DetalleCargueRepository detalleCargueRepository) {
    this.detalleCargueRepository = detalleCargueRepository;
  }

  @Override
  public DetalleCargue save(DetalleCargue detalleCargue) {
    log.debug("Request to save DetalleCargue : {}", detalleCargue);
    return detalleCargueRepository.save(detalleCargue);
  }

  @Override
  public Optional<DetalleCargue> partialUpdate(DetalleCargue detalleCargue) {
    log.debug("Request to partially update DetalleCargue : {}", detalleCargue);

    return detalleCargueRepository
      .findById(detalleCargue.getId())
      .map(
        existingDetalleCargue -> {
          if (detalleCargue.getDecaCup() != null) {
            existingDetalleCargue.setDecaCup(detalleCargue.getDecaCup());
          }
          if (detalleCargue.getDecaEstado() != null) {
            existingDetalleCargue.setDecaEstado(detalleCargue.getDecaEstado());
          }
          if (detalleCargue.getDecaJSON() != null) {
            existingDetalleCargue.setDecaJSON(detalleCargue.getDecaJSON());
          }

          return existingDetalleCargue;
        }
      )
      .map(detalleCargueRepository::save);
  }

  @Override
  @Transactional(readOnly = true)
  public List<DetalleCargue> findAll() {
    log.debug("Request to get all DetalleCargues");
    return detalleCargueRepository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<DetalleCargue> findOne(Long id) {
    log.debug("Request to get DetalleCargue : {}", id);
    return detalleCargueRepository.findById(id);
  }

  @Override
  public void delete(Long id) {
    log.debug("Request to delete DetalleCargue : {}", id);
    detalleCargueRepository.deleteById(id);
  }
}
