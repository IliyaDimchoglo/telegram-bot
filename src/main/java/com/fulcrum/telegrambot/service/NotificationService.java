package com.fulcrum.telegrambot.service;


import com.fulcrum.telegrambot.model.UserDto;

public interface NotificationService {

    void sendNotification(UserDto user, String text);
}
