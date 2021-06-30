package co.cima.agrofinca.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.cima.agrofinca.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnimalVacunasTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(AnimalVacunas.class);
    AnimalVacunas animalVacunas1 = new AnimalVacunas();
    animalVacunas1.setId(1L);
    AnimalVacunas animalVacunas2 = new AnimalVacunas();
    animalVacunas2.setId(animalVacunas1.getId());
    assertThat(animalVacunas1).isEqualTo(animalVacunas2);
    animalVacunas2.setId(2L);
    assertThat(animalVacunas1).isNotEqualTo(animalVacunas2);
    animalVacunas1.setId(null);
    assertThat(animalVacunas1).isNotEqualTo(animalVacunas2);
  }
}
