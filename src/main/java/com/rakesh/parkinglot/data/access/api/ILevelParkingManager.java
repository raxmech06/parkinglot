package com.rakesh.parkinglot.data.access.api;

import com.rakesh.parkinglot.datamodel.Vehicle;

import java.util.List;

public interface ILevelParkingManager<T extends Vehicle> {
    public int parkCar(T vehicle);

    public boolean leaveCar(int slotNumber);

    public List<String> getStatus();

    public List<String> getRegistrationNumbersForColor(String colour);

    public List<Integer> getSlotNumbersFromColor(String colour);

    public int getSlotNoFromRegistrationNumber(String registrationNo);

    public int getAvailableSlotsCount();

    public void doSystemCleanup();
}
