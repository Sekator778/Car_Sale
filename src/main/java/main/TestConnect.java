package main;

import connection.Connection;
import connection.Connector;
import models.User;
import org.junit.Test;
import service.Service;
import service.ServiceIml;

import javax.persistence.*;

import static org.junit.Assert.assertEquals;

/**
 *
 */

public class TestConnect {


    public static void main(String[] args) {
        final Connection connector = Connector.getINSTANCE();
        final Service service = ServiceIml.getInstance();
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("sales");
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
}
