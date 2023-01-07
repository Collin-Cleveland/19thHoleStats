package rocks.zipcode.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import rocks.zipcode.web.rest.TestUtil;

class VideoUserDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VideoUserDTO.class);
        VideoUserDTO videoUserDTO1 = new VideoUserDTO();
        videoUserDTO1.setId(1L);
        VideoUserDTO videoUserDTO2 = new VideoUserDTO();
        assertThat(videoUserDTO1).isNotEqualTo(videoUserDTO2);
        videoUserDTO2.setId(videoUserDTO1.getId());
        assertThat(videoUserDTO1).isEqualTo(videoUserDTO2);
        videoUserDTO2.setId(2L);
        assertThat(videoUserDTO1).isNotEqualTo(videoUserDTO2);
        videoUserDTO1.setId(null);
        assertThat(videoUserDTO1).isNotEqualTo(videoUserDTO2);
    }
}
