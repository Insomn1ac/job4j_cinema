package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.persistence.UserDbStore;

import java.util.List;
import java.util.Optional;

@Service
@ThreadSafe
public class UserService {
    private final UserDbStore store;

    public UserService(UserDbStore store) {
        this.store = store;
    }

    public Optional<User> add(User user) {
        return store.add(user);
    }

    public User findUserByName(String name) {
        return store.findUserByName(name);
    }

    public Optional<User> findUserByEmailAndPhone(String email, String phone) {
        return store.findUserByEmailAndPhone(email, phone);
    }

    public List<User> findAll() {
        return store.findAll();
    }
}
