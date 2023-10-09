package com.fulcrum.telegrambot.scheduler;

import com.fulcrum.telegrambot.model.UserDto;
import com.fulcrum.telegrambot.service.CryptoCurrencyService;
import com.fulcrum.telegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CryptoCurrencyRefreshScheduler {

    private final UserService userService;
    private final CryptoCurrencyService cryptoCurrencyService;

    @Scheduled(cron = "${processing.crypto-currency.refresh.cron}")
    public void refreshCryptoCurrency() {
        List<UserDto> usersToRefresh = userService.getUsers();
        if (!CollectionUtils.isEmpty(usersToRefresh)) {
            cryptoCurrencyService.refresh(usersToRefresh);
        }
    }
}
