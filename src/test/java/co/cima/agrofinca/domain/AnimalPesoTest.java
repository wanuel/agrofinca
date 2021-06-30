package co.cima.agrofinca.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.cima.agrofinca.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnimalPesoTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(AnimalPeso.class);
    AnimalPeso animalPeso1 = new AnimalPeso();
    animalPeso1.setId(1L);
    AnimalPeso animalPeso2 = new AnimalPeso();
    animalPeso2.setId(animalPeso1.getId());
    assertThat(animalPeso1).isEqualTo(animalPeso2);
    animalPeso2.setId(2L);
    assertThat(animalPeso1).isNotEqualTo(animalPeso2);
    animalPeso1.setId(null);
    assertThat(animalPeso1).isNotEqualTo(animalPeso2);
  }
}
