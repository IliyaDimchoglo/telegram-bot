package com.fulcrum.telegrambot.web;

import com.fulcrum.telegrambot.model.UserDto;
import com.fulcrum.telegrambot.service.CryptoCurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("crypto/currencies")
@RequiredArgsConstructor
public class CryptoCurrencyController {

    public final CryptoCurrencyService cryptoCurrencyService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void refresh(@Valid @RequestBody UserDto userDto) {
        cryptoCurrencyService.refresh(Collections.singletonList(userDto));
    }
}
