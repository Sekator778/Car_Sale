package servlets;

import models.Car;
import models.User;
import service.Service;
import service.ServiceIml;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
           getServletContext().getRequestDispatcher("/WEB-INF/signin.in").forward(req, resp);
       } else {
           String name = (String) req.getSession().getAttribute("name");
           List<Car> list = service.loadByUser(new User(id));
           req.setAttribute("list", list);
           req.setAttribute("name", name);
           getServletContext().getRequestDispatcher("/WEB-INF/cabinet.jsp").forward(req, resp);
       }
    }
}
