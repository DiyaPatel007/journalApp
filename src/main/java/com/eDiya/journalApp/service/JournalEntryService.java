package com.eDiya.journalApp.service;

import com.eDiya.journalApp.entity.JournalEntry;
import com.eDiya.journalApp.entity.User;
import com.eDiya.journalApp.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try{
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJounalEntries().add(saved);
            userService.saveEntry(user);
        }catch(Exception e){
            log.error("Error",e);
            throw new RuntimeException("An error occurred while saving the entry",e);
        }

    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }
    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }
    
    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName) {
        boolean removed = false;
        try{
            User user = userService.findByUserName(userName);
            removed = user.getJounalEntries().removeIf(x -> x.getId().equals(id));

            if (removed) {
                userService.saveEntry(user);
                journalEntryRepository.deleteById(id);
            }
        }catch(Exception e){

         throw new RuntimeException("An error occurred while deleting this entry.",e);
        }
        return removed;
    }

    public List<JournalEntry> findByUserName(String userName){
        User user = userService.findByUserName(userName);
        return user.getJounalEntries();
    }
}

// controller --> service --> repository