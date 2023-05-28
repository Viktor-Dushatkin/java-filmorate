package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private HashMap<Integer, User> users = new HashMap();
    private int idMaker = 1;

    @PostMapping
    public User create(@RequestBody User user) throws ValidationException {
        log.info("Получен запрос на создание нового пользователя");
        validate(user);
        user.setId(idMaker);
        idMaker++;
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User update(@RequestBody User user) throws ValidationException {
        log.info("Получен запрос на обновление пользователя {}", user.getId());
        validate(user);
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        } else {
            log.warn("Попытка обновления несуществующего пользователя");
            throw new ValidationException("Такого юзера нет");
        }
        return user;
    }

    @GetMapping
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    protected void validate(User user) throws ValidationException {
        try {
            if (user.getName().isEmpty()) {
                user.setName(user.getLogin());
            }
        } catch (NullPointerException e) {
            user.setName(user.getLogin());
        }
        if (user.getEmail().isEmpty()) {
            log.warn("Ошибка валидации: Поле почты пустое");
            throw new ValidationException("Поле почты пустое");
        } else if (!user.getEmail().contains("@")) {
            log.warn("Ошибка валидации: Неверный формат почты");
            throw new ValidationException("Неверный формат почты");
        }
        if (user.getLogin().isBlank()) {
            log.warn("Ошибка валидации: Поле логина пустое");
            throw new ValidationException("Поле логина пустое");
        } else if (user.getLogin().contains(" ")) {
            log.warn("Ошибка валидации: Пробелы в логине");
            throw new ValidationException("Логин не может содержать пробелы");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Ошибка валидации: дата рождения в будущем");
            throw new ValidationException("Вы еще не родились");
        }

    }
}
