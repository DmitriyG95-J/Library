package com.example.mylibrary.service.impl;

import com.example.mylibrary.configuration.JwtTokenProvider;
import com.example.mylibrary.configuration.UserAuthenticationProvider;
import com.example.mylibrary.dto.req.CredentialsDTO;
import com.example.mylibrary.dto.req.RegisterReqDTO;
import com.example.mylibrary.dto.resp.TokenRespDTO;
import com.example.mylibrary.dto.UserDTO;
import com.example.mylibrary.model.User;
import com.example.mylibrary.repository.UserRepo;
import com.example.mylibrary.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;

   private final UserRepo userRepo;

   //private final UserAuthenticationProvider userAuthenticationProvider;
   private final JwtTokenProvider jwtTokenProvider;

    @Override
    public UserDTO findByEmail(String email) {
        Optional<User> userOptional = userRepo.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new UserDTO(user);
        } else {
            throw new BadCredentialsException("User not found");
        }
    }

    @Override
    public TokenRespDTO authenticate(CredentialsDTO credentialsDTO) {

        String userEmail = credentialsDTO.getEmail();
        String userPassword = credentialsDTO.getPassword();
        Optional<User> userOptional = userRepo.findByEmail(userEmail);

        if (userOptional.isEmpty()) {
            throw new BadCredentialsException("Пользователь не зарегистрирован");
        }
        User user = userOptional.get();
        if(!passwordEncoder.matches(userPassword, user.getPassword())) {
            throw new BadCredentialsException("Неверный пароль");
        }
        String jwtToken = jwtTokenProvider.createToken(userEmail);
        user.setAccessToken(jwtToken);
        userRepo.saveAndFlush(user);

        TokenRespDTO tokenRespDTO = new TokenRespDTO();
        tokenRespDTO.setAccessToken("Bearer " + jwtToken);

        return tokenRespDTO;
    }

    @Override
    public void registerUser(RegisterReqDTO registerReqDTO, HttpServletRequest request) {
        if (userRepo.findByEmail(registerReqDTO.getEmail()).isPresent()) {
            throw new RuntimeException("пользователь уже зарегистрирован");
        }
        User user = new User();
        user.setUserName(registerReqDTO.getUserName());
        user.setEmail(registerReqDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerReqDTO.getPassword()));
        userRepo.save(user);
    }


}
