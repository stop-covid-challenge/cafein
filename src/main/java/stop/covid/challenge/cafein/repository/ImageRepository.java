package stop.covid.challenge.cafein.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stop.covid.challenge.cafein.domain.model.Image;
import stop.covid.challenge.cafein.domain.model.Menu;
import stop.covid.challenge.cafein.domain.model.Post;
import stop.covid.challenge.cafein.domain.model.Product;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByMenu(Menu menu);
    List<Image> findAllByPost(Post post);
    List<Image> findAllByProduct(Product product);
}
