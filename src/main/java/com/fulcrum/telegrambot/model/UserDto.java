package com.fulcrum.telegrambot.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserDto {

    @NotNull(message = "The field 'nick_name' is required.")
    private String nickName;

    @NotNull(message = "The field 'percent' is required.")
    private Double percent;
}
