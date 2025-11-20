package app.servlets;

import app.dao.MovieDao;
import app.model.Movie;
import com.google.gson.Gson;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/movies")
public class MovieServlet extends HttpServlet {

    private final MovieDao dao = new MovieDao();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Movie> movies = dao.findAll();
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(gson.toJson(movies));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Movie movie = gson.fromJson(req.getReader(), Movie.class);

        if (movie.getRating() < 0 || movie.getRating() > 10) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"rating must be 0-10\"}");
            return;
        }

        dao.add(movie);

        List<Movie> movies = dao.findAll();
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write(gson.toJson(movies));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Movie movie = gson.fromJson(req.getReader(), Movie.class);

        if (movie.getId() == 0) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"id is required\"}");
            return;
        }

        dao.update(movie);
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write("{\"status\":\"updated\"}");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Movie movie = gson.fromJson(req.getReader(), Movie.class);

        if (movie.getId() == 0) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"id is required\"}");
            return;
        }

        dao.delete(movie.getId());
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write("{\"status\":\"deleted\"}");
    }
}
