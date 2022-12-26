package rocks.zipcode.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GolferMapperTest {

    private GolferMapper golferMapper;

    @BeforeEach
    public void setUp() {
        golferMapper = new GolferMapperImpl();
    }
}
