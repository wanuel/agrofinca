package co.cima.agrofinca.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.cima.agrofinca.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PotreroActividadTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(PotreroActividad.class);
    PotreroActividad potreroActividad1 = new PotreroActividad();
    potreroActividad1.setId(1L);
    PotreroActividad potreroActividad2 = new PotreroActividad();
    potreroActividad2.setId(potreroActividad1.getId());
    assertThat(potreroActividad1).isEqualTo(potreroActividad2);
    potreroActividad2.setId(2L);
    assertThat(potreroActividad1).isNotEqualTo(potreroActividad2);
    potreroActividad1.setId(null);
    assertThat(potreroActividad1).isNotEqualTo(potreroActividad2);
  }
}
