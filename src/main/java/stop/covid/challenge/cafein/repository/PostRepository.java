package stop.covid.challenge.cafein.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stop.covid.challenge.cafein.domain.model.Post;
import stop.covid.challenge.cafein.domain.model.User;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUser(User user);

}
