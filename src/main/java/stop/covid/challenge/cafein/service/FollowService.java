package stop.covid.challenge.cafein.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stop.covid.challenge.cafein.domain.model.Follower;
import stop.covid.challenge.cafein.domain.model.Following;
import stop.covid.challenge.cafein.domain.model.User;
import stop.covid.challenge.cafein.dto.FollowListDto;
import stop.covid.challenge.cafein.repository.FollowerRepository;
import stop.covid.challenge.cafein.repository.FollowingRepository;
import stop.covid.challenge.cafein.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {

    private final UserRepository userRepository;
    private final FollowerRepository followerRepository;
    private final FollowingRepository followingRepository;

    // 사용자의 Follower 가져오기
    public List<FollowListDto> getFollower(Long id) {
        // 사용자의 Follower를 가져오기
        User host = userRepository.findById(id).get();
        List<Follower> followers = followerRepository.findAllByUser(host);
        List<FollowListDto> followDtos = new ArrayList<>();

        // 각 Follow를 조회하여 프로필 이미지와 닉네임 가져오기
        followers.forEach(follower -> {
            FollowListDto followDto = new FollowListDto();
            User user = userRepository.findById(follower.getFollowing_id()).get();
            followDto.setProfile_image(user.getProfileImage());
            followDto.setNickname(user.getNickname());
            followDtos.add(followDto);
        });
        return followDtos;
    }

    // 사용자의 Following 가져오기
    public List<FollowListDto> getFollowing(Long id) {
        // 사용자의 Following 가져오기
        User host = userRepository.findById(id).get();
        List<Following> followings = followingRepository.findAllByUser(host);
        List<FollowListDto> followDtos = new ArrayList<>();

        // 각 Follow를 조회하여 프로필 이미지와 닉네임 가져오기
        followings.forEach(following -> {
            FollowListDto followDto = new FollowListDto();
            User user = userRepository.findById(following.getFollower_id()).get();
            followDto.setProfile_image(user.getProfileImage());
            followDto.setNickname(user.getNickname());
            followDtos.add(followDto);
        });
        return followDtos;
    }

    @Transactional
    public Boolean setFollow(Long my_id, Long other_id) {
        // 내 객체
        User host = userRepository.findById(my_id).get();

        // 상대방 객체
        User otherUser = userRepository.findById(other_id).get();

        // 내 팔로잉 목록
        List<Following> followings = followingRepository.findAllByUser(host);
        boolean isExist = false;

        // 내 Following
        Following myFollowing = new Following();

        // 상대방 Follower
        Follower otherFollower = new Follower();

        // 내 팔로잉 목록중에서 상대방이 있는지 에크하기
        for (Following following : followings) {
            if (following.getFollower_id() == other_id) {
                isExist = true;
                myFollowing = following;
                break;
            }
        }

        if (isExist) {
            // 내가 팔로잉 하고 있다면 취소하기
            followingRepository.delete(myFollowing);

            // 상대방 팔로워 목록에서도 삭제하기
            List<Follower> followers = followerRepository.findAllByUser(otherUser);
            for (Follower compare : followers) {
                if (compare.getFollowing_id() == host.getId()) {
                    otherFollower = compare;
                    break;
                }
            }
            followerRepository.delete(otherFollower);
            return false;
        } else {
            // 내가 팔로우 안하고 있다면 추가하기
            myFollowing.setUser(host);
            myFollowing.setFollower_id(other_id);
            followingRepository.save(myFollowing);

            // 상대방 팔로워에다가도 추가하기
            otherFollower.setUser(otherUser);
            otherFollower.setFollowing_id(my_id);
            followerRepository.save(otherFollower);
            return true;
        }
    }

}
