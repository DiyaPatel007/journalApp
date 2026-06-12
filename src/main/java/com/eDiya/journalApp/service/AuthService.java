package com.eDiya.journalApp.service;

import com.eDiya.journalApp.entity.User;
import com.eDiya.journalApp.enums.AuthProviderType;
import com.eDiya.journalApp.repository.UserRepository;
import com.eDiya.journalApp.utils.AuthUtil;
import com.eDiya.journalApp.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AuthService {
    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public ResponseEntity<String> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId){

        AuthProviderType providerType = authUtil.getProviderTypeFromRegistrationId(registrationId);
        String providerId = authUtil.getProviderIdFromOAuth2User(oAuth2User, registrationId);

        User user = userRepository.findByProviderIdAndAuthProviderType(providerId,providerType).orElse(null);

        String email = oAuth2User.getAttribute("email");

        if (email == null || email.isBlank()) {
            log.error("Email not provided by OAuth2 provider: {}", registrationId);
            return ResponseEntity.badRequest().body("Email not provided by OAuth2 provider!");
        }

        User emailUser = userRepository.findByEmail(email);

        if(user == null && emailUser == null){
            String userName = authUtil.getUsernameFromOAuth2User(oAuth2User, registrationId, providerId);
            user = userService.saveNewUserWithoutPassword(userName,email,providerId,providerType);

            if (user == null) {
                return ResponseEntity.internalServerError().body("Failed to register user!");
            }

        } else if(user != null) {
            if(email!=null && !email.isBlank() && !email.equals(user.getEmail())){
                user.setEmail(email);
                userService.saveEntry(user);
            }
        } else {
            throw new BadCredentialsException(
                    "This email is already registered with " + emailUser.getAuthProviderType() +
                            ". Please login with " + emailUser.getAuthProviderType() +
                            " or use another email to login with " + registrationId
            );
        }

        String jwtToken = jwtUtil.generateToken(user.getUserName());
        return ResponseEntity.ok(jwtToken);
    }
}
