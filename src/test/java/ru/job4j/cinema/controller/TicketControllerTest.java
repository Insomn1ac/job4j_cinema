package ru.job4j.cinema.controller;

import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TicketControllerTest {

    @Test
    public void whenReturnAllTickets() {
        List<Ticket> tickets = Arrays.asList(
                new Ticket(1, 1, 1, 5),
                new Ticket(1, 2, 1, 4)
        );
        Model model = mock(Model.class);
        TicketService ticketService = mock(TicketService.class);
        HttpSession session = mock(HttpSession.class);
        when(ticketService.findAll()).thenReturn(tickets);
        TicketController ticketController = new TicketController(ticketService);
        String page = ticketController.tickets(model, session);
        verify(model).addAttribute("tickets", tickets);
        assertEquals(page, "seatSelection");
    }
}