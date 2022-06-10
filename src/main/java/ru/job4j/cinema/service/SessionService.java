package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.persistence.SessionDbStore;

import java.util.List;

@Service
@ThreadSafe
public class SessionService {
    private final SessionDbStore store;

    public SessionService(SessionDbStore store) {
        this.store = store;
    }

    public List<Session> findAll() {
        return store.findAll();
    }

    public void add(Session session) {
        store.add(session);
    }

    public Session findById(int id) {
        return store.findById(id);
    }

}
