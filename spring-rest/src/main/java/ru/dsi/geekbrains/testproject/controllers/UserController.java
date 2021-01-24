package ru.dsi.geekbrains.testproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dsi.geekbrains.testproject.common.UserDto;
import ru.dsi.geekbrains.testproject.entities.User;
import ru.dsi.geekbrains.testproject.exceptions.ResourceNotFoundException;
import ru.dsi.geekbrains.testproject.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/1.0/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> list() {
        return new ResponseEntity<>(userService.getAll().stream().map(User::toDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> get(@PathVariable Long id) {
        User user = userService.getById(id);
        if(user==null){
            throw new ResourceNotFoundException("User with id: " + id + " not found");
        }
        return new ResponseEntity<>(user.toDto(), HttpStatus.OK);
        //return Optional.ofNullable(userService.getById(id)).map(User::toDto).orElse(null);
    }
}
