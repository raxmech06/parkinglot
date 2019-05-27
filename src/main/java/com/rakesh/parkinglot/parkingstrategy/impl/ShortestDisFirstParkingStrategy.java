package com.rakesh.parkinglot.parkingstrategy.impl;

import com.rakesh.parkinglot.parkingstrategy.api.VehicleParkingStrategy;

import java.util.TreeSet;

public class ShortestDisFirstParkingStrategy implements VehicleParkingStrategy {
    private TreeSet<Integer> availableSlots;

    public ShortestDisFirstParkingStrategy() {
        availableSlots = new TreeSet<Integer>();
    }

    @Override
    public void add(int i) {
        this.availableSlots.add(i);
    }

    @Override
    public int getSlot() {
        return availableSlots.first();
    }

    @Override
    public void removeSlot(int slot) {
        this.availableSlots.remove(slot);
    }
}
