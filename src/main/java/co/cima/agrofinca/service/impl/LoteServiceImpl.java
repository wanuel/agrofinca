package co.cima.agrofinca.service.impl;

import co.cima.agrofinca.service.LoteService;
import co.cima.agrofinca.domain.Lote;
import co.cima.agrofinca.repository.LoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Lote}.
 */
@Service
@Transactional
public class LoteServiceImpl implements LoteService {

    private final Logger log = LoggerFactory.getLogger(LoteServiceImpl.class);

    private final LoteRepository loteRepository;

    public LoteServiceImpl(LoteRepository loteRepository) {
        this.loteRepository = loteRepository;
    }

    /**
     * Save a lote.
     *
     * @param lote the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Lote save(Lote lote) {
        log.debug("Request to save Lote : {}", lote);
        return loteRepository.save(lote);
    }

    /**
     * Get all the lotes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Lote> findAll(Pageable pageable) {
        log.debug("Request to get all Lotes");
        return loteRepository.findAll(pageable);
    }


    /**
     * Get one lote by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Lote> findOne(Long id) {
        log.debug("Request to get Lote : {}", id);
        return loteRepository.findById(id);
    }

    /**
     * Delete the lote by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Lote : {}", id);
        loteRepository.deleteById(id);
    }
}
