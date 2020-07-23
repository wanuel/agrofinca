package co.cima.agrofinca.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.cima.agrofinca.web.rest.TestUtil;

public class PotreroActividadAnimalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PotreroActividadAnimal.class);
        PotreroActividadAnimal potreroActividadAnimal1 = new PotreroActividadAnimal();
        potreroActividadAnimal1.setId(1L);
        PotreroActividadAnimal potreroActividadAnimal2 = new PotreroActividadAnimal();
        potreroActividadAnimal2.setId(potreroActividadAnimal1.getId());
        assertThat(potreroActividadAnimal1).isEqualTo(potreroActividadAnimal2);
        potreroActividadAnimal2.setId(2L);
        assertThat(potreroActividadAnimal1).isNotEqualTo(potreroActividadAnimal2);
        potreroActividadAnimal1.setId(null);
        assertThat(potreroActividadAnimal1).isNotEqualTo(potreroActividadAnimal2);
    }
}
