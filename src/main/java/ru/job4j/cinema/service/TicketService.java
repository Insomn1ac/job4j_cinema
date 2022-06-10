package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.persistence.TicketDbStore;

import java.util.Collection;
import java.util.Optional;

@Service
@ThreadSafe
public class TicketService {
    private final TicketDbStore store;

    public TicketService(TicketDbStore store) {
        this.store = store;
    }

    public Collection<Ticket> findAll() {
        return store.findAll();
    }

    public Optional<Ticket> add(Ticket ticket) {
        return store.add(ticket);
    }

    public Ticket findById(int id) {
        return store.findById(id);
    }

    public void update(Ticket ticket) {
        store.update(ticket);
    }
}
