package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SessionService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
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
    public String sessions(Model model, HttpSession session) {
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

    @GetMapping("/sessionPhoto/{sessionId}")
    public ResponseEntity<Resource> download(@PathVariable("sessionId") int sessionId) {
        Session session = service.findById(sessionId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(session.getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(session.getPhoto()));
    }

    @PostMapping("/createSession")
    public String createSession(@ModelAttribute Session session,
                                  @RequestParam("file") MultipartFile file) throws IOException {
        session.setPhoto(file.getBytes());
        service.add(session);
        return "redirect:/sessions";
    }
}
