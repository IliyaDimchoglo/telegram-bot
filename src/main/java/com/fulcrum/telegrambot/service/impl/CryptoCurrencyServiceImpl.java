package com.fulcrum.telegrambot.service.impl;

import com.fulcrum.telegrambot.excpetion.RestException;
import com.fulcrum.telegrambot.model.CryptoPrice;
import com.fulcrum.telegrambot.model.UserDto;
import com.fulcrum.telegrambot.model.document.CryptoCurrencyDocument;
import com.fulcrum.telegrambot.repository.CryptoCurrencyRepository;
import com.fulcrum.telegrambot.service.CryptoCurrencyService;
import com.fulcrum.telegrambot.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CryptoCurrencyServiceImpl extends HttpApiClient implements CryptoCurrencyService {

    private final CryptoCurrencyRepository cryptoCurrencyRepository;
    private final NotificationService notificationService;

    @Value("${processing.crypto.prices.context-path}")
    private String contextPath;

    public CryptoCurrencyServiceImpl(RestTemplate restTemplate, CryptoCurrencyRepository cryptoCurrencyRepository, NotificationService notificationService) {
        super(restTemplate);
        this.cryptoCurrencyRepository = cryptoCurrencyRepository;
        this.notificationService = notificationService;
    }

    @Override
    @Transactional
    public void refresh(List<UserDto> users) {
        try {
            final Instant now = Instant.now();
            List<CryptoPrice> refreshedPrices = this.getCryptoPrices();
            users.forEach(user -> cryptoCurrencyRepository.findFirstByOrderByCreatedTimeDesc()
                    .ifPresent(topDoc -> refreshedPrices.forEach(refreshedPrice -> {
                        Double existedPrice = topDoc.getSymbolPrices().get(refreshedPrice.getSymbol());
                        boolean isMoreThanPercent = isMoreThanPercent(refreshedPrice.getPrice(), existedPrice, user.getPercent());
                        if(isMoreThanPercent) {
                            notificationService.sendNotification(user, String.format("Difference between prices is more than your percent %s", user.getPercent()));
                        }
                    })));

            Map<String, Double> symbolPriceMap = refreshedPrices.stream().collect(Collectors.toMap(CryptoPrice::getSymbol, CryptoPrice::getPrice));
            cryptoCurrencyRepository.save(new CryptoCurrencyDocument(UUID.randomUUID().toString(), symbolPriceMap , now.getEpochSecond()));
            log.debug("Crypto currency refreshed. Time: {}", now);
        } catch (Exception e) {
            log.warn("Crypto currency refresh failed. Ex message {}", e.getMessage());
            throw new RestException("Crypto currency refresh failed. Try to do it later");
        }
    }

    public boolean isMoreThanPercent(Double refreshedPrice, Double existedPrice, Double userPercent) {
        double difference = Math.abs(refreshedPrice - existedPrice);
        double threshold = userPercent * Math.max(refreshedPrice, existedPrice);

        return difference > threshold;
    }

    private List<CryptoPrice> getCryptoPrices() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(contextPath);

        final ParameterizedTypeReference<List<CryptoPrice>> typeRef = new ParameterizedTypeReference<List<CryptoPrice>>() {
        };
        return executeGetRequest(uriComponentsBuilder.build(), typeRef, resp -> resp).orElse(new ArrayList<>());
    }
}
