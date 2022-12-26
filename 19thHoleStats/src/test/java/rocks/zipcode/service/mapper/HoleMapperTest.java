package rocks.zipcode.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HoleMapperTest {

    private HoleMapper holeMapper;

    @BeforeEach
    public void setUp() {
        holeMapper = new HoleMapperImpl();
    }
}
