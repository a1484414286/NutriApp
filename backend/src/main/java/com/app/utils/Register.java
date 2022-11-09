package com.app.utils;

public class Register {
    private String username;
    private String password;
    private String name;
    private String height;
    private String weight;
    private String dob;

    public Register(String username, String password, String name, String height, String weight, String dob) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.dob = dob;
    }

    public String getDob() {
        return dob;
    }

    public String getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getWeight() {
        return weight;
    }
}
