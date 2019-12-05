package ru.dsi.geekbrains.testproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dsi.geekbrains.testproject.entities.User;
import ru.dsi.geekbrains.testproject.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User getById(long id) {
        return this.userRepository.findById(id).get();
    }

    public List<User> getAll() {
        return this.userRepository.findAll();
    }
}
