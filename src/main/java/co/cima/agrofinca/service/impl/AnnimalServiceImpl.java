package co.cima.agrofinca.service.impl;

import co.cima.agrofinca.domain.Annimal;
import co.cima.agrofinca.repository.AnnimalRepository;
import co.cima.agrofinca.service.AnnimalService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Annimal}.
 */
@Service
@Transactional
public class AnnimalServiceImpl implements AnnimalService {

  private final Logger log = LoggerFactory.getLogger(AnnimalServiceImpl.class);

  private final AnnimalRepository annimalRepository;

  public AnnimalServiceImpl(AnnimalRepository annimalRepository) {
    this.annimalRepository = annimalRepository;
  }

  @Override
  public Annimal save(Annimal annimal) {
    log.debug("Request to save Annimal : {}", annimal);
    return annimalRepository.save(annimal);
  }

  @Override
  public Optional<Annimal> partialUpdate(Annimal annimal) {
    log.debug("Request to partially update Annimal : {}", annimal);

    return annimalRepository
      .findById(annimal.getId())
      .map(
        existingAnnimal -> {
          if (annimal.getNombre() != null) {
            existingAnnimal.setNombre(annimal.getNombre());
          }

          return existingAnnimal;
        }
      )
      .map(annimalRepository::save);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Annimal> findAll(Pageable pageable) {
    log.debug("Request to get all Annimals");
    return annimalRepository.findAll(pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Annimal> findOne(Long id) {
    log.debug("Request to get Annimal : {}", id);
    return annimalRepository.findById(id);
  }

  @Override
  public void delete(Long id) {
    log.debug("Request to delete Annimal : {}", id);
    annimalRepository.deleteById(id);
  }
}
