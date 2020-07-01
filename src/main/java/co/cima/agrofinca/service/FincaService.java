package co.cima.agrofinca.service;

import co.cima.agrofinca.domain.Finca;
import co.cima.agrofinca.repository.FincaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Finca}.
 */
@Service
@Transactional
public class FincaService {

    private final Logger log = LoggerFactory.getLogger(FincaService.class);

    private final FincaRepository fincaRepository;

    public FincaService(FincaRepository fincaRepository) {
        this.fincaRepository = fincaRepository;
    }

    /**
     * Save a finca.
     *
     * @param finca the entity to save.
     * @return the persisted entity.
     */
    public Finca save(Finca finca) {
        log.debug("Request to save Finca : {}", finca);
        return fincaRepository.save(finca);
    }

    /**
     * Get all the fincas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Finca> findAll(Pageable pageable) {
        log.debug("Request to get all Fincas");
        return fincaRepository.findAll(pageable);
    }


    /**
     * Get one finca by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Finca> findOne(Long id) {
        log.debug("Request to get Finca : {}", id);
        return fincaRepository.findById(id);
    }

    /**
     * Delete the finca by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Finca : {}", id);
        fincaRepository.deleteById(id);
    }
}
