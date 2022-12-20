package rocks.zipcode.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import rocks.zipcode.domain.HoleData;

/**
 * Spring Data JPA repository for the HoleData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HoleDataRepository extends JpaRepository<HoleData, Long> {}
