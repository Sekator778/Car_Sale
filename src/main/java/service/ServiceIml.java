package service;

import connection.Connection;
import connection.Connector;
import models.Car;
import models.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 */

public class ServiceIml implements Service {
    /**
     * the connector to data base class instance.
     */
   private final Connection connector = Connector.getINSTANCE();
    /**
     * the instance of the serviceImpl class.
     */
   private static final ServiceIml INSTANCE = new ServiceIml();
    /**
     * the getter of the instance.
     *
     * @return the instance of the serviceImpl class.
     */
    public static Service getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Car> loadTable() {
        List<Car> cars = connector.allCars();
        if (cars == null) {
            return new ArrayList<>();
        }
        return cars;
    }

    @Override
    public int isCredential(User user) {
        int result = -1;
        User foundUser = connector.isCredential(user);
        if (foundUser != null) {
            result = foundUser.getId();
        }
        return result;
    }

    @Override
    public List<Car> loadByUser(User user) {
        List<Car> cars = new ArrayList<>();
        if (user.getId() > 0) {
           cars = connector.carsByUser(user);
       }
        return cars;
    }

    @Override
    public List<Car> filter(boolean day, boolean photo, String brand) {
        List<Car> result;
        if (day) {
            Calendar now = new GregorianCalendar();
            Calendar calendarDay = new GregorianCalendar(
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DATE)
            );
            if (photo) {
                if (!brand.equals("none")) {
                    result = connector.filterCarsByBrandPicDay(brand, calendarDay);
                } else {
                    result = connector.filterCarsByPicDay(calendarDay);
                }
            } else {
                if (!brand.equals("none")) {
                    result = connector.filterCarsByBrandDay(brand, calendarDay);
                } else {
                    result = connector.filterCarsByDay(calendarDay);
                }
            }
        } else {
            if (photo) {
                if (!brand.equals("none")) {
                    result = connector.filterCarsByBrandPic(brand);
                } else {
                    result = connector.filterCarsByPic();
                }
            } else {
                if (!brand.equals("none")) {
                    result = connector.filterCarsByBrand(brand);
                } else {
                    result = connector.allCars();
                }
            }
        }
        return result;
    }

    @Override
    public int addUser(User user) {
        return connector.addUser(user);
    }

    @Override
    public boolean addCar(Car car) {
        return connector.addCar(car) != -1;
    }

    @Override
    public boolean changeStatus(Car car) {
        return connector.changeStatus(car);
    }

    @Override
    public List<String> allBrands() {
        return connector.allBrands();
    }
}
