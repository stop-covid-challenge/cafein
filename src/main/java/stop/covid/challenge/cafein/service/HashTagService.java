package stop.covid.challenge.cafein.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stop.covid.challenge.cafein.domain.model.HashTag;
import stop.covid.challenge.cafein.domain.model.Menu;
import stop.covid.challenge.cafein.repository.HashTagRepository;
import stop.covid.challenge.cafein.repository.MenuRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HashTagService {

    private final HashTagRepository hashTagRepository;

    public List<HashTag> getHashTag(String keyword) {
        List<HashTag> hashTags = hashTagRepository.findAllByTagContaining(keyword);
        return hashTags;
    }

}
