package com.user.userservice.controller;

import com.user.userservice.entities.UserInfoDTO;
import com.user.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

  @Autowired
  UserService userService;

  @GetMapping("/user/v1/getUser")
  public ResponseEntity<UserInfoDTO> getUser(@RequestBody UserInfoDTO userInfoDTO){
      try{
          UserInfoDTO user = userService.getUser(userInfoDTO);
          return new ResponseEntity<>(user, HttpStatus.OK);
      }catch (Exception ex){
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
  }

  @PostMapping("/user/v1/createUpdate")
    public ResponseEntity<UserInfoDTO> createUpdateUser( UserInfoDTO userInfoDTO){
      try{
          UserInfoDTO user = userService.createOrUpdateUser(userInfoDTO);
          return new ResponseEntity<>(user, HttpStatus.OK);
      }catch (Exception ex){
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
  }


}
