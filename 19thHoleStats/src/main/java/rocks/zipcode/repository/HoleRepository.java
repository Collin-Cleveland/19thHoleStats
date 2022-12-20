package rocks.zipcode.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import rocks.zipcode.domain.Hole;

/**
 * Spring Data JPA repository for the Hole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HoleRepository extends JpaRepository<Hole, Long> {}
