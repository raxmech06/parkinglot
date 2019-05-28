package com.rakesh.parkinglot.data.access.api;

import com.rakesh.parkinglot.datamodel.Vehicle;

import java.util.List;

public interface IParkingManager <T extends Vehicle> {
    public int parkCar(int level, T vehicle);

    public boolean leaveCar(int level, int slotNumber);

    public List<String> getStatus(int level);

    public List<String> getRegistrationNumbersForColor(int level, String colour);

    public List<Integer> getSlotNumbersFromColor(int level, String colour);

    public int getSlotNoFromRegistrationNumber(int level, String registrationNo);

    public int getAvailableSlotsCount(int level);

    public void doSystemCleanup();
}
