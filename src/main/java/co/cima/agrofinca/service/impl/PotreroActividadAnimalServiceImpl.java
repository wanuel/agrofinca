package co.cima.agrofinca.service.impl;

import co.cima.agrofinca.service.PotreroActividadAnimalService;
import co.cima.agrofinca.domain.PotreroActividadAnimal;
import co.cima.agrofinca.repository.PotreroActividadAnimalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PotreroActividadAnimal}.
 */
@Service
@Transactional
public class PotreroActividadAnimalServiceImpl implements PotreroActividadAnimalService {

    private final Logger log = LoggerFactory.getLogger(PotreroActividadAnimalServiceImpl.class);

    private final PotreroActividadAnimalRepository potreroActividadAnimalRepository;

    public PotreroActividadAnimalServiceImpl(PotreroActividadAnimalRepository potreroActividadAnimalRepository) {
        this.potreroActividadAnimalRepository = potreroActividadAnimalRepository;
    }

    /**
     * Save a potreroActividadAnimal.
     *
     * @param potreroActividadAnimal the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PotreroActividadAnimal save(PotreroActividadAnimal potreroActividadAnimal) {
        log.debug("Request to save PotreroActividadAnimal : {}", potreroActividadAnimal);
        return potreroActividadAnimalRepository.save(potreroActividadAnimal);
    }

    /**
     * Get all the potreroActividadAnimals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PotreroActividadAnimal> findAll(Pageable pageable) {
        log.debug("Request to get all PotreroActividadAnimals");
        return potreroActividadAnimalRepository.findAll(pageable);
    }


    /**
     * Get one potreroActividadAnimal by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PotreroActividadAnimal> findOne(Long id) {
        log.debug("Request to get PotreroActividadAnimal : {}", id);
        return potreroActividadAnimalRepository.findById(id);
    }

    /**
     * Delete the potreroActividadAnimal by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PotreroActividadAnimal : {}", id);
        potreroActividadAnimalRepository.deleteById(id);
    }
}
