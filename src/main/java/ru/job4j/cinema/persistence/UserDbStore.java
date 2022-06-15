package ru.job4j.cinema.persistence;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@ThreadSafe
public class UserDbStore {
    private final BasicDataSource pool;

    public UserDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<User> add(User user) {
        try (Connection connection = pool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "INSERT INTO users(username, email, phone) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPhone());
            stmt.execute();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            user = null;
        }
        return Optional.ofNullable(user);
    }

    public Optional<User> findUserByName(String name) {
        Optional<User> user = Optional.empty();
        try (Connection connection = pool.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT id, username, email, phone FROM users WHERE username = ?")) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = Optional.of(new User(rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("phone")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public Optional<User> findUserByEmailAndPhone(String email, String phone) {
        Optional<User> rsl = Optional.empty();
        try (Connection connection = pool.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT id, username FROM users WHERE email = ? AND phone = ?")) {
            stmt.setString(1, email);
            stmt.setString(2, phone);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    rsl = Optional.of(new User(
                            rs.getInt("id"),
                            rs.getString("username"))
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rsl;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT id, username, email, phone FROM users")) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(new User(rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("phone"))
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
