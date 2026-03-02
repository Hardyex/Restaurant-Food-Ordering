package com.example.OrderFoodSystem.dto;

import java.util.List;

public class IntentConfigDTO {

    public static class Root {
        private ChatbotData chatbot_training_data;

        public ChatbotData getChatbot_training_data() {
            return chatbot_training_data;
        }

        public void setChatbot_training_data(ChatbotData chatbot_training_data) {
            this.chatbot_training_data = chatbot_training_data;
        }
    }

    public static class ChatbotData {
        private List<Intent> intents;

        public List<Intent> getIntents() {
            return intents;
        }

        public void setIntents(List<Intent> intents) {
            this.intents = intents;
        }
    }

    public static class Intent {
        private String intent;
        private List<String> user_utterances;
        private List<String> responses;

        public String getIntent() {
            return intent;
        }

        public void setIntent(String intent) {
            this.intent = intent;
        }

        public List<String> getUser_utterances() {
            return user_utterances;
        }

        public void setUser_utterances(List<String> user_utterances) {
            this.user_utterances = user_utterances;
        }

        public List<String> getResponses() {
            return responses;
        }

        public void setResponses(List<String> responses) {
            this.responses = responses;
        }
    }

    public static class IntentResult {
        private String reply;
        private String intent;

        public IntentResult(String reply, String intent) {
            this.reply = reply;
            this.intent = intent;
        }

        public String getReply() {
            return reply;
        }

        public String getIntent() {
            return intent;
        }
    }
}
