package stop.covid.challenge.cafein.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stop.covid.challenge.cafein.domain.model.Follow;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
}
