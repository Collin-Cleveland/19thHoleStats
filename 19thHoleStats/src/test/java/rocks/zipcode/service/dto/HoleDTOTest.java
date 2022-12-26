package rocks.zipcode.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import rocks.zipcode.web.rest.TestUtil;

class HoleDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HoleDTO.class);
        HoleDTO holeDTO1 = new HoleDTO();
        holeDTO1.setId(1L);
        HoleDTO holeDTO2 = new HoleDTO();
        assertThat(holeDTO1).isNotEqualTo(holeDTO2);
        holeDTO2.setId(holeDTO1.getId());
        assertThat(holeDTO1).isEqualTo(holeDTO2);
        holeDTO2.setId(2L);
        assertThat(holeDTO1).isNotEqualTo(holeDTO2);
        holeDTO1.setId(null);
        assertThat(holeDTO1).isNotEqualTo(holeDTO2);
    }
}
