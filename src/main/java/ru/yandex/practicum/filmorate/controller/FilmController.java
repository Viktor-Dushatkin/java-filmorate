package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final HashMap<Integer,Film> films = new HashMap<>();
    private int idMaker = 1;

    @PostMapping
    public Film create(@RequestBody Film film) throws ValidationException {
        log.info("Получен запрос на создание нового фильма");
        validate(film);
        film.setId(idMaker);
        idMaker++;
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) throws ValidationException {
        log.info("Получен запрос на обновление фильма {}", film.getId());
        validate(film);
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            log.warn("Попытка обновления несуществующего фильма");
            throw new ValidationException("Такого фильма нет");
        }
        return film;
    }

    @GetMapping
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    protected void validate(Film film) throws ValidationException {
        if (film.getName().isBlank()) {
            log.warn("Ошибка валидации: Имя фильма пустое");
            throw new ValidationException("Имя фильма пустое");
        }
        if (film.getDescription().length() > 200) {
            log.warn("Ошибка валидации: превышение макс длины описания");
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Ошибка валидации: Дата релиза до создания кино");
            throw new ValidationException("До 28 декабря 1895 года фильмов не было");
        }
        if (film.getDuration() < 0) {
            log.warn("Ошибка валидации: Продолжительность фильма отрицательная");
            throw new ValidationException("Продолжительность фильма отрицательная");
        }
    }
}
