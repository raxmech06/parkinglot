package com.rakesh.parkinglot.data.access.impl;

import com.rakesh.parkinglot.data.access.api.ILevelParkingManager;
import com.rakesh.parkinglot.datamodel.Vehicle;
import com.rakesh.parkinglot.parkingstrategy.api.VehicleParkingStrategy;
import com.rakesh.parkinglot.parkingstrategy.impl.ShortestDisFirstParkingStrategy;
import com.rakesh.parkinglot.util.ParkingConstantUtil;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class LevelParkingManager<T extends Vehicle> implements ILevelParkingManager<T> {
    private AtomicInteger level = new AtomicInteger(0);
    private AtomicInteger capacity = new AtomicInteger();
    private AtomicInteger availability = new AtomicInteger();
    private VehicleParkingStrategy vehicleParkingStrategy;
    private Map<Integer, Optional<T>> slotVehicleMap;
    private static LevelParkingManager instance = null;

    public static <T extends Vehicle> LevelParkingManager<T> getInstance(int level, int capacity, VehicleParkingStrategy
            vehicleParkingStrategy) {
        if (instance == null) {
            synchronized (LevelParkingManager.class) {
                if (instance == null) {
                    instance = new LevelParkingManager(level, capacity, vehicleParkingStrategy);
                }
            }
        }
        return instance;
    }

    private LevelParkingManager(int level, int capacity, VehicleParkingStrategy vehicleParkingStrategy) {
        this.level.set(level);
        this.capacity.set(capacity);
        this.availability.set(capacity);
        this.vehicleParkingStrategy = vehicleParkingStrategy;
        if (vehicleParkingStrategy == null) {
            vehicleParkingStrategy = new ShortestDisFirstParkingStrategy();
        }
        slotVehicleMap = new ConcurrentHashMap<>();
        for (int i = 0; i < capacity; i++) {
            slotVehicleMap.put(i, Optional.empty());
            vehicleParkingStrategy.add(i);
        }
    }

    @Override
    public int parkCar(T vehicle) {
        int availableSlot = 0;
        if (availability.get() == 0) {
            return ParkingConstantUtil.NOT_AVAILABLE;
        } else {
            availableSlot = vehicleParkingStrategy.getSlot();
            if (slotVehicleMap.containsValue(Optional.of(vehicle))) {
                return ParkingConstantUtil.VEHICLE_ALREADY_EXIST;
            }
            slotVehicleMap.put(availableSlot, Optional.of(vehicle));
            availability.decrementAndGet();
            vehicleParkingStrategy.removeSlot(availableSlot);
        }
        return availableSlot;
    }

    @Override
    public boolean leaveCar(int slotNumber) {
        if (!slotVehicleMap.get(slotNumber).isPresent()) {
            return false;
        }
        availability.incrementAndGet();
        vehicleParkingStrategy.add(slotNumber);
        slotVehicleMap.put(slotNumber, Optional.empty());
        return true;
    }

    @Override
    public List<String> getStatus() {
        return null;
    }

    @Override
    public List<String> getRegistrationNumbersForColor(String color) {
        return null;
    }

    @Override
    public List<Integer> getSlotNumbersFromColor(String colour) {
        return null;
    }

    @Override
    public int getSlotNoFromRegistrationNumber(String registrationNo) {
        return 0;
    }

    @Override
    public int getAvailableSlotsCount() {
        return 0;
    }

    @Override
    public void doSystemCleanup() {

    }
}
