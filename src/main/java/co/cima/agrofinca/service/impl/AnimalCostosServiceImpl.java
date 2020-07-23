package co.cima.agrofinca.service.impl;

import co.cima.agrofinca.service.AnimalCostosService;
import co.cima.agrofinca.domain.AnimalCostos;
import co.cima.agrofinca.repository.AnimalCostosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AnimalCostos}.
 */
@Service
@Transactional
public class AnimalCostosServiceImpl implements AnimalCostosService {

    private final Logger log = LoggerFactory.getLogger(AnimalCostosServiceImpl.class);

    private final AnimalCostosRepository animalCostosRepository;

    public AnimalCostosServiceImpl(AnimalCostosRepository animalCostosRepository) {
        this.animalCostosRepository = animalCostosRepository;
    }

    /**
     * Save a animalCostos.
     *
     * @param animalCostos the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AnimalCostos save(AnimalCostos animalCostos) {
        log.debug("Request to save AnimalCostos : {}", animalCostos);
        return animalCostosRepository.save(animalCostos);
    }

    /**
     * Get all the animalCostos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AnimalCostos> findAll(Pageable pageable) {
        log.debug("Request to get all AnimalCostos");
        return animalCostosRepository.findAll(pageable);
    }


    /**
     * Get one animalCostos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AnimalCostos> findOne(Long id) {
        log.debug("Request to get AnimalCostos : {}", id);
        return animalCostosRepository.findById(id);
    }

    /**
     * Delete the animalCostos by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnimalCostos : {}", id);
        animalCostosRepository.deleteById(id);
    }
}
