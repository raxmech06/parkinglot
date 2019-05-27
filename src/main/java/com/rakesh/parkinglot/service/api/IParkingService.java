package com.rakesh.parkinglot.service.api;

import com.rakesh.parkinglot.datamodel.Vehicle;
import com.rakesh.parkinglot.exception.ParkingException;

import java.util.Optional;

public interface IParkingService {
    public void createParkingLot(int level, int capacity) throws ParkingException;

    public Optional<Integer> park(int level, Vehicle vehicle) throws ParkingException;

    public void unPark(int level, int slotNumber) throws ParkingException;

    public void getStatus(int level) throws ParkingException;

    public Optional<Integer> getAvailableSlotsCount(int level) throws ParkingException;

    public void getRegNumberForColor(int level, String colour) throws ParkingException;

    public void getSlotNumbersFromColor(int level, String colour) throws ParkingException;

    public int getSlotNoFromRegistrationNumber(int level, String registrationNumber) throws ParkingException;

    public void doSystemCleanup();
}
