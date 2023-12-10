package com.example.mylibrary.service;

import com.example.mylibrary.dto.UserDTO;
import com.example.mylibrary.dto.req.CredentialsDTO;
import com.example.mylibrary.dto.req.RegisterReqDTO;
import com.example.mylibrary.dto.resp.TokenRespDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
     UserDTO findByEmail(String email);

     TokenRespDTO authenticate(CredentialsDTO credentialsDTO);

     void registerUser(RegisterReqDTO registerReqDTO, HttpServletRequest request);
}
