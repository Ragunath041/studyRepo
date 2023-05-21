package com.project.ServerSide.controller;

import com.project.ServerSide.Repository.UserRepo;
import com.project.ServerSide.model.UserInfo;
import com.project.ServerSide.payload.request.loginRequest;
import com.project.ServerSide.payload.request.signupRequest;
import com.project.ServerSide.payload.response.messageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class userController {
    @Autowired
    private UserRepo Repo;

    @GetMapping("/login")
    private boolean login(@RequestBody loginRequest loginRequest){
        if(Repo.existsByUsername(loginRequest.getUserName())&&Repo.existByPass(loginRequest.getPassword())){
            return true;
        }
        else {
            return false;
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registeruser(@RequestBody signupRequest signupRequest){

        if(Repo.existsByUsername(signupRequest.getUserName())){
            return ResponseEntity
                    .badRequest()
                    .body(new messageResponse("Error : the userName already existing!!"));
        }
        if(Repo.existByEmail(signupRequest.getEmail())){
            return ResponseEntity
                    .badRequest()
                    .body(new messageResponse("Error : the email already in Use!!"));
        }

        UserInfo un = new UserInfo(signupRequest.getId(),signupRequest.getUserName(),signupRequest.getEmail(),signupRequest.getPassword(),signupRequest.getRole());
        Repo.save(un);

        return ResponseEntity.ok(new messageResponse("User Registration successful!"));

    }

}
