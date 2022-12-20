package rocks.zipcode.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import rocks.zipcode.web.rest.TestUtil;

class HoleDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HoleData.class);
        HoleData holeData1 = new HoleData();
        holeData1.setId(1L);
        HoleData holeData2 = new HoleData();
        holeData2.setId(holeData1.getId());
        assertThat(holeData1).isEqualTo(holeData2);
        holeData2.setId(2L);
        assertThat(holeData1).isNotEqualTo(holeData2);
        holeData1.setId(null);
        assertThat(holeData1).isNotEqualTo(holeData2);
    }
}
