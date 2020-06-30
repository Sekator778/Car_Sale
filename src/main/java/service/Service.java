package service;

import models.Car;
import models.User;

import java.util.List;

public interface Service {
    /**
     * отображение всех авто
     *
     * @return лист все авто
     */
    List<Car> loadTable();

    /**
     * есть ли юзер и возвращаем его айди или -1 если нет
     *
     * @return id user or -1 if not found
     */
    int isCredential(User user);
    /**
     * load all car current user
     */
    List<Car> loadByUser(User user);
    /**
     * load car with filter
     */
    List<Car> filter(boolean day, boolean photo, String brand);
    /**
     * add user
     */
    int addUser(User user);
    boolean addCar(Car car);
    boolean changeStatus(Car car);
    List<String> allBrands();



}