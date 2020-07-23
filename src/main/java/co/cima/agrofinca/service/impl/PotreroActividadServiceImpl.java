package co.cima.agrofinca.service.impl;

import co.cima.agrofinca.service.PotreroActividadService;
import co.cima.agrofinca.domain.PotreroActividad;
import co.cima.agrofinca.repository.PotreroActividadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    /**
     * Save a potreroActividad.
     *
     * @param potreroActividad the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PotreroActividad save(PotreroActividad potreroActividad) {
        log.debug("Request to save PotreroActividad : {}", potreroActividad);
        return potreroActividadRepository.save(potreroActividad);
    }

    /**
     * Get all the potreroActividads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PotreroActividad> findAll(Pageable pageable) {
        log.debug("Request to get all PotreroActividads");
        return potreroActividadRepository.findAll(pageable);
    }


    /**
     * Get all the potreroActividads with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PotreroActividad> findAllWithEagerRelationships(Pageable pageable) {
        return potreroActividadRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one potreroActividad by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PotreroActividad> findOne(Long id) {
        log.debug("Request to get PotreroActividad : {}", id);
        return potreroActividadRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the potreroActividad by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PotreroActividad : {}", id);
        potreroActividadRepository.deleteById(id);
    }
}
