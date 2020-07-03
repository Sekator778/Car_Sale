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
 * class prepare data for DB
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

    /**
     * loads list of all offers from data base.
     *
     * @return the list of offers.
     */
    @Override
    public List<Car> loadTable() {
        List<Car> cars = connector.allCars();
        if (cars == null) {
            return new ArrayList<>();
        }
        return cars;
    }

    /**
     * checks credentials of the user.
     *
     * @param user - the user.
     * @return user's id or -1, if the user was not found.
     */
    @Override
    public int isCredential(User user) {
        int result = -1;
        User foundUser = connector.isCredential(user);
        if (foundUser != null) {
            result = foundUser.getId();
        }
        return result;
    }

    /**
     * loads the offers which tied to the user.
     *
     * @param user - the user who added offers.
     * @return - the list of offers.
     */
    @Override
    public List<Car> loadByUser(User user) {
        List<Car> cars = new ArrayList<>();
        if (user.getId() > 0) {
            cars = connector.carsByUser(user);
        }
        return cars;
    }

    /**
     * the method returns the list of cars after filters applying.
     *
     * @param day   - current day tickets only.
     * @param photo - tickets with photos only.
     * @param brand - required brand only.
     * @return the list of the cars.
     */
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

    /**
     * add user
     *
     * @param user - user for adding
     * @return -1 if user no add or user id when success
     */
    @Override
    public int addUser(User user) {
        return connector.addUser(user);
    }

    /**
     * adds a new offer.
     *
     * @param car the offer for car selling.
     * @return true if added; otherwise false.
     */
    @Override
    public boolean addCar(Car car) {
        return connector.addCar(car) != -1;
    }

    /**
     * changes status of the offer.
     *
     * @param car the offer for car selling.
     * @return true if changed; otherwise false.
     */
    @Override
    public boolean changeStatus(Car car) {
        return connector.changeStatus(car);
    }

    /**
     * the method returns list of car brands which were added to the data base.
     *
     * @return list of the brands.
     */
    @Override
    public List<String> allBrands() {
        return connector.allBrands();
    }
}
