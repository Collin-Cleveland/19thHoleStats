package rocks.zipcode.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import rocks.zipcode.web.rest.TestUtil;

class ScorecardDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScorecardDTO.class);
        ScorecardDTO scorecardDTO1 = new ScorecardDTO();
        scorecardDTO1.setId(1L);
        ScorecardDTO scorecardDTO2 = new ScorecardDTO();
        assertThat(scorecardDTO1).isNotEqualTo(scorecardDTO2);
        scorecardDTO2.setId(scorecardDTO1.getId());
        assertThat(scorecardDTO1).isEqualTo(scorecardDTO2);
        scorecardDTO2.setId(2L);
        assertThat(scorecardDTO1).isNotEqualTo(scorecardDTO2);
        scorecardDTO1.setId(null);
        assertThat(scorecardDTO1).isNotEqualTo(scorecardDTO2);
    }
}
