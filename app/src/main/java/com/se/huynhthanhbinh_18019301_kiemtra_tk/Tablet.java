package com.se.huynhthanhbinh_18019301_kiemtra_tk;

public class Tablet {
    private int id;
    private String brand;
    private String operatingSystem;
    private double screenSize;

    public Tablet(int id, String brand, String operatingSystem, double screenSize) {
        this.id = id;
        this.brand = brand;
        this.operatingSystem = operatingSystem;
        this.screenSize = screenSize;
    }

    public Tablet() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                ", brand='" + brand + '\'' +
                ", operatingSystem='" + operatingSystem + '\'' +
                ", screenSize=" + screenSize +
                '}';
    }
}
