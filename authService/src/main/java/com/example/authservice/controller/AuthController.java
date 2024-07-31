package com.example.authservice.controller;
import com.example.authservice.entities.RefreshToken;
import com.example.authservice.model.UserInfoDTO;
import com.example.authservice.response.JwtResponseDTO;
import com.example.authservice.service.JwtService;
import com.example.authservice.service.RefreshTokenService;
import com.example.authservice.service.UserDetailsImpln;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserDetailsImpln userDetailsService;

    @PostMapping("auth/v1/signup")
    public ResponseEntity SignUp(@RequestBody UserInfoDTO userInfoDto){
        try{
//            System.out.println(userInfoDto.getUsername());
            Boolean isSign = userDetailsService.signupUser(userInfoDto);
            if(Boolean.FALSE.equals(isSign)){
                return new ResponseEntity<>("Already Exist", HttpStatus.BAD_REQUEST);
            }
//            System.out.println("isSign is "+" "+ isSign);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userInfoDto.getUsername());
            String jwtToken = jwtService.GenerateToken(userInfoDto.getUsername());
//            System.out.println(refreshToken+" "+jwtToken);
            return new ResponseEntity<>(JwtResponseDTO.builder().accessToken(jwtToken).
                    token(refreshToken.getToken()).build(), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>("Exception in User Service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
