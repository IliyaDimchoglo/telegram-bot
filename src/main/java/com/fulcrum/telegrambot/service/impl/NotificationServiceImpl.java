package com.fulcrum.telegrambot.service.impl;


import com.fulcrum.telegrambot.model.UserDto;
import com.fulcrum.telegrambot.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void sendNotification(UserDto user, String text) {
        // didn't have enough time to implement this functionality
    }
}
