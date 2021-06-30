package co.cima.agrofinca.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.cima.agrofinca.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetalleCargueTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(DetalleCargue.class);
    DetalleCargue detalleCargue1 = new DetalleCargue();
    detalleCargue1.setId(1L);
    DetalleCargue detalleCargue2 = new DetalleCargue();
    detalleCargue2.setId(detalleCargue1.getId());
    assertThat(detalleCargue1).isEqualTo(detalleCargue2);
    detalleCargue2.setId(2L);
    assertThat(detalleCargue1).isNotEqualTo(detalleCargue2);
    detalleCargue1.setId(null);
    assertThat(detalleCargue1).isNotEqualTo(detalleCargue2);
  }
}
