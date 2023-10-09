package com.fulcrum.telegrambot.service.impl;

import com.fulcrum.telegrambot.excpetion.RestException;
import com.fulcrum.telegrambot.model.UserDto;
import com.fulcrum.telegrambot.model.document.UserDocument;
import com.fulcrum.telegrambot.repository.UserRepository;
import com.fulcrum.telegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    @Value("${system.max.available.users}")
    private int maxAvailableUsers;

    @Override
    @Transactional
    public void create(UserDto userDto) {
        try {
            if (userRepository.count() <= maxAvailableUsers) {
                userRepository.save(new UserDocument(UUID.randomUUID().toString(), userDto.getNickName(), userDto.getPercent()));
                log.debug("User with nick name {}, successfully created", userDto.getNickName());
            } else {
                throw new RestException("System not support additional users.");
            }
        } catch (DuplicateKeyException e) {
            log.warn("User creation failed, nickname {} already exists", userDto.getNickName());
            throw new DuplicateKeyException(String.format("User with nick name %s, already created", userDto.getNickName()));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getUsers() {
        try {
            return userRepository.findAll().stream()
                    .map(user -> mapper.map(user, UserDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.warn("Failed to get users. Ex message {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}
