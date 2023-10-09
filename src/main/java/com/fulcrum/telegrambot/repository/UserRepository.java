package com.fulcrum.telegrambot.repository;


import com.fulcrum.telegrambot.model.document.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserDocument, String> {

}
