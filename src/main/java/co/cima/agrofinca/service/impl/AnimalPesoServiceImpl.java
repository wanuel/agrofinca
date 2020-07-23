package co.cima.agrofinca.service.impl;

import co.cima.agrofinca.service.AnimalPesoService;
import co.cima.agrofinca.domain.AnimalPeso;
import co.cima.agrofinca.repository.AnimalPesoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AnimalPeso}.
 */
@Service
@Transactional
public class AnimalPesoServiceImpl implements AnimalPesoService {

    private final Logger log = LoggerFactory.getLogger(AnimalPesoServiceImpl.class);

    private final AnimalPesoRepository animalPesoRepository;

    public AnimalPesoServiceImpl(AnimalPesoRepository animalPesoRepository) {
        this.animalPesoRepository = animalPesoRepository;
    }

    /**
     * Save a animalPeso.
     *
     * @param animalPeso the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AnimalPeso save(AnimalPeso animalPeso) {
        log.debug("Request to save AnimalPeso : {}", animalPeso);
        return animalPesoRepository.save(animalPeso);
    }

    /**
     * Get all the animalPesos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AnimalPeso> findAll(Pageable pageable) {
        log.debug("Request to get all AnimalPesos");
        return animalPesoRepository.findAll(pageable);
    }


    /**
     * Get one animalPeso by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AnimalPeso> findOne(Long id) {
        log.debug("Request to get AnimalPeso : {}", id);
        return animalPesoRepository.findById(id);
    }

    /**
     * Delete the animalPeso by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnimalPeso : {}", id);
        animalPesoRepository.deleteById(id);
    }
}
