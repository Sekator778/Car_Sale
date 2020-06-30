package models;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

/**
 *
 */
@Entity
@Table(name = "car_owners")
@NamedQuery(name = "credential", query = "From User u where u.name like :userName and u.password like :userPassword")
public class User {
    /**
     * unique id.
     */
    @Id
    @GeneratedValue
    private int id;
    /**
     * user's name.
     */
    @Column(unique = true, nullable = false)
    private String name;
    /**
     * user's password.
     */
    @Column(nullable = false)
    private String password;
    /**
     * the set of all offers.
     */
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Car> cars;

    public User() {
    }

    public User(int id) {
        this.id = id;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(name, user.name)
                && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password);
    }
}
