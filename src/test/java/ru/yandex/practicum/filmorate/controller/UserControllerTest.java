package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

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
    void validateTest() {
        assertDoesNotThrow(() -> new UserController().validate(user));
    }

    @Test
    void validateTestWithEmptyEmail() {
        user.setEmail("  ");
        assertValidationException(user);
    }

    @Test
    void validateTestWithIncorrectEmail() {
        user.setEmail("vasyapupkin");
        assertValidationException(user);
    }

    @Test
    void validateTestWithEmptyLogin() {
        user.setLogin("  ");
        assertValidationException(user);
    }

    @Test
    void validateTestWithIncorrectLogin() {
        user.setLogin("vasya pupkin");
        assertValidationException(user);
    }

    @Test
    void validateTestWithIncorrectBirthday() {
        user.setBirthday(LocalDate.now().plus(Period.ofDays(1)));
        assertValidationException(user);
    }

    private void assertValidationException(User user) {
        assertThrows(ValidationException.class, () -> new UserController().validate(user));
    }
}