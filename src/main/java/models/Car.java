package models;

import javax.persistence.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * class car
 */
@Entity
@Table(name = "car_tickets")
@NamedQueries({
        @NamedQuery(name = "allCars", query = "from Car"),
        @NamedQuery(name = "Car_Brand", query = "from Car c where c.brand =:carBrand"),
        @NamedQuery(name = "Car_Pic", query = "from Car c where c.picture != ''"),
        @NamedQuery(name = "Car_Day", query = "from Car c where c.date >:day"),
        @NamedQuery(name = "Car_BrandPic", query = "from Car c where c.brand =:carBrand and c.picture != ''"),
        @NamedQuery(name = "Car_BrandDay", query = "from Car c where c.brand =:carBrand and c.date >:day"),
        @NamedQuery(name = "Car_PicDay", query = "from Car c where c.picture != '' and c.date >:day"),
        @NamedQuery(name = "Car_BrandPicDay", query = "from Car c where c.brand =:carBrand and c.picture != '' and c.date >:day"),
        @NamedQuery(name = "allBrands", query = "select distinct c.brand From Car c"),
        @NamedQuery(name = "CarsByUser", query = "From Car c where c.user.id = :userId"),
        @NamedQuery(name = "CarById", query = "From Car c where c.id = :carId")
})
public class Car {
    /**
     * unique id.
     */
    @Id
    @GeneratedValue
    private int id;
    /**
     * car type.
     */
    @Column(nullable = false)
    private String type;
    /**
     * car brand.
     */
    @Column(nullable = false)
    private String brand;
    /**
     * car model.
     */
    @Column(nullable = false)
    private String model;
    /**
     * the usage of the vehicle.
     */
    @Column(nullable = false)
    private int usage;
    /**
     * year of the manufacture.
     */
    @Column(nullable = false)
    private int year;
    /**
     * additional information.
     */
    @Column(nullable = false)
    private String description;
    /**
     * the price in RUB.
     */
    @Column(nullable = false)
    private long price;
    /**
     * the path to the picture file.
     */
    private String picture;
    /**
     * the creation date.
     */
    @Column(nullable = false)
    private Calendar date;
    /**
     * the status of the offer; true if sold;
     */
    @Column(nullable = false)
    private boolean sold;
    /**
     * the user who added this offer.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "id"))
    private User user;

    public Car() {
    }

    public Car(String type, String brand, String model, int usage, int year, String description, long price) {
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.usage = usage;
        this.year = year;
        this.description = description;
        this.price = price;
        this.picture = "";
        this.date = new GregorianCalendar();
        this.sold = false;
        this.user = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getUsage() {
        return usage;
    }

    public void setUsage(int usage) {
        this.usage = usage;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return usage == car.usage
                && year == car.year
                && price == car.price
                && sold == car.sold
                && Objects.equals(type, car.type)
                && Objects.equals(brand, car.brand)
                && Objects.equals(model, car.model)
                && Objects.equals(description, car.description)
                && Objects.equals(picture, car.picture)
                && Objects.equals(date, car.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, brand, model, usage, year, description, price, picture, date, sold);
    }
}