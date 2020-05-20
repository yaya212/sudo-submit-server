package com.example.websocketdemo.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.websocketdemo.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ChatController {

    static String[] arr = new String[2];
    @Autowired
    JdbcTemplate jdbcTemplate;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        arr[1] = "Message:"+ chatMessage.getContent();
        List<Object[]> Messages = Arrays.asList(arr[1]).stream()
                .map(name -> name.split(":"))
                .collect(Collectors.toList());
        jdbcTemplate.batchUpdate("INSERT INTO user(garbage, message) VALUES (?,?)", Messages);
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {

        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());

        //Add username in database
        arr[0] = chatMessage.getSender() + " Joined";
        List<Object[]> splitUpNames = Arrays.asList(arr[0]).stream()
                .map(name -> name.split(" "))
                .collect(Collectors.toList());
        jdbcTemplate.batchUpdate("INSERT INTO user(username, status) VALUES (?,?)", splitUpNames);


        return chatMessage;
    }

}
