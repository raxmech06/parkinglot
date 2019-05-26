package com.rakesh.parkinglot.datamodel;

public abstract class Vehicle {
    private String registrationNumber;
    private String color;
    public Vehicle(String registrationNumber, String color) {
        this.registrationNumber = registrationNumber;
        this.color = color;
    }
}
