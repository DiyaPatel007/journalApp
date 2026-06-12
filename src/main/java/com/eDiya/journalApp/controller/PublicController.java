package com.eDiya.journalApp.controller;

import com.eDiya.journalApp.dto.UserDTO;
import com.eDiya.journalApp.entity.User;
import com.eDiya.journalApp.repository.UserRepository;
import com.eDiya.journalApp.service.UserDetailsServiceImpl;
import com.eDiya.journalApp.service.UserService;
import com.eDiya.journalApp.utils.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/public")
@Tag(name = "Public APIs", description="Endpoints accessible without authentication")
public class PublicController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("health-check")
    public String healthCheck(){
        return "ok";
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDTO user) {

        if (userService.findByUserName(user.getUserName()) != null) {
            return new ResponseEntity<>("Username already exists!", HttpStatus.CONFLICT);
        }

        if (user.getEmail() != null && userService.findByEmail(user.getEmail()) != null) {
            return new ResponseEntity<>("Email already registered!", HttpStatus.CONFLICT);
        }

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setUserName(user.getUserName());
        newUser.setPassword(user.getPassword());
        newUser.setSentimentAnalysis(user.isSentimentAnalysis());

        boolean saved = userService.saveNewUser(newUser);
        if(saved){
            return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Failed to register user!", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        if (user.getUserName() == null || user.getUserName().isBlank()) {
            return new ResponseEntity<>("Username is required!", HttpStatus.BAD_REQUEST);
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            return new ResponseEntity<>("Password is required!", HttpStatus.BAD_REQUEST);
        }

        if (userService.findByUserName(user.getUserName()) == null) {
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
        }

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);

        }catch (Exception e){
            log.error("Exception occurred while createAuthenticationToken",e);
            return new ResponseEntity<>("Invalid username or password", HttpStatus.BAD_REQUEST);
        }
    }

}
