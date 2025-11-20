package app.servlets;

import app.dao.UserDao;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/confirm")
public class ConfirmEmailServlet extends HttpServlet {

    private final UserDao dao = new UserDao();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, String> data = gson.fromJson(req.getReader(), Map.class);
        String token = data.get("token");

        resp.setContentType("application/json;charset=UTF-8");
        Map<String, Object> response = new HashMap<>();

        if (token == null || token.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.put("error", "token is required");
        } else if (dao.confirm(token)) {
            response.put("status", "confirmed");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.put("error", "invalid_token");
        }

        resp.getWriter().write(gson.toJson(response));
    }
}
