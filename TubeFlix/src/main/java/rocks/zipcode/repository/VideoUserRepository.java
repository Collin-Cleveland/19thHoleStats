package rocks.zipcode.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import rocks.zipcode.domain.VideoUser;

/**
 * Spring Data JPA repository for the VideoUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VideoUserRepository extends JpaRepository<VideoUser, Long> {}
