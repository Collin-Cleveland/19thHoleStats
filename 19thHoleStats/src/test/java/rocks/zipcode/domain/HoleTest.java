package rocks.zipcode.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import rocks.zipcode.web.rest.TestUtil;

class HoleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hole.class);
        Hole hole1 = new Hole();
        hole1.setId(1L);
        Hole hole2 = new Hole();
        hole2.setId(hole1.getId());
        assertThat(hole1).isEqualTo(hole2);
        hole2.setId(2L);
        assertThat(hole1).isNotEqualTo(hole2);
        hole1.setId(null);
        assertThat(hole1).isNotEqualTo(hole2);
    }
}
