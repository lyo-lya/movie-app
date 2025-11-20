package app.dao;

import app.db.DataSourceProvider;
import app.model.User;

import java.sql.*;

public class UserDao {

    public void create(User u) {
        String sql = "INSERT INTO users (email, password, confirm_token) VALUES (?, ?, ?)";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getEmail());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getConfirmToken());
            ps.executeUpdate();

        } catch (Exception e) { e.printStackTrace(); }
    }

    public boolean confirm(String token) {
        String sql = "UPDATE users SET confirmed=true WHERE confirm_token=?";
        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, token);
            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email=?";
        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setConfirmed(rs.getBoolean("confirmed"));
                u.setConfirmToken(rs.getString("confirm_token"));
                return u;
            }

        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
}
