package rocks.zipcode.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import rocks.zipcode.web.rest.TestUtil;

class ScorecardTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Scorecard.class);
        Scorecard scorecard1 = new Scorecard();
        scorecard1.setId(1L);
        Scorecard scorecard2 = new Scorecard();
        scorecard2.setId(scorecard1.getId());
        assertThat(scorecard1).isEqualTo(scorecard2);
        scorecard2.setId(2L);
        assertThat(scorecard1).isNotEqualTo(scorecard2);
        scorecard1.setId(null);
        assertThat(scorecard1).isNotEqualTo(scorecard2);
    }
}
