package rocks.zipcode.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import rocks.zipcode.domain.Golfer;

/**
 * Spring Data JPA repository for the Golfer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GolferRepository extends JpaRepository<Golfer, Long> {}
