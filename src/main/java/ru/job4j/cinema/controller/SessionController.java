package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SessionService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@ThreadSafe
public class SessionController {
    private final SessionService service;

    public SessionController(SessionService service) {
        this.service = service;
    }

    @GetMapping("/sessions")
    public String posts(Model model, HttpSession session) {
        model.addAttribute("sessions", service.findAll());
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        return "sessions";
    }

    @GetMapping("/sessions/{sessionId}")
    public String formBuyTicket(Model model, HttpSession session, @PathVariable("sessionId") int id) {
        model.addAttribute("film", service.findById(id));
        User user = (User) session.getAttribute("user");
        model.addAttribute("ticket", new Ticket(user.getId()));
        session.setAttribute("film", service.findById(id));
        List<Integer> rows = IntStream.range(1, 6).boxed().collect(Collectors.toList());
        List<Integer> cells = IntStream.range(1, 6).boxed().collect(Collectors.toList());
        model.addAttribute("rows", rows);
        model.addAttribute("cells", cells);
        model.addAttribute("user", user);
        return "seatSelection";
    }
}
