package ru.job4j.cinema.controller;

import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UserControllerTest {

    @Test
    public void whenAddNewUser() {
        User user = new User(1, "New user");
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        userController.createUser(model, user, session);
        verify(userService).add(user);
    }

    @Test
    public void whenFailedAddingUser() {
        User user = new User(1, "New user");
        Model model = mock(Model.class);
        UserService userService = mock(UserService.class);
        HttpSession session = mock(HttpSession.class);
        UserController userController = new UserController(userService);
        String page = userController.createUser(model, user, session);
        verify(userService).add(user);
        assertEquals("redirect:/fail", page);
    }
}