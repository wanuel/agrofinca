package co.cima.agrofinca.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import co.cima.agrofinca.domain.Finca;
import co.cima.agrofinca.domain.*; // for static metamodels
import co.cima.agrofinca.repository.FincaRepository;
import co.cima.agrofinca.service.dto.FincaCriteria;

/**
 * Service for executing complex queries for {@link Finca} entities in the database.
 * The main input is a {@link FincaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Finca} or a {@link Page} of {@link Finca} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FincaQueryService extends QueryService<Finca> {

    private final Logger log = LoggerFactory.getLogger(FincaQueryService.class);

    private final FincaRepository fincaRepository;

    public FincaQueryService(FincaRepository fincaRepository) {
        this.fincaRepository = fincaRepository;
    }

    /**
     * Return a {@link List} of {@link Finca} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Finca> findByCriteria(FincaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Finca> specification = createSpecification(criteria);
        return fincaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Finca} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Finca> findByCriteria(FincaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Finca> specification = createSpecification(criteria);
        return fincaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FincaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Finca> specification = createSpecification(criteria);
        return fincaRepository.count(specification);
    }

    /**
     * Function to convert {@link FincaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Finca> createSpecification(FincaCriteria criteria) {
        Specification<Finca> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Finca_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), Finca_.nombre));
            }
            if (criteria.getArea() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getArea(), Finca_.area));
            }
        }
        return specification;
    }
}
