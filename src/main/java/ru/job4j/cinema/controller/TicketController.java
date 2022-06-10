package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.TicketService;
import ru.job4j.cinema.model.User;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@ThreadSafe
public class TicketController {
    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    @GetMapping("/successfulBuying")
    public String success(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "successfulBuying";
    }

    @GetMapping("/buyingFailed")
    public String fail(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "buyingFailed";
    }

    @GetMapping("/seatSelection")
    public String tickets(Model model, HttpSession session) {
        model.addAttribute("tickets", service.findAll());
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        return "seatSelection";
    }

    @PostMapping("/seatSelection")
    public String buyingTicket(@ModelAttribute Ticket ticket,
                               HttpSession session,
                               @RequestParam("row") int row,
                               @RequestParam("cell") int cell,
                               Model model) {
        User user = (User) session.getAttribute("user");
        Session film = (Session) session.getAttribute("film");
        model.addAttribute("user", user);
        ticket.setUserId(user.getId());
        ticket.setId(ticket.getId());
        ticket.setSessionId(film.getId());
        ticket.setPosRow(row);
        ticket.setCell(cell);
        Optional<Ticket> buying = service.add(ticket);
        if (buying.isEmpty()) {
            return "redirect:/buyingFailed";
        }
        return "redirect:/successfulBuying";
    }
}
