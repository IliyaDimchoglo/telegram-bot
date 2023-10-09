package com.fulcrum.telegrambot.service;


import com.fulcrum.telegrambot.model.UserDto;

import java.util.List;

public interface CryptoCurrencyService {

    void refresh(List<UserDto> userDto);
}
