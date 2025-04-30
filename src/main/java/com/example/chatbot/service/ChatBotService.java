package com.example.chatbot.service;

import com.example.chatbot.model.ChatMessage;
import com.example.chatbot.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatBotService {

    private final ChatMessageRepository chatMessageRepository;

    @Autowired
    public ChatBotService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public String getResponse(String message) {
        if (message == null || message.trim().isEmpty()) {
            saveMessage("user", message);
            return saveAndRespond("bot", "Could you please say something?");
        }

        message = message.toLowerCase();
        saveMessage("user", message);

        String response;

        if (containsAny(message, "hi", "hello", "hey")) {
            response = "Hi there! 😊 How can I assist you today?";
        } else if (containsAny(message, "how are you", "how's it going")) {
            response = "I'm just a bot, but I'm doing great! Thanks for asking!";
        } else if (containsAny(message, "bye", "goodbye", "see you")) {
            response = "Goodbye! 👋 Have a nice day!";
        } else if (containsAny(message, "help", "support", "assist")) {
            response = "Sure! Let me know what you need help with.";
        } else if (containsAny(message, "what is your name", "who are you")) {
            response = "I'm your friendly chatbot 🤖, here to help!";
        } else {
            response = "I'm not sure how to respond to that. Try asking something else!";
        }

        return saveAndRespond("bot", response);
    }

    private boolean containsAny(String message, String... keywords) {
        for (String keyword : keywords) {
            if (message.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    private void saveMessage(String sender, String message) {
        if (message != null && !message.trim().isEmpty()) {
            ChatMessage chatMessage = new ChatMessage(sender, message);
            chatMessageRepository.save(chatMessage);
        }
    }

    private String saveAndRespond(String sender, String response) {
        saveMessage(sender, response);
        return response;
    }
}
