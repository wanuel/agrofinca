package co.cima.agrofinca.service.impl;

import co.cima.agrofinca.service.AnimalImagenService;
import co.cima.agrofinca.domain.AnimalImagen;
import co.cima.agrofinca.repository.AnimalImagenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AnimalImagen}.
 */
@Service
@Transactional
public class AnimalImagenServiceImpl implements AnimalImagenService {

    private final Logger log = LoggerFactory.getLogger(AnimalImagenServiceImpl.class);

    private final AnimalImagenRepository animalImagenRepository;

    public AnimalImagenServiceImpl(AnimalImagenRepository animalImagenRepository) {
        this.animalImagenRepository = animalImagenRepository;
    }

    /**
     * Save a animalImagen.
     *
     * @param animalImagen the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AnimalImagen save(AnimalImagen animalImagen) {
        log.debug("Request to save AnimalImagen : {}", animalImagen);
        return animalImagenRepository.save(animalImagen);
    }

    /**
     * Get all the animalImagens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AnimalImagen> findAll(Pageable pageable) {
        log.debug("Request to get all AnimalImagens");
        return animalImagenRepository.findAll(pageable);
    }


    /**
     * Get one animalImagen by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AnimalImagen> findOne(Long id) {
        log.debug("Request to get AnimalImagen : {}", id);
        return animalImagenRepository.findById(id);
    }

    /**
     * Delete the animalImagen by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnimalImagen : {}", id);
        animalImagenRepository.deleteById(id);
    }
}
