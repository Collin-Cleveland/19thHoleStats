package rocks.zipcode.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScorecardMapperTest {

    private ScorecardMapper scorecardMapper;

    @BeforeEach
    public void setUp() {
        scorecardMapper = new ScorecardMapperImpl();
    }
}
