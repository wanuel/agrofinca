package co.cima.agrofinca.service.impl;

import co.cima.agrofinca.service.AnimalEventoService;
import co.cima.agrofinca.domain.AnimalEvento;
import co.cima.agrofinca.repository.AnimalEventoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AnimalEvento}.
 */
@Service
@Transactional
public class AnimalEventoServiceImpl implements AnimalEventoService {

    private final Logger log = LoggerFactory.getLogger(AnimalEventoServiceImpl.class);

    private final AnimalEventoRepository animalEventoRepository;

    public AnimalEventoServiceImpl(AnimalEventoRepository animalEventoRepository) {
        this.animalEventoRepository = animalEventoRepository;
    }

    /**
     * Save a animalEvento.
     *
     * @param animalEvento the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AnimalEvento save(AnimalEvento animalEvento) {
        log.debug("Request to save AnimalEvento : {}", animalEvento);
        return animalEventoRepository.save(animalEvento);
    }

    /**
     * Get all the animalEventos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AnimalEvento> findAll(Pageable pageable) {
        log.debug("Request to get all AnimalEventos");
        return animalEventoRepository.findAll(pageable);
    }


    /**
     * Get one animalEvento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AnimalEvento> findOne(Long id) {
        log.debug("Request to get AnimalEvento : {}", id);
        return animalEventoRepository.findById(id);
    }

    /**
     * Delete the animalEvento by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnimalEvento : {}", id);
        animalEventoRepository.deleteById(id);
    }
}
