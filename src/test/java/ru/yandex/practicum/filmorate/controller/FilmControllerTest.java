package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FilmControllerTest {

    Film film;

    @BeforeEach
    void beforeEach() {
        film = Film.builder()
                .name("Test")
                .id(1)
                .description("Description")
                .releaseDate(LocalDate.now())
                .duration(3)
                .build();
    }

    @Test
    void validateTest() {
        assertDoesNotThrow(() -> new FilmController().validate(film));
    }

    @Test
    void validateTestWithEmptyName() {
        film.setName("  ");
        assertValidationException(film);
    }

    @Test
    void validateTestWithBigDescription() {
        String s = "1";
        for (int i = 1; i <= 200; i++) {
            s += "1";
        }
        film.setDescription(s);
        assertValidationException(film);
    }

    @Test
    void validateTestWithToOldDate() {
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        assertValidationException(film);
    }

    @Test
    void validateTestWithNegativeDuration() {
        film.setDuration(-1);
        assertValidationException(film);
    }

    private void assertValidationException(Film film) {
        assertThrows(ValidationException.class, () -> new FilmController().validate(film));
    }
}