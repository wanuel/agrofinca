package co.cima.agrofinca.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.cima.agrofinca.domain.Potrero;
import co.cima.agrofinca.domain.vo.ListVO;
import co.cima.agrofinca.repository.PotreroRepository;
import co.cima.agrofinca.service.PotreroService;

/**
 * Service Implementation for managing {@link Potrero}.
 */
@Service
@Transactional
public class PotreroServiceImpl implements PotreroService {

    private final Logger log = LoggerFactory.getLogger(PotreroServiceImpl.class);

    private final PotreroRepository potreroRepository;

    public PotreroServiceImpl(PotreroRepository potreroRepository) {
        this.potreroRepository = potreroRepository;
    }

    /**
     * Save a potrero.
     *
     * @param potrero the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Potrero save(Potrero potrero) {
        log.debug("Request to save Potrero : {}", potrero);
        return potreroRepository.save(potrero);
    }

    /**
     * Get all the potreros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Potrero> findAll(Pageable pageable) {
        log.debug("Request to get all Potreros");
        return potreroRepository.findAll(pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Potrero> findAll() {
        log.debug("Request to get all Potreros");
        return potreroRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ListVO> findListVO() {
        log.debug("Request to get all Potreros");
        return potreroRepository.findListVO();
    }


    /**
     * Get one potrero by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Potrero> findOne(Long id) {
        log.debug("Request to get Potrero : {}", id);
        return potreroRepository.findById(id);
    }

    /**
     * Delete the potrero by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Potrero : {}", id);
        potreroRepository.deleteById(id);
    }
}
