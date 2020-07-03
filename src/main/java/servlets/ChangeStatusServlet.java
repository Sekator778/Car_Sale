package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Car;
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
 * change status car sold or not sold
 */

public class ChangeStatusServlet extends HttpServlet {
    private Service service = new ServiceIml();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        reader.lines().forEach(sb::append);
        ObjectMapper mapper = new ObjectMapper();
        String json = sb.toString();
        HashMap map = mapper.readValue(json, HashMap.class);
        int id = (Integer)map.get("id");
        boolean sold = (Boolean)map.get("status");
        Car car = new Car();
        car.setId(id);
        car.setSold(sold);
        boolean result = service.changeStatus(car);
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
