package com.se.huynhthanhbinh_18019301_kiemtra_tk;

import java.io.Serializable;

public class Tablet implements Serializable {
    private int id;
    private String name;
    private String brand;
    private String operatingSystem;
    private double screenSize;

    public Tablet() {
    }

    public Tablet(int id, String name, String brand, String operatingSystem, double screenSize) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.operatingSystem = operatingSystem;
        this.screenSize = screenSize;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public double getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(double screenSize) {
        this.screenSize = screenSize;
    }

    @Override
    public String toString() {
        return "Tablet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", operatingSystem='" + operatingSystem + '\'' +
                ", screenSize=" + screenSize +
                '}';
    }
}
