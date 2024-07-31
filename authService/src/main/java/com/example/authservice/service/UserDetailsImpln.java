package com.example.authservice.service;

import com.example.authservice.entities.UserInfo;
import com.example.authservice.model.UserInfoDTO;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.utils.ValidationUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@Component
@Data
@AllArgsConstructor
public class UserDetailsImpln implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {

        UserInfo user = userRepository.findByUsername(username);
        if(user == null){

            throw new UsernameNotFoundException("could not found user..!!");
        }

        return new CustomUserDetails(user);
    }

    public UserInfo checkIfUserAlreadyExist(UserInfoDTO userInfoDto){
        return userRepository.findByUsername(userInfoDto.getUsername());
    }

    public Boolean signupUser(UserInfoDTO userInfoDto){
//        System.out.println(userInfoDto.getUsername()+" "+userInfoDto.getPassword());
//        boolean user=  ValidationUtil.validateUserAttributes(userInfoDto.getEmail(),userInfoDto.getPassword());
//        System.out.println(user);

            userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
            if (Objects.nonNull(checkIfUserAlreadyExist(userInfoDto))) {
                      return false;
                  }
            String userId = UUID.randomUUID().toString();
            userRepository.save(new UserInfo(userId, userInfoDto.getUsername(), userInfoDto.getPassword(), new HashSet<>()));
//            System.out.println("userid is"+" " + userId);
            return true;

    }
}
