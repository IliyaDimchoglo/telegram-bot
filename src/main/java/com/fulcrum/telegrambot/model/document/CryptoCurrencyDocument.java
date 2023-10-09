package com.fulcrum.telegrambot.model.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("crypto_currencies")
public class CryptoCurrencyDocument {

    @Id
    private String id;

    private Map<String, Double> symbolPrices;

    @Indexed(name = "ttl_index", expireAfterSeconds = 86400)
    private Long createdTime;
}
