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
import java.util.StringJoiner;
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
    public Optional<Integer> park(int level, Vehicle vehicle) throws ParkingException {
        Optional<Integer> value = Optional.empty();
        lock.writeLock().lock();
        validateParkingLot();
        try {
            value = Optional.of(parkingManager.parkCar(level, vehicle));
            if (value.get() == ParkingConstantUtil.NOT_AVAILABLE) {
                System.out.println("Sorry, parking lot is full");
            } else if (value.get() == ParkingConstantUtil.VEHICLE_ALREADY_EXIST) {
                System.out.println("Sorry, another vehicle is already parked.");
            } else {
                System.out.println("Allocated slot number: " + value.get());
            }
        } catch (Exception exp) {
            throw new ParkingException(ParkingException.ErrorCode.PROCESSING_ERROR.getMessage(), exp);
        } finally {
            lock.writeLock().unlock();
        }
        return value;
    }

    @Override
    public void unPark(int level, int slotNumber) throws ParkingException {
        lock.writeLock().lock();
        validateParkingLot();
        try {
            if (parkingManager.leaveCar(level, slotNumber)) {
                System.out.println("Slot number " + slotNumber + " is free");
            } else {
                System.out.println("Slot number " + slotNumber + " is already empty");
                System.out.println();
            }
        } catch (Exception exp) {
            throw new ParkingException(ParkingException.ErrorCode.INVALID_VALUE.getMessage().replace("{variable}",
                    "slot number"), exp);
        } finally {
            lock.writeLock().unlock();
        }

    }

    @Override
    public void getStatus(int level) throws ParkingException {
        lock.readLock().lock();
        validateParkingLot();
        try {
            System.out.println("Slot No. \t\t  Registration No \t\t Colour");
            List<String> statusList = parkingManager.getStatus(level);
            if (statusList.size() == 0)
                System.out.println("Sorry, parking lot is empty.");
            else {
                for (String status : statusList) {
                    System.out.println(status);
                }
            }
        } catch (Exception e) {
            throw new ParkingException(ParkingException.ErrorCode.PROCESSING_ERROR.getMessage(), e);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Optional<Integer> getAvailableSlotsCount(int level) throws ParkingException {
        lock.readLock().lock();
        Optional<Integer> value = Optional.empty();
        validateParkingLot();
        try {
            value = Optional.of(parkingManager.getAvailableSlotsCount(level));
        } catch (Exception exp) {
            throw new ParkingException(ParkingException.ErrorCode.PROCESSING_ERROR.getMessage(), exp);
        } finally {
            lock.readLock().unlock();
        }
        return value;
    }

    @Override
    public void getRegNumberForColor(int level, String colour) throws ParkingException {
        lock.readLock().lock();
        validateParkingLot();
        try {
            List<String> registrationNumbersList = parkingManager.getRegistrationNumbersForColor(level, colour);
            if (registrationNumbersList.size() == 0) {
                System.out.println("Not Found");
            } else {
                System.out.println(String.join(",", registrationNumbersList));
            }
        } catch (Exception exp) {
            throw new ParkingException(ParkingException.ErrorCode.PROCESSING_ERROR.getMessage(), exp);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void getSlotNumbersFromColor(int level, String colour) throws ParkingException {
        lock.readLock().lock();
        validateParkingLot();
        try {
            List<Integer> slotList = parkingManager.getSlotNumbersFromColor(level, colour);
            if (slotList.size() == 0)
                System.out.println("Not Found");
            StringJoiner joiner = new StringJoiner(",");
            for (Integer slot : slotList) {
                joiner.add(slot + "");
            }
            System.out.println(joiner.toString());
        } catch (Exception exp) {
            throw new ParkingException(ParkingException.ErrorCode.PROCESSING_ERROR.getMessage(), exp);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public int getSlotNoFromRegistrationNumber(int level, String registrationNumber) throws ParkingException {
        int value = -1;
        lock.readLock().lock();
        validateParkingLot();
        try {
            value = parkingManager.getSlotNoFromRegistrationNumber(level, registrationNumber);
            System.out.println(value != -1 ? value : "Not Found");
        } catch (Exception exp) {
            throw new ParkingException(ParkingException.ErrorCode.PROCESSING_ERROR.getMessage(), exp);
        } finally {
            lock.readLock().unlock();
        }
        return value;
    }

    @Override
    public void doSystemCleanup() {
        if (parkingManager != null)
            parkingManager.doSystemCleanup();
    }

    private void validateParkingLot() throws ParkingException {
        if (parkingManager == null) {
            throw new ParkingException(ParkingException.ErrorCode.PARKING_NOT_EXIST_ERROR.getMessage());
        }
    }
}
