package com.rakesh.parkinglot.service.impl;

import com.rakesh.parkinglot.data.access.api.IParkingManager;
import com.rakesh.parkinglot.data.access.impl.ParkingManager;
import com.rakesh.parkinglot.datamodel.Vehicle;
import com.rakesh.parkinglot.exception.ParkingException;
import com.rakesh.parkinglot.parkingstrategy.api.VehicleParkingStrategy;
import com.rakesh.parkinglot.parkingstrategy.impl.ShortestDisFirstParkingStrategy;
import com.rakesh.parkinglot.service.api.IParkingService;
import com.rakesh.parkinglot.util.ParkingConstantUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ParkingService implements IParkingService {
    private IParkingManager<Vehicle> parkingManager = null;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public void createParkingLot(int level, int capacity) throws ParkingException {
        if (parkingManager != null) {
            throw new ParkingException(ParkingException.ErrorCode.PARKING_ALREADY_EXIST.getMessage());
        }
        List<Integer> parkingLevelsList = new ArrayList<>();
        parkingLevelsList.add(level);
        List<Integer> capacityList = new ArrayList<>();
        capacityList.add(capacity);
        List<VehicleParkingStrategy> vehicleParkingStrategiesList = new ArrayList<>();
        vehicleParkingStrategiesList.add(new ShortestDisFirstParkingStrategy());
        parkingManager = ParkingManager.getInstance(parkingLevelsList, capacityList, vehicleParkingStrategiesList);
        System.out.println("Created a parking lot with " + capacity + " 6 slots");
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
    private void validateParkingLot() throws ParkingException {
        if(parkingManager == null){
            throw new ParkingException(ParkingException.ErrorCode.PARKING_NOT_EXIST_ERROR.getMessage());
        }
    }
}
