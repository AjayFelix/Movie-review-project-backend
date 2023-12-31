package com.moviereview.movieproject.service;

import com.moviereview.movieproject.model.*;
import com.moviereview.movieproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private  final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    private final AuthenticationManager manager;
    public AuthenticationResponse registerUser(NewUserDetails newUser) {

        var user = User.builder()
                .userName(newUser.getUserName())
                .email(newUser.getEmail())
                .password(passwordEncoder.encode(newUser.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);

        var jwtToker = userService.generateToken(user);

        return  AuthenticationResponse
                .builder()
                .token(jwtToker)
                .build();


    }

    public AuthenticationResponse loginUser(LoginDetails loginUser) {
       Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(
                loginUser.getEmail(),loginUser.getPassword()
        ));
       Collection<? extends GrantedAuthority> authorities= authentication.getAuthorities();

      var user = repository.findByEmail(loginUser.getEmail())
              .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        var jwtToken = userService.generateToken(user,authorities);

        return  AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }


}
