package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    User user;

    @BeforeEach
    void beforeEach() {
        user = User.builder()
                .id(1)
                .email("vasyapupkin@yandex.ru")
                .login("test")
                .name("Vasya")
                .birthday(LocalDate.of(1999,8,10))
                .build();
    }

    @Test
    void validateTestWithEmptyEmail() {
        user.setEmail("  ");
        assertThrows(ValidationException.class, () -> new UserController().validate(user));
    }

    @Test
    void validateTestWithIncorrectEmail() {
        user.setEmail("vasyapupkin");
        assertThrows(ValidationException.class, () -> new UserController().validate(user));
    }

    @Test
    void validateTestWithEmptyLogin() {
        user.setLogin("  ");
        assertThrows(ValidationException.class, () -> new UserController().validate(user));
    }

    @Test
    void validateTestWithIncorrectLogin() {
        user.setLogin("vasya pupkin");
        assertThrows(ValidationException.class, () -> new UserController().validate(user));
    }

    @Test
    void validateTestWithIncorrectBirthday() {
        user.setBirthday(LocalDate.now().plus(Period.ofDays(1)));
        assertThrows(ValidationException.class, () -> new UserController().validate(user));
    }




}