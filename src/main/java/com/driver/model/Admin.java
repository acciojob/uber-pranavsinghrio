package com.driver.model;

import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;

import javax.persistence.*;

@Entity
@Table(name = "admin")
public class Admin {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adminId;
     private String username;
     private String passwowd;

    public int getId() {
        return adminId;
    }

    public void setId(int id) {
        this.adminId = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswowd() {
        return passwowd;
    }

    public void setPasswowd(String passwowd) {
        this.passwowd = passwowd;
    }

    public Admin() {
    }

    public Admin(String username, String passwowd) {
        this.username = username;
        this.passwowd = passwowd;
    }
}