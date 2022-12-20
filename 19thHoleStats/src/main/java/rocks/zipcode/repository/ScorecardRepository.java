package rocks.zipcode.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import rocks.zipcode.domain.Scorecard;

/**
 * Spring Data JPA repository for the Scorecard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScorecardRepository extends JpaRepository<Scorecard, Long> {}
