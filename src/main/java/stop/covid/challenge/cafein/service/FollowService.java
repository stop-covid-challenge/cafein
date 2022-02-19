package stop.covid.challenge.cafein.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stop.covid.challenge.cafein.domain.model.Follower;
import stop.covid.challenge.cafein.domain.model.Following;
import stop.covid.challenge.cafein.domain.model.PersonalCafe;
import stop.covid.challenge.cafein.dto.FollowDto;
import stop.covid.challenge.cafein.repository.FollowerRepository;
import stop.covid.challenge.cafein.repository.FollowingRepository;
import stop.covid.challenge.cafein.repository.PersonalCafeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {

    private final PersonalCafeRepository personalCafeRepository;
    private final FollowerRepository followerRepository;
    private final FollowingRepository followingRepository;

    // 사용자의 Follower 가져오기
    public List<FollowDto> getFollower(Long id) {
        // 사용자의 Follower를 가져오기
        List<Follower> followers = personalCafeRepository.findById(id).orElseThrow().getFollowers();
        List<FollowDto> followDtos = new ArrayList<>();

        // 각 Follow를 조회하여 프로필 이미지와 닉네임 가져오기
        followers.forEach(follower -> {
            FollowDto followDto = new FollowDto();
            PersonalCafe personalCafe = personalCafeRepository.findById(follower.getFollowing_id()).get();
            followDto.setProfile_image(personalCafe.getProfileImage());
            followDto.setNickname(personalCafe.getNickname());
            followDtos.add(followDto);
        });
        return followDtos;
    }

    // 사용자의 Following 가져오기
    public List<FollowDto> getFollowing(Long id) {
        // 사용자의 Following 가져오기
        List<Following> followings = personalCafeRepository.findById(id).orElseThrow().getFollowings();
        List<FollowDto> followDtos = new ArrayList<>();

        // 각 Follow를 조회하여 프로필 이미지와 닉네임 가져오기
        followings.forEach(following -> {
            FollowDto followDto = new FollowDto();
            PersonalCafe personalCafe = personalCafeRepository.findById(following.getFollower_id()).get();
            followDto.setProfile_image(personalCafe.getProfileImage());
            followDto.setNickname(personalCafe.getNickname());
            followDtos.add(followDto);
        });
        return followDtos;
    }

    @Transactional
    public Boolean setFollow(Long my_id, Long follow_id) {
        // 먼저 내가 팔로잉하고 있는지 체크하기
        List<Following> followings = personalCafeRepository.findById(my_id).orElseThrow().getFollowings();

        // 1. 팔로잉 하고 있다면 취소하기

    }

}
