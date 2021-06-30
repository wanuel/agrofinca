package co.cima.agrofinca.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.cima.agrofinca.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CargueTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(Cargue.class);
    Cargue cargue1 = new Cargue();
    cargue1.setId(1L);
    Cargue cargue2 = new Cargue();
    cargue2.setId(cargue1.getId());
    assertThat(cargue1).isEqualTo(cargue2);
    cargue2.setId(2L);
    assertThat(cargue1).isNotEqualTo(cargue2);
    cargue1.setId(null);
    assertThat(cargue1).isNotEqualTo(cargue2);
  }
}
