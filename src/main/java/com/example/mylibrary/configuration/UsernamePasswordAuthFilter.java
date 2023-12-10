package com.example.mylibrary.configuration;

import com.example.mylibrary.dto.req.CredentialsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class UsernamePasswordAuthFilter extends OncePerRequestFilter {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    public UsernamePasswordAuthFilter(UserAuthenticationProvider provider) {
        this.provider = provider;
    }
    private final UserAuthenticationProvider provider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if ("/auth/login".equals(request.getMethod())
            && HttpMethod.POST.matches(request.getMethod())) {
            CredentialsDTO credentialsDTO = MAPPER.readValue(request.getInputStream(), CredentialsDTO.class);

            try {
                SecurityContextHolder.getContext().setAuthentication(
                        provider.validateCredentials(credentialsDTO)
                );
            } catch (RuntimeException e) {
                SecurityContextHolder.clearContext();
                throw e;
            }
        }
        filterChain.doFilter(request, response);
    }
}
