package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Car;
import models.User;
import org.json.JSONObject;
import service.Service;
import service.ServiceIml;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

/**
 * servlet for user page and respresent personal info
 */

public class UserPageServlet extends HttpServlet {
    private Service service = ServiceIml.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = (Integer) req.getSession().getAttribute("id");
        if (id == null) {
            getServletContext().getRequestDispatcher("/WEB-INF/signin.jsp").forward(req, resp);
        } else {
            String name = (String) req.getSession().getAttribute("name");
            List<Car> list = service.loadByUser(new User(id));
            req.setAttribute("list", list);
            req.setAttribute("name", name);
            getServletContext().getRequestDispatcher("/WEB-INF/cabinet.jsp").forward(req, resp);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        reader.lines().forEach(sb::append);
        String json = sb.toString();
        ObjectMapper mapper = new ObjectMapper();
        HashMap map = mapper.readValue(json, HashMap.class);
        String name = (String) map.get("name");
        String password = (String) map.get("pass");
        int id = service.addUser(new User(name, password));
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        JSONObject status = new JSONObject();
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        if (id != -1) {
            HttpSession session = req.getSession();
            synchronized (session) {
                session.setAttribute("name", name);
                session.setAttribute("id", id);
            }
            status.put("status", "valid");
        } else {
            status.put("status", "invalid");
        }
        writer.append(status.toString());
        writer.flush();
    }
}
