package rocks.zipcode.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClubMapperTest {

    private ClubMapper clubMapper;

    @BeforeEach
    public void setUp() {
        clubMapper = new ClubMapperImpl();
    }
}
