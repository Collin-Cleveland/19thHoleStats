package rocks.zipcode.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rocks.zipcode.domain.HoleData;
import rocks.zipcode.domain.Scorecard;
import rocks.zipcode.service.dto.HoleDataDTO;
import rocks.zipcode.service.dto.ScorecardDTO;

/**
 * Spring Data JPA repository for the HoleData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HoleDataRepository extends JpaRepository<HoleData, Long> {
// List<HoleData> findAllByScorecard(Optional<ScorecardDTO> scorecard);

//ADDED METHOD
@Query(value = "SELECT * FROM Hole_Data WHERE Scorecard_id = :scorecardid", nativeQuery = true)
public List<HoleDataDTO> findAllForScorecard (@Param("scorecardid") Long id);
}