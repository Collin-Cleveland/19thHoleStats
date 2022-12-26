package rocks.zipcode.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import rocks.zipcode.web.rest.TestUtil;

class HoleDataDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HoleDataDTO.class);
        HoleDataDTO holeDataDTO1 = new HoleDataDTO();
        holeDataDTO1.setId(1L);
        HoleDataDTO holeDataDTO2 = new HoleDataDTO();
        assertThat(holeDataDTO1).isNotEqualTo(holeDataDTO2);
        holeDataDTO2.setId(holeDataDTO1.getId());
        assertThat(holeDataDTO1).isEqualTo(holeDataDTO2);
        holeDataDTO2.setId(2L);
        assertThat(holeDataDTO1).isNotEqualTo(holeDataDTO2);
        holeDataDTO1.setId(null);
        assertThat(holeDataDTO1).isNotEqualTo(holeDataDTO2);
    }
}
