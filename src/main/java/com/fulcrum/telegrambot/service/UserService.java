package com.fulcrum.telegrambot.service;


import com.fulcrum.telegrambot.model.UserDto;

import java.util.List;

public interface UserService {

    void create(UserDto userDto);

    List<UserDto> getUsers();
}
