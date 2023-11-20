package com.moviereview.movieproject.controller;

import com.moviereview.movieproject.model.AuthenticationResponse;
import com.moviereview.movieproject.model.LoginDetails;
import com.moviereview.movieproject.model.NewUserDetails;
import com.moviereview.movieproject.repository.UserRepository;
import com.moviereview.movieproject.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private  final AuthenticationService service;

    private final UserRepository repository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody NewUserDetails newUser){

        if (repository.findByEmail(newUser.getEmail()).isEmpty()){
            return  ResponseEntity.ok(service.registerUser(newUser));

        }else  return  new ResponseEntity<String>("Email Id already registered", HttpStatus.IM_USED);


    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody LoginDetails loginUser){

        return  ResponseEntity.ok(service.loginUser(loginUser));


    }
    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request){
        System.out.println("inside logout....");
        HttpSession session = request.getSession();
        session.invalidate();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.clearContext();
        authentication.setAuthenticated(false);
        return new ResponseEntity<String>("logged out Successfully", HttpStatus.OK);

    }

}
