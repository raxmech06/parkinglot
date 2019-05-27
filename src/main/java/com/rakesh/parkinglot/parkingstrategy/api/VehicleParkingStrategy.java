package com.rakesh.parkinglot.parkingstrategy.api;

public interface VehicleParkingStrategy {
    public void add(int i);

    public int getSlot();

    public void removeSlot(int slot);
}
