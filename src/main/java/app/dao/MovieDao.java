package app.dao;

import app.db.DataSourceProvider;
import app.model.Movie;

import java.sql.*;
import java.util.*;

public class MovieDao {

    public void add(Movie m) {
        String sql = "INSERT INTO movies (title, director, year, rating, user_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, m.getTitle());
            ps.setString(2, m.getDirector());
            ps.setInt(3, m.getYear());
            ps.setDouble(4, m.getRating());
            if (m.getAuthorId() != null) {
                ps.setInt(5, m.getAuthorId());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<Movie> findAll() {
        List<Movie> list = new ArrayList<>();
        String sql = "SELECT * FROM movies";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Movie m = new Movie();
                m.setId(rs.getInt("id"));
                m.setTitle(rs.getString("title"));
                m.setDirector(rs.getString("director"));
                m.setYear(rs.getInt("year"));
                m.setRating(rs.getDouble("rating"));
                m.setAuthorId(rs.getInt("user_id"));
                list.add(m);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public void update(Movie m) {
        String sql = "UPDATE movies SET title=?, director=?, year=?, rating=? WHERE id=?";
        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, m.getTitle());
            ps.setString(2, m.getDirector());
            ps.setInt(3, m.getYear());
            ps.setDouble(4, m.getRating());
            ps.setInt(5, m.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM movies WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
