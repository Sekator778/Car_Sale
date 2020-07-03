package servlets;

import models.Car;
import service.Service;
import service.ServiceIml;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 *
 */

public class UserFiltersServlet extends HttpServlet {
    private Service service = ServiceIml.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String name = (String) session.getAttribute("name");
        if (name == null) {
            name = "Log In";
        }
        boolean day = stringToBoolean(req.getParameter("day"));
        boolean photo = stringToBoolean(req.getParameter("photo"));
        String brand = req.getParameter("brand");
        List<Car> list = this.service.filter(day, photo, brand);
        List<String> brands = this.service.allBrands();
        req.setAttribute("list", list);
        req.setAttribute("name", name);
        req.setAttribute("day", day);
        req.setAttribute("photo", photo);
        req.setAttribute("setBrand", brand);
        req.setAttribute("brands", brands);
        this.getServletContext().getRequestDispatcher("/WEB-INF/table.jsp").forward(req, resp);
    }

    private boolean stringToBoolean(String bool) {
        boolean result = false;
        if (bool.equals("true")) {
            result = true;
        }
        return result;
    }
}