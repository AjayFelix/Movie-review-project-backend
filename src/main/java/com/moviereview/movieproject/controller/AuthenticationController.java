package com.moviereview.movieproject.controller;

import com.moviereview.movieproject.model.AuthenticationResponse;
import com.moviereview.movieproject.model.LoginDetails;
import com.moviereview.movieproject.model.NewUserDetails;
import com.moviereview.movieproject.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private  final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody NewUserDetails newUser){
        System.out.println("we are in side the conroller");

        return  ResponseEntity.ok(service.registerUser(newUser));


    }
    @GetMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody LoginDetails loginUser){

        return  ResponseEntity.ok(service.loginUser(loginUser));


    }

}
