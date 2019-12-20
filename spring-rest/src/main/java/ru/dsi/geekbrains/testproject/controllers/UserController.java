package ru.dsi.geekbrains.testproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dsi.geekbrains.testproject.common.UserDto;
import ru.dsi.geekbrains.testproject.entities.User;
import ru.dsi.geekbrains.testproject.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/1.0/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDto> list() {
        return userService.getAll().stream().map(User::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDto get(@PathVariable Long id) {
        return Optional.ofNullable(userService.getById(id)).map(User::toDto).orElse(null);
    }
}
