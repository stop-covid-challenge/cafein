package stop.covid.challenge.cafein.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stop.covid.challenge.cafein.domain.model.Comment;
import stop.covid.challenge.cafein.domain.model.PersonalCafe;
import stop.covid.challenge.cafein.domain.model.Post;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findCommentByPersonalCafeAndPost(PersonalCafe personalCafe, Post post);

}
