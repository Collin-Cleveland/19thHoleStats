package rocks.zipcode.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VideoUserMapperTest {

    private VideoUserMapper videoUserMapper;

    @BeforeEach
    public void setUp() {
        videoUserMapper = new VideoUserMapperImpl();
    }
}
