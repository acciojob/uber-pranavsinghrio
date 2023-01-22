package com.driver.model;

import javax.persistence.*;

@Entity
@Table(name = "cab")
public class Cab {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    private int perkmrate;
    private boolean available;

    @OneToOne
    @JoinColumn
    private Driver driver;

    public Cab(int i) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPerkmrate() {
        return perkmrate;
    }

    public void setPerkmrate(int perkmrate) {
        this.perkmrate = perkmrate;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Cab(int perkmrate, boolean available) {
        this.perkmrate = perkmrate;
        this.available = available;
    }
}