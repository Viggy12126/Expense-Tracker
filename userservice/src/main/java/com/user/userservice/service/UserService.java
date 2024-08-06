package com.user.userservice.service;

import com.user.userservice.entities.UserInfo;
import com.user.userservice.entities.UserInfoDTO;
import com.user.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserInfoDTO createOrUpdateUser(UserInfoDTO userInfoDto){
        UnaryOperator<UserInfo> updatingUser = user -> {

            user.setFirstName(userInfoDto.getFirstName());
            user.setLastName(userInfoDto.getLastName());
            user.setPhoneNumber(userInfoDto.getPhoneNumber());
            user.setEmail(userInfoDto.getEmail());
            user.setProfilePic(userInfoDto.getProfilePic());
            return userRepository.save(user);
        };

        Supplier<UserInfo> createUser = () -> {
            return userRepository.save(userInfoDto.transformToUserInfo());
        };

        UserInfo userInfo = userRepository.findByUserId(userInfoDto.getUserId())
                .map(updatingUser)
                .orElseGet(createUser);
        return new UserInfoDTO(
                userInfo.getUserId(),
                userInfo.getFirstName(),
                userInfo.getLastName(),
                userInfo.getPhoneNumber(),
                userInfo.getEmail(),
                userInfo.getProfilePic()
        );
    }

}
