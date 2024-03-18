package com.securepay.securepay.service;

import com.securepay.securepay.auth.JwtService;
import com.securepay.securepay.entities.User;
import com.securepay.securepay.entities.request.AuthenticationRequest;
import com.securepay.securepay.entities.request.RegisterRequest;
import com.securepay.securepay.entities.response.AuthenticationResponse;
import com.securepay.securepay.repos.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public CustomerService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request) {

        boolean isUserExists =  userRepository.findByEmail(request.getEmail()).isPresent();
        if (isUserExists){
            return AuthenticationResponse.builder()
                    .token("")
                    .success(false)
                    .message("User already exists")
                    .build();
        }

        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        user = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .success(true)
                .message("Registered")
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .success(true)
                .message("Authenticated")
                .build();
        }catch (Exception e){
            return AuthenticationResponse.builder()
                    .token("")
                    .message("Invalid credentials")
                    .success(false)
                    .build();
        }
    }

    public User findUserByUserId(Integer userId) {
       return userRepository.findById(userId).orElse(null);
    }

    public User findUserByEmail(String customerEmail) {
        return userRepository.findByEmail(customerEmail).orElse(null);
    }

}
