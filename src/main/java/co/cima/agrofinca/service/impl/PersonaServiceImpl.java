package co.cima.agrofinca.service.impl;

import co.cima.agrofinca.domain.Persona;
import co.cima.agrofinca.repository.PersonaRepository;
import co.cima.agrofinca.service.PersonaService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Persona}.
 */
@Service
@Transactional
public class PersonaServiceImpl implements PersonaService {

  private final Logger log = LoggerFactory.getLogger(PersonaServiceImpl.class);

  private final PersonaRepository personaRepository;

  public PersonaServiceImpl(PersonaRepository personaRepository) {
    this.personaRepository = personaRepository;
  }

  @Override
  public Persona save(Persona persona) {
    log.debug("Request to save Persona : {}", persona);
    return personaRepository.save(persona);
  }

  @Override
  public Optional<Persona> partialUpdate(Persona persona) {
    log.debug("Request to partially update Persona : {}", persona);

    return personaRepository
      .findById(persona.getId())
      .map(
        existingPersona -> {
          if (persona.getTipoDocumento() != null) {
            existingPersona.setTipoDocumento(persona.getTipoDocumento());
          }
          if (persona.getNumDocuemnto() != null) {
            existingPersona.setNumDocuemnto(persona.getNumDocuemnto());
          }
          if (persona.getPrimerNombre() != null) {
            existingPersona.setPrimerNombre(persona.getPrimerNombre());
          }
          if (persona.getSegundoNombre() != null) {
            existingPersona.setSegundoNombre(persona.getSegundoNombre());
          }
          if (persona.getPrimerApellido() != null) {
            existingPersona.setPrimerApellido(persona.getPrimerApellido());
          }
          if (persona.getSegundoApellido() != null) {
            existingPersona.setSegundoApellido(persona.getSegundoApellido());
          }
          if (persona.getFechaNacimiento() != null) {
            existingPersona.setFechaNacimiento(persona.getFechaNacimiento());
          }
          if (persona.getGenero() != null) {
            existingPersona.setGenero(persona.getGenero());
          }

          return existingPersona;
        }
      )
      .map(personaRepository::save);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Persona> findAll(Pageable pageable) {
    log.debug("Request to get all Personas");
    return personaRepository.findAll(pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Persona> findOne(Long id) {
    log.debug("Request to get Persona : {}", id);
    return personaRepository.findById(id);
  }

  @Override
  public void delete(Long id) {
    log.debug("Request to delete Persona : {}", id);
    personaRepository.deleteById(id);
  }
}
