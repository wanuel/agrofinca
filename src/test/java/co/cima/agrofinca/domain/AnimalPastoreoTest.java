package co.cima.agrofinca.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.cima.agrofinca.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnimalPastoreoTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(AnimalPastoreo.class);
    AnimalPastoreo animalPastoreo1 = new AnimalPastoreo();
    animalPastoreo1.setId(1L);
    AnimalPastoreo animalPastoreo2 = new AnimalPastoreo();
    animalPastoreo2.setId(animalPastoreo1.getId());
    assertThat(animalPastoreo1).isEqualTo(animalPastoreo2);
    animalPastoreo2.setId(2L);
    assertThat(animalPastoreo1).isNotEqualTo(animalPastoreo2);
    animalPastoreo1.setId(null);
    assertThat(animalPastoreo1).isNotEqualTo(animalPastoreo2);
  }
}
