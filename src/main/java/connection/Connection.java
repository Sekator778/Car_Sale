package connection;

import models.Car;
import models.User;

import java.util.Calendar;
import java.util.List;

/**
 * work with DB
 */

public interface Connection {
    int addUser(User user);
    int addCar(Car car);
    List<Car> allCars();
    List<Car> carsByUser(User user);
    User isCredential(User user);
    boolean changeStatus(Car car);
    List<String> allBrands();
    List<Car> filterCarsByBrandPicDay(String brand, Calendar day);
    List<Car> filterCarsByPicDay(Calendar day);
    List<Car> filterCarsByBrandDay(String brand, Calendar day);
    List<Car> filterCarsByDay(Calendar day);
    List<Car> filterCarsByBrandPic(String brand);
    List<Car> filterCarsByPic();
    List<Car> filterCarsByBrand(String brand);
}
