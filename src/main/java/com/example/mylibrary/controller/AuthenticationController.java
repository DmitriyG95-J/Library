package com.example.mylibrary.controller;

import com.example.mylibrary.configuration.UserAuthenticationProvider;
import com.example.mylibrary.dto.req.CredentialsDTO;
import com.example.mylibrary.dto.req.RegisterReqDTO;
import com.example.mylibrary.dto.resp.TokenRespDTO;
import com.example.mylibrary.service.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Api(description = "Контроллер для авторизации и регистрации")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserAuthenticationProvider userAuthenticationProvider;



    @PostMapping("/login")
    @ApiOperation(("Ендпоинт для авторизации, принимает два request body с  - email и  password, " +
            "в которых хранится email и пароль соответственно, " +
            "возвращает response с header'ом Authorization в формате Bearer jwtTokenInStringFormat"))
    public TokenRespDTO authenticate(@RequestBody CredentialsDTO credentialsDTO) {
        return authenticationService.authenticate(credentialsDTO);
    }

    @PostMapping("/register")
    @ApiOperation(("Эндпоинт для регистрации нового пользователя, " +
            "после регистрации для доступа к основным частям сайта нужно пройти верификацию"))
    public void registerUser(@Valid @RequestBody RegisterReqDTO registerReqDTO, HttpServletRequest httpServletRequest) {
        authenticationService.registerUser(registerReqDTO, httpServletRequest);
    }

}
