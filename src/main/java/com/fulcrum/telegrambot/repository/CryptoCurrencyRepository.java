package com.fulcrum.telegrambot.repository;


import com.fulcrum.telegrambot.model.document.CryptoCurrencyDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CryptoCurrencyRepository extends MongoRepository<CryptoCurrencyDocument, String> {

    Optional<CryptoCurrencyDocument> findFirstByOrderByCreatedTimeDesc();
}
