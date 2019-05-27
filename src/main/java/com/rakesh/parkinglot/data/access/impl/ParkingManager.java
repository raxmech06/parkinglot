package com.rakesh.parkinglot.data.access.impl;

import com.rakesh.parkinglot.data.access.api.ILevelParkingManager;
import com.rakesh.parkinglot.data.access.api.IParkingManager;
import com.rakesh.parkinglot.datamodel.Vehicle;
import com.rakesh.parkinglot.parkingstrategy.api.VehicleParkingStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingManager<T extends Vehicle> implements IParkingManager<T> {
    private Map<Integer, ILevelParkingManager<T>> levelParkingManagerMap;
    private static ParkingManager instance = null;

    public static <T extends Vehicle> ParkingManager<T> getInstance(List<Integer> parkingLevelsList, List<Integer>
            capacityList, List<VehicleParkingStrategy> vehicleParkingStrategiesList) {
        if (instance == null) {
            synchronized (ParkingManager.class) {
                if (instance == null) {
                    instance = new ParkingManager(parkingLevelsList, capacityList, vehicleParkingStrategiesList);
                }
            }
        }
        return instance;
    }

    private ParkingManager(List<Integer> parkingLevelsList, List<Integer>
            capacityList, List<VehicleParkingStrategy> vehicleParkingStrategiesList) {
        if (levelParkingManagerMap == null) {
            levelParkingManagerMap = new HashMap<>();
        }
        for (int i = 0; i < parkingLevelsList.size(); i++) {
            levelParkingManagerMap.put(parkingLevelsList.get(i), LevelParkingManager.getInstance(parkingLevelsList
                    .get(i), capacityList.get(i), vehicleParkingStrategiesList.get(i)));
        }
    }

    @Override
    public int parkCar(int level, T vehicle) {
        return levelParkingManagerMap.get(level).parkCar(vehicle);
    }

    @Override
    public boolean leaveCar(int level, int slotNumber) {
        return levelParkingManagerMap.get(level).leaveCar(slotNumber);
    }

    @Override
    public List<String> getStatus(int level) {
        return levelParkingManagerMap.get(level).getStatus();
    }

    @Override
    public List<String> getRegistrationNumbersForColor(int level, String color) {
        return levelParkingManagerMap.get(level).getRegistrationNumbersForColor(color);
    }

    @Override
    public List<Integer> getSlotNumbersFromColor(int level, String color) {
        return levelParkingManagerMap.get(level).getSlotNumbersFromColor(color);
    }

    @Override
    public int getSlotNoFromRegistrationNumber(int level, String registrationNo) {
        return levelParkingManagerMap.get(level).getSlotNoFromRegistrationNumber(registrationNo);
    }

    @Override
    public int getAvailableSlotsCount(int level) {
        return levelParkingManagerMap.get(level).getAvailableSlotsCount();
    }

    @Override
    public void doSystemCleanup() {
        for (ILevelParkingManager<T> levelParkingManager : levelParkingManagerMap.values()) {
            levelParkingManager.doSystemCleanup();
        }
        levelParkingManagerMap = null;
        instance = null;
    }
}
