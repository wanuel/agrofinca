package co.cima.agrofinca.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.cima.agrofinca.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ErrorCargueTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(ErrorCargue.class);
    ErrorCargue errorCargue1 = new ErrorCargue();
    errorCargue1.setId(1L);
    ErrorCargue errorCargue2 = new ErrorCargue();
    errorCargue2.setId(errorCargue1.getId());
    assertThat(errorCargue1).isEqualTo(errorCargue2);
    errorCargue2.setId(2L);
    assertThat(errorCargue1).isNotEqualTo(errorCargue2);
    errorCargue1.setId(null);
    assertThat(errorCargue1).isNotEqualTo(errorCargue2);
  }
}
