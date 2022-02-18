package stop.covid.challenge.cafein.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stop.covid.challenge.cafein.domain.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByKakaoId(String kakaoId);

}
