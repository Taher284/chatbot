package com.example.chatbot;

import com.example.chatbot.service.ChatBotService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
public class ChatView extends VerticalLayout {

    private final ChatBotService chatBotService;

    private Div chatArea = new Div();
    private TextField userInput = new TextField("Ask something:");

    @Autowired
    public ChatView(ChatBotService chatBotService) {
        this.chatBotService = chatBotService;

        chatArea.setWidthFull();
        chatArea.getStyle().set("background-color", "#f3f3f3");
        chatArea.getStyle().set("min-height", "300px");
        chatArea.getStyle().set("padding", "10px");
        chatArea.getStyle().set("overflow-y", "auto");
        chatArea.getStyle().set("max-height", "400px");

        userInput.setWidthFull();

        Button sendBtn = new Button("Send", event -> sendMessage());

        add(chatArea, userInput, sendBtn);
    }

    private void sendMessage() {
        String message = userInput.getValue();
        if (!message.isBlank()) {
            chatArea.add(new Paragraph("👤 You: " + message));
            String botReply = chatBotService.getResponse(message);
            chatArea.add(new Paragraph("🤖 Bot: " + botReply));
            userInput.clear();
        }
    }
}
