package com.eDiya.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement // will make container which consists of all the operations available in db ensures atomicity, isolation.
public class JournalApplication {

	public static void main(String[] args) {
        SpringApplication.run(JournalApplication.class, args);
	}
    @Bean
    public PlatformTransactionManager add(MongoDatabaseFactory dbFactory){
        return new MongoTransactionManager(dbFactory);
    }

}

//Mongo DB Factory --> Implementation --> SimpleMongoClientDatabaseFactory
// Platform Transaction Manager - Implemented by Mongo Transaction Manager
// Mongo Transaction Manager
