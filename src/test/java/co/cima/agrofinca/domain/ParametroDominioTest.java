package co.cima.agrofinca.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.cima.agrofinca.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ParametroDominioTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(ParametroDominio.class);
    ParametroDominio parametroDominio1 = new ParametroDominio();
    parametroDominio1.setId(1L);
    ParametroDominio parametroDominio2 = new ParametroDominio();
    parametroDominio2.setId(parametroDominio1.getId());
    assertThat(parametroDominio1).isEqualTo(parametroDominio2);
    parametroDominio2.setId(2L);
    assertThat(parametroDominio1).isNotEqualTo(parametroDominio2);
    parametroDominio1.setId(null);
    assertThat(parametroDominio1).isNotEqualTo(parametroDominio2);
  }
}
