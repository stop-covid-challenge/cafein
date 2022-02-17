package stop.covid.challenge.cafein.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stop.covid.challenge.cafein.domain.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
