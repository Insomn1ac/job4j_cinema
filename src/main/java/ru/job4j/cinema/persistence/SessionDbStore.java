package ru.job4j.cinema.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SessionDbStore {
    private final BasicDataSource pool;

    public SessionDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Session> findAll() {
        List<Session> sessions = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM sessions ORDER BY timeofsession")) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    sessions.add(new Session(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getString("timeOfSession"),
                            rs.getBytes("photo")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessions;
    }

    public Session add(Session session) {
        try (Connection connection = pool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "INSERT INTO sessions(name, description, timeOfSession, photo) VALUES (?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, session.getName());
            stmt.setString(2, session.getDescription());
            stmt.setString(3, session.getTimeOfSession());
            stmt.setBytes(4, session.getPhoto());
            stmt.execute();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    session.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return session;
    }

    public Session findById(int id) {
        try (Connection connection = pool.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM sessions WHERE id = ?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Session(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getString("timeOfSession"),
                            rs.getBytes("photo")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
