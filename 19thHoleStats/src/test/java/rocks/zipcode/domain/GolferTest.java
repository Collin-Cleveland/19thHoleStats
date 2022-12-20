package rocks.zipcode.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import rocks.zipcode.web.rest.TestUtil;

class GolferTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Golfer.class);
        Golfer golfer1 = new Golfer();
        golfer1.setId(1L);
        Golfer golfer2 = new Golfer();
        golfer2.setId(golfer1.getId());
        assertThat(golfer1).isEqualTo(golfer2);
        golfer2.setId(2L);
        assertThat(golfer1).isNotEqualTo(golfer2);
        golfer1.setId(null);
        assertThat(golfer1).isNotEqualTo(golfer2);
    }
}
