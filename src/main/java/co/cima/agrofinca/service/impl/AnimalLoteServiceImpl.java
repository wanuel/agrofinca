package co.cima.agrofinca.service.impl;

import co.cima.agrofinca.service.AnimalLoteService;
import co.cima.agrofinca.domain.AnimalLote;
import co.cima.agrofinca.repository.AnimalLoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AnimalLote}.
 */
@Service
@Transactional
public class AnimalLoteServiceImpl implements AnimalLoteService {

    private final Logger log = LoggerFactory.getLogger(AnimalLoteServiceImpl.class);

    private final AnimalLoteRepository animalLoteRepository;

    public AnimalLoteServiceImpl(AnimalLoteRepository animalLoteRepository) {
        this.animalLoteRepository = animalLoteRepository;
    }

    /**
     * Save a animalLote.
     *
     * @param animalLote the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AnimalLote save(AnimalLote animalLote) {
        log.debug("Request to save AnimalLote : {}", animalLote);
        return animalLoteRepository.save(animalLote);
    }

    /**
     * Get all the animalLotes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AnimalLote> findAll(Pageable pageable) {
        log.debug("Request to get all AnimalLotes");
        return animalLoteRepository.findAll(pageable);
    }


    /**
     * Get one animalLote by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AnimalLote> findOne(Long id) {
        log.debug("Request to get AnimalLote : {}", id);
        return animalLoteRepository.findById(id);
    }

    /**
     * Delete the animalLote by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnimalLote : {}", id);
        animalLoteRepository.deleteById(id);
    }
}
