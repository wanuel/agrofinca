package co.cima.agrofinca.service.impl;

import co.cima.agrofinca.domain.ErrorCargue;
import co.cima.agrofinca.repository.ErrorCargueRepository;
import co.cima.agrofinca.service.ErrorCargueService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ErrorCargue}.
 */
@Service
@Transactional
public class ErrorCargueServiceImpl implements ErrorCargueService {

  private final Logger log = LoggerFactory.getLogger(ErrorCargueServiceImpl.class);

  private final ErrorCargueRepository errorCargueRepository;

  public ErrorCargueServiceImpl(ErrorCargueRepository errorCargueRepository) {
    this.errorCargueRepository = errorCargueRepository;
  }

  @Override
  public ErrorCargue save(ErrorCargue errorCargue) {
    log.debug("Request to save ErrorCargue : {}", errorCargue);
    return errorCargueRepository.save(errorCargue);
  }

  @Override
  public Optional<ErrorCargue> partialUpdate(ErrorCargue errorCargue) {
    log.debug("Request to partially update ErrorCargue : {}", errorCargue);

    return errorCargueRepository
      .findById(errorCargue.getId())
      .map(
        existingErrorCargue -> {
          if (errorCargue.getErcaError() != null) {
            existingErrorCargue.setErcaError(errorCargue.getErcaError());
          }

          return existingErrorCargue;
        }
      )
      .map(errorCargueRepository::save);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ErrorCargue> findAll() {
    log.debug("Request to get all ErrorCargues");
    return errorCargueRepository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<ErrorCargue> findOne(Long id) {
    log.debug("Request to get ErrorCargue : {}", id);
    return errorCargueRepository.findById(id);
  }

  @Override
  public void delete(Long id) {
    log.debug("Request to delete ErrorCargue : {}", id);
    errorCargueRepository.deleteById(id);
  }
}
