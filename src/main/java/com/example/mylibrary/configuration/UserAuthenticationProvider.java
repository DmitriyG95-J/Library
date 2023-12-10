package com.example.mylibrary.configuration;

import java.util.Collections;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.mylibrary.dto.req.CredentialsDTO;
import com.example.mylibrary.dto.UserDTO;
import com.example.mylibrary.dto.resp.TokenRespDTO;
import com.example.mylibrary.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenticationProvider {
    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;
    private final AuthenticationService authenticationService;
    private final JwtTokenProvider jwtTokenProvider;

    public UserAuthenticationProvider(AuthenticationService authenticationService, JwtTokenProvider jwtTokenProvider) {
        this.authenticationService = authenticationService;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        DecodedJWT decoded = verifier.verify(token);
        UserDTO user = authenticationService.findByEmail(decoded.getIssuer());
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }
    public Authentication validateCredentials(CredentialsDTO credentialsDTO) {
        TokenRespDTO user = authenticationService.authenticate(credentialsDTO);
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }


}