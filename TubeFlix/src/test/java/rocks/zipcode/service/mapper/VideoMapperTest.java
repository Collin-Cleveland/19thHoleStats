package rocks.zipcode.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VideoMapperTest {

    private VideoMapper videoMapper;

    @BeforeEach
    public void setUp() {
        videoMapper = new VideoMapperImpl();
    }
}
