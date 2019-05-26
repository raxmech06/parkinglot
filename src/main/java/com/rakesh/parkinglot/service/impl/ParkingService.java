package com.rakesh.parkinglot.service.impl;

import com.rakesh.parkinglot.datamodel.Vehicle;
import com.rakesh.parkinglot.service.api.IParkingService;

import java.util.Optional;

public class ParkingService implements IParkingService {
    @Override
    public void createParkingLot(int level, int capacity) {

    }

    @Override
    public Optional<Integer> park(int level, Vehicle vehicle) {
        return Optional.empty();
    }

    @Override
    public void unPark(int level, int slotNumber) {

    }

    @Override
    public void getStatus(int level) {

    }

    @Override
    public Optional<Integer> getAvailableSlotsCount(int level) {
        return Optional.empty();
    }

    @Override
    public void getRegNumberForColor(int level, String color) {

    }

    @Override
    public void getSlotNumbersFromColor(int level, String colour) {

    }

    @Override
    public int getSlotNoFromRegistrationNumber(int level, String registrationNumber) {
        return 0;
    }

    @Override
    public void doSystemCleanup() {

    }
}
