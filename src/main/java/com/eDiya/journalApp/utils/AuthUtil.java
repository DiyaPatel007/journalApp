package com.eDiya.journalApp.utils;

import com.eDiya.journalApp.enums.AuthProviderType;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@Slf4j
public class AuthUtil {
    public AuthProviderType getProviderTypeFromRegistrationId(String registrationId) {
        return switch(registrationId.toLowerCase()){
            case "google" -> AuthProviderType.GOOGLE;
            case "facebook" -> AuthProviderType.FACEBOOK;
            case "github" -> AuthProviderType.GITHUB;
            default -> {
                throw new IllegalArgumentException("Unsupported provider: " + registrationId);
            }
        };
    }

    public String getProviderIdFromOAuth2User(OAuth2User oAuth2User, String registrationId) {
        String providerId = switch(registrationId.toLowerCase()) {
            case "google" -> oAuth2User.getAttribute("sub");
            case "github" -> oAuth2User.getAttribute("id").toString();
            default -> {
                log.error("Unsupported provider: {}",registrationId);
                throw new IllegalArgumentException("Unable to determine provider id for OAUth2 login");
            }
        };
        if(providerId == null || providerId.isBlank()) {
            log.error("Unable to determine providerId for provider: {}", registrationId);
            throw new IllegalArgumentException("Unable to determine provider id for OAUth2 login");
        }
        return providerId;
    }

    public String getUsernameFromOAuth2User(OAuth2User oAuth2User, String registrationId, String providerId) {
        String email = oAuth2User.getAttribute("email");
        if(email != null && !email.isBlank()) {
            return email;
        }
        return switch(registrationId.toLowerCase()) {
            case "google" -> oAuth2User.getAttribute("sub");
            case "github" -> oAuth2User.getAttribute("login");
            default -> providerId;
        };
    }
}
