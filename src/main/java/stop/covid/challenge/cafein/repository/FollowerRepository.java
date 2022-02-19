package stop.covid.challenge.cafein.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stop.covid.challenge.cafein.domain.model.Follower;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {
}
