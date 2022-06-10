package ru.job4j.cinema.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TicketDbStore {
    private final BasicDataSource pool;

    public TicketDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ticket")) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tickets.add(new Ticket(rs.getInt("id"),
                            rs.getInt("sessionId"),
                            rs.getInt("posRow"),
                            rs.getInt("cell"),
                            rs.getInt("userId")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    public Optional<Ticket> add(Ticket ticket) {
        try (Connection connection = pool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "INSERT INTO ticket(session_id, pos_row, cell, user_id) VALUES (?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, ticket.getSessionId());
            stmt.setInt(2, ticket.getPosRow());
            stmt.setInt(3, ticket.getCell());
            stmt.setInt(4, ticket.getUserId());
            stmt.execute();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    ticket.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            ticket = null;
        }
        return Optional.ofNullable(ticket);
    }

    public Ticket findById(int id) {
        try (Connection connection = pool.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ticket WHERE id = ?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Ticket(rs.getInt("id"),
                            rs.getInt("sessionId"),
                            rs.getInt("posRow"),
                            rs.getInt("cell"),
                            rs.getInt("userId"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Ticket ticket) {
        try (Connection connection = pool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "UPDATE ticket SET id = ?, session_id = ?, pos_row = ?, cell = ?, user_id = ? WHERE id = ?")) {
            stmt.setInt(1, ticket.getId());
            stmt.setInt(2, ticket.getSessionId());
            stmt.setInt(3, ticket.getPosRow());
            stmt.setInt(4, ticket.getCell());
            stmt.setInt(5, ticket.getUserId());
            stmt.setInt(6, ticket.getId());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
