package stop.covid.challenge.cafein.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stop.covid.challenge.cafein.domain.model.HashTag;

@Repository
public interface HashTagRepository extends JpaRepository<HashTag, Long> {
}
