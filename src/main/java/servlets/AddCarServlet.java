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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * servlet for add the new car for sale
 */

public class AddCarServlet extends HttpServlet {
    private Service service = ServiceIml.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/addPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        reader.lines().forEach(sb::append);
        ObjectMapper mapper = new ObjectMapper();
        String json = sb.toString();
        HashMap map = mapper.readValue(json, HashMap.class);
        String type = (String) map.get("type");
        String brand = (String) map.get("type");
        String model = (String) map.get("type");
        int usage = Integer.parseInt((String) map.get("usage"));
        int year = Integer.parseInt((String) map.get("year"));
        String desc = (String)map.get("desc");
        int price = Integer.parseInt((String) map.get("price"));
        String pic = (String)map.get("picPath");
        int id = (Integer) req.getSession().getAttribute("id");
        Car car = new Car(type, brand, model, usage, year, desc, price);
        car.setUser(new User(id));
        car.setPicture(pic);
        boolean result = service.addCar(car);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        JSONObject status = new JSONObject();
        if (result) {
            status.put("status", "valid");
        } else {
            status.put("status", "invalid");
        }
        writer.append(status.toString());
        writer.flush();
    }
}
