package rocks.zipcode.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import rocks.zipcode.web.rest.TestUtil;

class GolferDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GolferDTO.class);
        GolferDTO golferDTO1 = new GolferDTO();
        golferDTO1.setId(1L);
        GolferDTO golferDTO2 = new GolferDTO();
        assertThat(golferDTO1).isNotEqualTo(golferDTO2);
        golferDTO2.setId(golferDTO1.getId());
        assertThat(golferDTO1).isEqualTo(golferDTO2);
        golferDTO2.setId(2L);
        assertThat(golferDTO1).isNotEqualTo(golferDTO2);
        golferDTO1.setId(null);
        assertThat(golferDTO1).isNotEqualTo(golferDTO2);
    }
}
