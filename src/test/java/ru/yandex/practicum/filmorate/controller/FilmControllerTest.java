package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    Film film;

    @BeforeEach
    void beforeEach() {
        film = Film.builder()
                .name("Test")
                .id(1)
                .description("Description")
                .releaseDate(LocalDate.now())
                .duration(Duration.ofHours(3))
                .build();
    }
    @Test
    void validateTestWithEmptyName() {
        film.setName("  ");
        assertThrows(ValidationException.class, () -> new FilmController().validate(film));
    }

    @Test
    void validateTestWithBigDescription() {
        String s = "1";
        for (int i = 1; i <= 200; i++) {
            s+="1";
        }
        film.setDescription(s);
        assertThrows(ValidationException.class, () -> new FilmController().validate(film));
    }

    @Test
    void validateTestWithToOldDate() {
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        assertThrows(ValidationException.class, () -> new FilmController().validate(film));
    }

        @Test
        void validateTestWithNegativeDuration() {
            film.setDuration(Duration.ofHours(-1));
            assertThrows(ValidationException.class, () -> new FilmController().validate(film));
        }
}