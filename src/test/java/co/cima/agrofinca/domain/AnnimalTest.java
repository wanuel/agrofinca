package co.cima.agrofinca.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.cima.agrofinca.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnnimalTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(Annimal.class);
    Annimal annimal1 = new Annimal();
    annimal1.setId(1L);
    Annimal annimal2 = new Annimal();
    annimal2.setId(annimal1.getId());
    assertThat(annimal1).isEqualTo(annimal2);
    annimal2.setId(2L);
    assertThat(annimal1).isNotEqualTo(annimal2);
    annimal1.setId(null);
    assertThat(annimal1).isNotEqualTo(annimal2);
  }
}
