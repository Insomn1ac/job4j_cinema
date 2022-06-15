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

    public Optional<Ticket> findById(int id) {
        Optional<Ticket> ticket = Optional.empty();
        try (Connection connection = pool.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ticket WHERE id = ?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ticket = Optional.of(new Ticket(rs.getInt("id"),
                            rs.getInt("sessionId"),
                            rs.getInt("posRow"),
                            rs.getInt("cell"),
                            rs.getInt("userId")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticket;
    }

    public List<Ticket> findByUserId(int userId) {
        List<Ticket> userTickets = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ticket t "
                     + "JOIN sessions s on t.session_id = s.id WHERE user_id = ? ORDER BY s.timeofsession")) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    userTickets.add(new Ticket(rs.getString("name"),
                            rs.getString("timeOfSession"),
                            rs.getInt("pos_row"),
                            rs.getInt("cell")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userTickets;
    }
}
