package stop.covid.challenge.cafein.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stop.covid.challenge.cafein.domain.model.Following;
import stop.covid.challenge.cafein.domain.model.User;

import java.util.List;

@Repository
public interface FollowingRepository extends JpaRepository<Following, Long> {
    List<Following> findAllByUser(User user);
}
