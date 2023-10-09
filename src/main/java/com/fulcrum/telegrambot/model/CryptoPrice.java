package com.fulcrum.telegrambot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CryptoPrice {

    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("price")
    private Double price;
}
