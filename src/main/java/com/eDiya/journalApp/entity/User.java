package com.eDiya.journalApp.entity;

import com.eDiya.journalApp.enums.AuthProviderType;
import com.fasterxml.jackson.databind.annotation.EnumNaming;
import lombok.*;
import org.apache.kafka.shaded.com.google.protobuf.DescriptorProtos;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull
    private String userName;

    private String password;
    private String email;
    private boolean sentimentAnalysis;

    private String providerId;
    private AuthProviderType authProviderType;

    @DBRef // Reference of JournalEntry in User class - Foreign Key only Id is referenced not the whole document
    private List<JournalEntry> jounalEntries = new ArrayList<>();
    private List<String> roles;
}
