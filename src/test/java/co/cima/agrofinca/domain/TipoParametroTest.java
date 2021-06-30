package co.cima.agrofinca.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.cima.agrofinca.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoParametroTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(TipoParametro.class);
    TipoParametro tipoParametro1 = new TipoParametro();
    tipoParametro1.setId(1L);
    TipoParametro tipoParametro2 = new TipoParametro();
    tipoParametro2.setId(tipoParametro1.getId());
    assertThat(tipoParametro1).isEqualTo(tipoParametro2);
    tipoParametro2.setId(2L);
    assertThat(tipoParametro1).isNotEqualTo(tipoParametro2);
    tipoParametro1.setId(null);
    assertThat(tipoParametro1).isNotEqualTo(tipoParametro2);
  }
}
