package com.eDiya.journalApp.entity;

import com.eDiya.journalApp.enums.Sentiment;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

// POJO class - Plain Old Java Object
/*
It is just a simple normal Java class that:
   - contains variables (fields)
   - contains constructors
   - contains getters/setters
   - may contain methods
   - does NOT depend on special framework rules
*/
@Document(collection = "journal_entries")
@Data
public class JournalEntry {

    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;
    private Sentiment sentiment;
}
