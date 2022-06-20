package stop.covid.challenge.cafein.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stop.covid.challenge.cafein.domain.model.Follower;
import stop.covid.challenge.cafein.domain.model.PersonalCafe;

import java.util.List;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {
    List<Follower> findAllByPersonalCafe(PersonalCafe personalCafe);
}
