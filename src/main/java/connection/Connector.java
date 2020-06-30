package connection;

import models.Car;
import models.User;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;
import java.util.function.Function;

/**
 *
 */

public class Connector implements Connection {
    /**
     * Entity Factory
     */
    private final EntityManagerFactory entityManager = Persistence.createEntityManagerFactory("SaleCar");
    /**
     * Instance of this class.
     */
    private static final Connector INSTANCE = new Connector();

    /**
     * the getter for singleton.
     *
     * @return the instance.
     */
    public static Connector getINSTANCE() {
        return INSTANCE;
    }

    public Connector() {
    }

    /**
     * the wrapper for methods, which return List of cars.
     *
     * @param factory   - Entity Manager factory.
     * @param operation - unique operation for each method.
     * @param <T>       - returning type.
     * @return - result of the unique operation.
     */
    private <T> T transaction(EntityManagerFactory factory, Function<EntityManager, T> operation) {
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            T result = operation.apply(em);
            tx.commit();
            return result;
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        return null;
    }

    /**
     * add new user to DB
     *
     * @param user - new user
     * @return result user id or -1 if error
     */
    @Override
    public int addUser(User user) {
        Integer result = transaction(entityManager, em -> {
            em.persist(user);
            return user.getId();
        });
        return result != null ? result : -1;
    }

    @Override
    public int addCar(Car car) {
        Integer result = transaction(entityManager, em -> {
            em.persist(car);
            return car.getId();

        });
        return result != null ? result : -1;
    }

    @Override
    public List<Car> allCars() {
        return transaction(entityManager, em -> {
            Query query = em.createNamedQuery("allCars");
            return query.getResultList();
        });
    }

    @Override
    public List<Car> carsByUser(User user) {
        return transaction(entityManager, em -> {
            Query query = em.createNamedQuery("CarsByUser");
            query.setParameter("userId", user.getId());
            return query.getResultList();
        });
    }

    /**
     * checks the credential for the sign in
     *
     * @param user - includes name and password for checking.
     * @return - User if found; otherwise null.
     */
    @Override
    public User isCredential(User user) {
        return transaction(entityManager, em -> {
            Query query = em.createNamedQuery("credential");
            query.setParameter("userName", user.getName());
            query.setParameter("userPassword", user.getPassword());
            return (User) query.getSingleResult();
        });
    }

    /**
     * changes the status of the offer for selling.
     *
     * @param car - contains id and new status of the offer.
     * @return - true if changed; otherwise false.
     */
    @Override
    public boolean changeStatus(Car car) {
        Object result = transaction(entityManager, em -> {
            Query query = em.createNamedQuery("CarById");
            query.setParameter("carId", car.getId());
            Car foundCar = (Car) query.getSingleResult();
            foundCar.setSold(car.isSold());
            return em.merge(foundCar);
        });
        return result != null;
    }
    /**
     * returns all brands of cars which were added to the data base.
     *
     * @return the list of brands.
     */
    @Override
    public List<String> allBrands() {
        return transaction(entityManager, em -> {
            Query query = em.createNamedQuery("allBrands");
            return query.getResultList();
        });
    }
    /**
     * returns the cars which were filtered by brand, picture and day parameter.
     *
     * @param brand - the certain car brand
     * @param day   - the latest day of car ticket publication
     * @return the list of cars
     */
    @Override
    public List<Car> filterCarsByBrandPicDay(String brand, Calendar day) {
        return transaction(entityManager, em -> {
            Query query = em.createNamedQuery("Car_BrandPicDay");
            query.setParameter("carBrand", brand);
            query.setParameter("day", day);
            return query.getResultList();
        });
    }
    /**
     * returns the cars which were filtered by picture and day parameter.
     *
     * @param day - the latest day of car ticket publication
     * @return the list of cars
     */
    @Override
    public List<Car> filterCarsByPicDay(Calendar day) {
        return transaction(entityManager, em -> {
            Query query = em.createNamedQuery("Car_PicDay");
            query.setParameter("day", day);
            return query.getResultList();
        });
    }
    /**
     * returns the cars which were filtered by brand and day parameter.
     *
     * @param brand - the certain car brand
     * @param day   - the latest day of car ticket publication
     * @return the list of cars
     */
    @Override
    public List<Car> filterCarsByBrandDay(String brand, Calendar day) {
        return transaction(entityManager, em -> {
            Query query = em.createNamedQuery("Car_BrandDay");
            query.setParameter("carBrand", brand);
            query.setParameter("day", day);
            return query.getResultList();
        });
    }
    /**
     * returns the cars which were filtered by day parameter.
     *
     * @param day - the latest day of car ticket publication
     * @return the list of cars
     */
    @Override
    public List<Car> filterCarsByDay(Calendar day) {
        return transaction(entityManager, em -> {
            Query query = em.createNamedQuery("Car_Day");
            query.setParameter("day", day);
            return query.getResultList();
        });
    }
    /**
     * returns the cars which were filtered by brand parameter.
     *
     * @param brand - the certain car brand
     * @return the list of cars
     */
    @Override
    public List<Car> filterCarsByBrandPic(String brand) {
        return transaction(entityManager, em -> {
            Query query = em.createNamedQuery("Car_BrandPic");
            query.setParameter("carBrand", brand);
            return query.getResultList();
        });
    }
    /**
     * returns the cars which were filtered by picture parameter.
     * returns the car tickets only with pictures
     *
     * @return the list of cars
     */
    @Override
    public List<Car> filterCarsByPic() {
        return transaction(entityManager, em -> {
            Query q = em.createNamedQuery("Car_Pic");
            return q.getResultList();
        });
    }
    /**
     * returns the cars which were filtered by brand parameter.
     *
     * @param brand - the certain car brand
     * @return the list of cars
     */
    @Override
    public List<Car> filterCarsByBrand(String brand) {
        return transaction(entityManager, em -> {
            Query q = em.createNamedQuery("Car_Brand");
            q.setParameter("carBrand", brand);
            return q.getResultList();
        });
    }
}
