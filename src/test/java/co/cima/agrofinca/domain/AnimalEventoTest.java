package co.cima.agrofinca.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.cima.agrofinca.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnimalEventoTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(AnimalEvento.class);
    AnimalEvento animalEvento1 = new AnimalEvento();
    animalEvento1.setId(1L);
    AnimalEvento animalEvento2 = new AnimalEvento();
    animalEvento2.setId(animalEvento1.getId());
    assertThat(animalEvento1).isEqualTo(animalEvento2);
    animalEvento2.setId(2L);
    assertThat(animalEvento1).isNotEqualTo(animalEvento2);
    animalEvento1.setId(null);
    assertThat(animalEvento1).isNotEqualTo(animalEvento2);
  }
}
