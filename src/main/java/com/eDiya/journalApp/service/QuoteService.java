package com.eDiya.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuoteService {

    private static final String apiKey = "wV4ZnzRoP26qaM1bMS2TvNh7W1nUFnEJRohEmJPh";

    @Autowired
    private RestTemplate restTemplate;

}
