package ru.dsi.geekbrains.testproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.dsi.geekbrains.testproject.common.JwtAuthRequestDto;
import ru.dsi.geekbrains.testproject.common.JwtAuthResponseDto;
import ru.dsi.geekbrains.testproject.common.UserDto;
import ru.dsi.geekbrains.testproject.configs.JwtTokenUtil;
import ru.dsi.geekbrains.testproject.entities.User;
import ru.dsi.geekbrains.testproject.services.JwtUserDetailsService;

import java.util.Collections;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private JwtUserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, JwtUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthRequestDto authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
//            throw new Exception("Incorrect username or password", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        }

        User user = userDetailsService.loadUser(authenticationRequest.getUsername());
        final UserDetails userDetails = userDetailsService.getUserDetailsByUser(user);//userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        //Добавляем id авторизованного пользователя в claims
        final String token = jwtTokenUtil.generateToken(userDetails, Collections.singletonMap("userId", user.getId()));
        return ResponseEntity.ok(new JwtAuthResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody UserDto user) {
        return ResponseEntity.ok(userDetailsService.save(user));
    }
}