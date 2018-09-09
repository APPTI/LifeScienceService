package com.littlefrog.entity;

import javax.persistence.*;

@Entity(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id", nullable = false, updatable = false, columnDefinition = "INT(11) UNSIGNED")
    private Integer adminID;

    @Column(nullable = false, length = 20)
    private String admin_name;

    @Column(nullable = false, length = 20)
    private String password;

    public Admin() {
    }

    public Admin(String admin_name, String password) {

        this.admin_name = admin_name;
        this.password = password;
    }

    public Integer getAdminID() {
        return adminID;
    }

    public void setAdminID(Integer adminID) {
        this.adminID = adminID;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
