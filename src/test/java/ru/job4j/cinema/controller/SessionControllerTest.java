package ru.job4j.cinema.controller;

import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.service.SessionService;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SessionControllerTest {

    @Test
    public void whenReturnAllSessions() {
        List<Session> sessions = Arrays.asList(
                new Session(1, "New film"),
                new Session(2, "New film")
        );
        Model model = mock(Model.class);
        SessionService sessionService = mock(SessionService.class);
        HttpSession session = mock(HttpSession.class);
        when(sessionService.findAll()).thenReturn(sessions);
        SessionController sessionController = new SessionController(sessionService);
        String page = sessionController.sessions(model, session);
        verify(model).addAttribute("sessions", sessions);
        assertEquals(page, "sessions");
    }
}