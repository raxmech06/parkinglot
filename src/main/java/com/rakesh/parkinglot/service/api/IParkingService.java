package com.rakesh.parkinglot.service.api;

import com.rakesh.parkinglot.datamodel.Vehicle;

import java.util.Optional;

public interface IParkingService {
    public void createParkingLot(int level, int capacity) throws ParkingException;

    public Optional<Integer> park(int level, Vehicle vehicle) throws ParkingException;

    public void unPark(int level, int slotNumber);

    public void getStatus(int level);

    public Optional<Integer> getAvailableSlotsCount(int level);

    public void getRegNumberForColor(int level, String color);

    public void getSlotNumbersFromColor(int level, String colour);

    public int getSlotNoFromRegistrationNumber(int level, String registrationNumber);

    public void doSystemCleanup();
}
