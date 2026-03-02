package com.example.OrderFoodSystem.service;

import com.example.OrderFoodSystem.dto.IntentConfigDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class IntentEngine {

    @Autowired
    private ResourceLoader resourceLoader;

    private List<IntentConfigDTO.Intent> intents = new ArrayList<>();
    private final Random random = new Random();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        try {
            Resource resource = resourceLoader.getResource("classpath:chatbot_training_data.json");
            IntentConfigDTO.Root root = objectMapper.readValue(resource.getInputStream(), IntentConfigDTO.Root.class);
            if (root != null && root.getChatbot_training_data() != null) {
                this.intents = root.getChatbot_training_data().getIntents();
            }
        } catch (IOException e) {
            System.err.println("Failed to load chatbot training data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Optional<IntentConfigDTO.IntentResult> detectIntent(String message) {
        if (message == null || message.trim().isEmpty()) {
            return Optional.empty();
        }

        String normalizedMessage = message.toLowerCase().trim();

        for (IntentConfigDTO.Intent intent : intents) {
            // Priority 1: Exact whole-word matches
            boolean exactMatch = intent.getUser_utterances().stream()
                    .anyMatch(u -> normalizedMessage.equalsIgnoreCase(u.trim()));

            if (exactMatch) {
                return generateResult(intent);
            }

            // Priority 2: Message contains user utterance or vice versa
            boolean partialMatch = intent.getUser_utterances().stream()
                    .anyMatch(u -> normalizedMessage.contains(u.toLowerCase())
                            || u.toLowerCase().contains(normalizedMessage));

            if (partialMatch) {
                return generateResult(intent);
            }
        }

        return Optional.empty();
    }

    private Optional<IntentConfigDTO.IntentResult> generateResult(IntentConfigDTO.Intent intent) {
        if (intent.getResponses() != null && !intent.getResponses().isEmpty()) {
            String randomResponse = intent.getResponses().get(random.nextInt(intent.getResponses().size()));
            return Optional.of(new IntentConfigDTO.IntentResult(randomResponse, intent.getIntent()));
        }
        return Optional.empty();
    }
}
