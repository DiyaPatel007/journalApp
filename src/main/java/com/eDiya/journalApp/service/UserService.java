package com.eDiya.journalApp.service;

import com.eDiya.journalApp.entity.User;
import com.eDiya.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveEntry(User user){
        userRepository.save(user);
    }

    public boolean saveNewUser(User user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("User"));
            userRepository.save(user);
            return true;
        }
        catch (Exception e){
            log.error("Error Occured for {}",user.getUserName(),e);
            return false;
        }
    }

    public void saveAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("User","Admin"));
        userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }
    
    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public User findByUserName(String userName){
        return  userRepository.findByUserName(userName);
    }

    public User saveNewUserWithoutPassword(String userName, String email, String providerId, com.eDiya.journalApp.enums.AuthProviderType providerType){
        try{
            User user = User.builder()
                    .userName(userName)
                    .email(email)
                    .providerId(providerId)
                    .authProviderType(providerType)
                    .roles(Arrays.asList("User"))
                    .build();
            userRepository.save(user);
            return user;
        }catch (Exception e){
            log.error("Error Occured for {}",userName,e);
            return null;
        }
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}

// contorller --> service --> repository