package app.servlets;

import app.dao.UserDao;
import app.model.User;
import app.utils.EmailSender;
import app.utils.ValidationUtils;
import com.google.gson.Gson;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

    private final UserDao dao = new UserDao();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, String> data = gson.fromJson(req.getReader(), Map.class);

        String email = data.get("email");
        String password = data.get("password");

        resp.setContentType("application/json;charset=UTF-8");

        if (!ValidationUtils.isValidEmail(email)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"invalid_email\"}");
            return;
        }

        String token = UUID.randomUUID().toString();
        User user = new User();
        user.setEmail(email);
        user.setPassword(password); // По-хорошему — хешировать
        user.setConfirmToken(token);
        user.setConfirmed(false);

        dao.create(user);

        String link = "http://localhost:8080/movie-app/confirm";
        EmailSender.send(email, "Confirm your account", "Click here: " + link + "?token=" + token);

        resp.getWriter().write("{\"status\":\"email_sent\"}");
    }
}
