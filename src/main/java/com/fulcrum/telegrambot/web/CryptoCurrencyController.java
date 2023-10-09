package com.fulcrum.telegrambot.web;

import com.fulcrum.telegrambot.model.UserDto;
import com.fulcrum.telegrambot.service.CryptoCurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("crypto/currencies")
@RequiredArgsConstructor
public class CryptoCurrencyController {

    public final CryptoCurrencyService cryptoCurrencyService;

    @PostMapping
    public void refresh(UserDto userDto) {
        cryptoCurrencyService.refresh(Collections.singletonList(userDto));
    }
}
