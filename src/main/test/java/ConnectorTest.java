import connection.Connection;
import connection.Connector;
import models.Car;
import models.User;
import org.junit.Test;
import service.Service;
import service.ServiceIml;

import javax.persistence.*;
import java.util.*;

import static org.junit.Assert.*;

public class ConnectorTest {
    private final Connection connector = Connector.getINSTANCE();
    private final Service service = ServiceIml.getInstance();
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("SaleCar");
    @Test
    public void whenAddUserThenAdded() {
        User user = new User("User", "pass");
        int userId = connector.addUser(user);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query q = em.createQuery("select u From User u where u.id = :userId");
        q.setParameter("userId", userId);
        User foundUser = (User) q.getSingleResult();
        tx.commit();
        assertEquals(foundUser.getName(), (user.getName()));
        em.close();
    }
    @Test
    public void whenIsCredentialThenTrue() {
        User user = new User("Alex", "pass");
        int userId = connector.addUser(user);
        User foundUser = connector.isCredential(user);
        assertEquals(foundUser.getId(), userId);
    }
    @Test
    public void whenChangeStatusThenChanged() {
        User user = new User("Alex", "pass");
        int userId = connector.addUser(user);
        user.setId(userId);
        Car car = new Car("type", "brand", "model", 1, 2020, "desc", 123);
        car.setUser(user);
        int carId = connector.addCar(car);
        connector.changeStatus(car);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query q = em.createQuery("select c From Car c where c.id = :carId");
        q.setParameter("carId", carId);
        Car foundCar = (Car) q.getSingleResult();
        tx.commit();
        assertEquals(car.isSold(), foundCar.isSold());
        em.close();
    }
    @Test
    public void whenAddCarThenAdded() {
        User user = new User("Alex", "Alex");
        int userId = connector.addUser(user);
        user.setId(userId);
        Car car = new Car("type", "brand", "model", 100, 2020, "desc", 10000);
        car.setUser(user);
        int carId = connector.addCar(car);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query q = em.createQuery("select c From Car c where c.id = :carId");
        q.setParameter("carId", carId);
        Car foundCar = (Car) q.getSingleResult();
        tx.commit();
        assertEquals(car.getDescription(), foundCar.getDescription());
        em.close();
    }

    @Test
    public void whenCarsByUserThenCarsList() {
        User user = new User("Alex", "Alex");
        int userId = connector.addUser(user);
        User user2 = new User("Bob", "Bob");
        int user2Id = connector.addUser(user2);
        user.setId(userId);
        user2.setId(user2Id);
        Car car = new Car("type", "brand", "model", 100, 2020, "desc", 10000);
        car.setUser(user);
        Car car2 = new Car("type2", "brand2", "model2", 102, 2022, "desc2", 10002);
        car2.setUser(user2);
        connector.addCar(car);
        connector.addCar(car2);
        List<Car> carsByUser2 = connector.carsByUser(user2);
        List<Car> expected = List.of(car2);
        assertEquals(expected, carsByUser2);
    }

    @Test
    public void whenAllCarsThenAll() {
        User user = new User("Alex", "Alex");
        int userId = connector.addUser(user);
        User user2 = new User("Bob", "Bob");
        int user2Id = connector.addUser(user2);
        user.setId(userId);
        user2.setId(user2Id);
        Car car = new Car("type", "brand", "model", 100, 2020, "desc", 10000);
        car.setUser(user);
        Car car2 = new Car("type2", "brand2", "model2", 102, 2022, "desc2", 10002);
        car2.setUser(user2);
        connector.addCar(car);
        connector.addCar(car2);
        List<Car> allCars = connector.allCars();
        List<Car> expected = List.of(car, car2);
        assertEquals(expected, allCars);
    }

    @Test
    public void whenAllBrandsThenAll() {
        User user = new User("Alex", "Alex");
        int userId = connector.addUser(user);
        User user2 = new User("Bob", "Bob");
        int user2Id = connector.addUser(user2);
        user.setId(userId);
        user2.setId(user2Id);
        Car car = new Car("type", "brand", "model", 100, 2020, "desc", 10000);
        car.setUser(user);
        Car car2 = new Car("type2", "brand2", "model2", 102, 2022, "desc2", 10002);
        car2.setUser(user2);
        connector.addCar(car);
        connector.addCar(car2);
        List<String> allBrands = connector.allBrands();
        List<String> expected = List.of(car.getBrand(), car2.getBrand());
        assertEquals(expected, allBrands);
    }

    @Test
    public void whenDayFilterThenTodayTickets() {
        User user = new User("Alex", "Alex");
        int userId = connector.addUser(user);
        User user2 = new User("Bob", "Bob");
        int user2Id = connector.addUser(user2);
        user.setId(userId);
        user2.setId(user2Id);
        Car car = new Car("type", "brand", "model", 100, 2020, "desc", 10000);
        car.setUser(user);
        Car car2 = new Car("type2", "brand2", "model2", 102, 2022, "desc2", 10002);
        long yesterday = System.currentTimeMillis() - 1000 * 60 * 60 * 24;
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(yesterday);
        car2.setDate(cal);
        car2.setUser(user2);

        connector.addCar(car);
        connector.addCar(car2);
        List<Car> todayCars = service.filter(true, false, "none");
        List<Car> expected = List.of(car);
        assertEquals(expected, todayCars);
    }

    @Test
    public void whenPhotoFilterThenOnlyWithPhoto() {
        User user = new User("Alex", "Alex");
        int userId = connector.addUser(user);
        User user2 = new User("Bob", "Bob");
        int user2Id = connector.addUser(user2);
        user.setId(userId);
        user2.setId(user2Id);
        Car car = new Car("type", "brand", "model", 100, 2020, "desc", 10000);
        car.setUser(user);
        Car car2 = new Car("type2", "brand2", "model2", 102, 2022, "desc2", 10002);
        car2.setUser(user2);
        car2.setPicture("some picture");
        connector.addCar(car);
        connector.addCar(car2);
        List<Car> photoCars = service.filter(false, true, "none");
        List<Car> expected = List.of(car2);
        assertEquals(expected, photoCars);
    }

    @Test
    public void whenBrandFilterThenOnlyOne() {
        User user = new User("Alex", "Alex");
        int userId = connector.addUser(user);
        User user2 = new User("Bob", "Bob");
        int user2Id = connector.addUser(user2);
        user.setId(userId);
        user2.setId(user2Id);
        Car car = new Car("type", "brand", "model", 100, 2020, "desc", 10000);
        car.setUser(user);
        Car car2 = new Car("type2", "brand2", "model2", 102, 2022, "desc2", 10002);
        car2.setUser(user2);
        connector.addCar(car);
        connector.addCar(car2);
        List<Car> brandCars = service.filter(false, false, "brand");
        List<Car> expected = List.of(car);
        assertEquals(expected, brandCars);
    }

    @Test
    public void whenAllFiltersThenThird() {
        User user = new User("Alex", "Alex");
        int userId = connector.addUser(user);
        User user2 = new User("Bob", "Bob");
        int user2Id = connector.addUser(user2);
        user.setId(userId);
        user2.setId(user2Id);
        Car car = new Car("type", "brand", "model", 100, 2020, "desc", 10000);
        car.setUser(user);
        Car car2 = new Car("type2", "brand2", "model2", 102, 2022, "desc2", 10002);
        car2.setUser(user2);
        Car car3 = new Car("type3", "brand2", "model3", 103, 2023, "desc3", 10003);
        car3.setUser(user2);
        long yesterday = System.currentTimeMillis() - 1000 * 60 * 60 * 24;
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(yesterday);
        car.setDate(cal);
        car.setPicture("some picture");
        car3.setPicture("new picture");
        connector.addCar(car);
        connector.addCar(car2);
        connector.addCar(car3);
        List<Car> cars = service.filter(true, true, "brand2");
        List<Car> expected = List.of(car3);
        assertEquals(expected, cars);
    }
}