package com.securepay.securepay.controller;

import com.securepay.securepay.auth.JwtService;
import com.securepay.securepay.entities.User;
import com.securepay.securepay.entities.request.AuthenticationRequest;
import com.securepay.securepay.entities.request.RegisterRequest;
import com.securepay.securepay.entities.response.AuthenticationResponse;
import com.securepay.securepay.repos.UserRepository;
import com.securepay.securepay.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.mock;

public class CustomerServiceTest {
     private  UserRepository userRepository;
    private  PasswordEncoder passwordEncoder;
    private  JwtService jwtService;
    private AuthenticationManager authenticationManager;

    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtService = mock(JwtService.class);
        authenticationManager = mock(AuthenticationManager.class);
        customerService = new CustomerService(userRepository, passwordEncoder, jwtService, authenticationManager);
    }

    @Test
    public void whenRegisterRequest_thenRegisterUser() {
        RegisterRequest request = RegisterRequest.builder()
                .email("test@gmail.com")
                .firstname("test")
                .lastname("test")
                .password("test")
                .build();
        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        AuthenticationResponse response = AuthenticationResponse.builder()
                .token("")
                .success(true)
                .message("Registered")
                .build();

        Mockito.when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        Mockito.when(jwtService.generateToken(user)).thenReturn("");
        Mockito.when(userRepository.save(user)).thenReturn(user);

        var result = customerService.register(request);
        assert(result.getToken().equals(response.getToken()));

        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(request.getEmail());
        Mockito.verify(jwtService, Mockito.times(1)).generateToken(user);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);

    }

    @Test
    public void whenRegisterRequest_thenCheckAlreadyRegisteredUserAndFail() {
        RegisterRequest request = RegisterRequest.builder()
                .email("test@gmail.com")
                .firstname("test")
                .lastname("test")
                .password("test")
                .build();
        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        AuthenticationResponse response = AuthenticationResponse.builder()
                .token("")
                .success(false)
                .message("User already exists")
                .build();

        Mockito.when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        var result = customerService.register(request);
        assert(result.getToken().equals(response.getToken()));
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(request.getEmail());
    }

    @Test
    public void whenAuthenticateRequest_thenAuthenticateUser() {
        var request = AuthenticationRequest.builder()
                .email("test@gmail.com")
                .password("test")
                .build();
        var user = User.builder()
                .firstName("test")
                .lastName("test")
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        var response = AuthenticationResponse.builder()
                .token("")
                .success(true)
                .message("")
                .build();
        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(null);
        Mockito.when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        Mockito.when(jwtService.generateToken(user)).thenReturn("");
        var result = customerService.authenticate(request);
        assert(result.getToken().equals(response.getToken()));
        Mockito.verify(authenticationManager, Mockito.times(1)).authenticate(Mockito.any());
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(request.getEmail());
        Mockito.verify(jwtService, Mockito.times(1)).generateToken(user);
    }

    @Test
    public void whenFindByUserId_thenReturnUser(){
        Integer userId = 1;
        var user = User.builder()
                .firstName("test")
                .lastName("test")
                .email("test@gmail.com")
                .password("test")
                .id(1)
                .build();
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        var result = customerService.findUserByUserId(userId);
        assert(result.equals(user));
        Mockito.verify(userRepository, Mockito.times(1)).findById(user.getId());
    }

    @Test
    public void whenFindUserByEmail_theReturnUser(){
        var user = User.builder()
                .firstName("test")
                .lastName("test")
                .email("test@gmail.com")
                .password("test")
                .id(1)
                .build();
        String email = "test@gmail.com";
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        var result = customerService.findUserByEmail(email);
        assert(result.equals(user));
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(email);
    }
}
