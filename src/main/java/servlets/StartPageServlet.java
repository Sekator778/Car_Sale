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
 * the servlet for loading the main table for the main page.
 */
public class StartPageServlet extends HttpServlet {

    private Service service = ServiceIml.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("Hi all ++++++++++++++++++++++++=");
//        HttpSession session = req.getSession();
//        String name = (String) session.getAttribute("name");
//        if (name == null) {
//            name = "Log In";
//        }
//        List<Car> list = this.service.loadTable();
//        List<String> brands = this.service.allBrands();
//        req.setAttribute("list", list);
//        req.setAttribute("name", name);
//        req.setAttribute("day", false);
//        req.setAttribute("photo", false);
//        req.setAttribute("setBrand", "none");
//        req.setAttribute("brands", brands);
//        getServletContext().getRequestDispatcher("/table.jsp").forward(req, resp);
        req.getRequestDispatcher("/WEB-INF/table.jsp").forward(req, resp);
    }
}

