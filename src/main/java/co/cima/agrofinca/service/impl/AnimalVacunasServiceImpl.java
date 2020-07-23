package co.cima.agrofinca.service.impl;

import co.cima.agrofinca.service.AnimalVacunasService;
import co.cima.agrofinca.domain.AnimalVacunas;
import co.cima.agrofinca.repository.AnimalVacunasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AnimalVacunas}.
 */
@Service
@Transactional
public class AnimalVacunasServiceImpl implements AnimalVacunasService {

    private final Logger log = LoggerFactory.getLogger(AnimalVacunasServiceImpl.class);

    private final AnimalVacunasRepository animalVacunasRepository;

    public AnimalVacunasServiceImpl(AnimalVacunasRepository animalVacunasRepository) {
        this.animalVacunasRepository = animalVacunasRepository;
    }

    /**
     * Save a animalVacunas.
     *
     * @param animalVacunas the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AnimalVacunas save(AnimalVacunas animalVacunas) {
        log.debug("Request to save AnimalVacunas : {}", animalVacunas);
        return animalVacunasRepository.save(animalVacunas);
    }

    /**
     * Get all the animalVacunas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AnimalVacunas> findAll(Pageable pageable) {
        log.debug("Request to get all AnimalVacunas");
        return animalVacunasRepository.findAll(pageable);
    }


    /**
     * Get one animalVacunas by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AnimalVacunas> findOne(Long id) {
        log.debug("Request to get AnimalVacunas : {}", id);
        return animalVacunasRepository.findById(id);
    }

    /**
     * Delete the animalVacunas by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnimalVacunas : {}", id);
        animalVacunasRepository.deleteById(id);
    }
}
