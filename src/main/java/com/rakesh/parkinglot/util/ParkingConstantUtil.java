package com.rakesh.parkinglot.util;

import java.util.HashMap;
import java.util.Map;

public class ParkingConstantUtil {
    public static final String CREATE_PARKING_LOT = "create_parking_lot";
    public static final String PARK = "park";
    public static final String LEAVE = "leave";
    public static final String STATUS = "status";
    public static final String REG_NUMBER_FOR_CARS_WITH_COLOR = "registration_numbers_for_cars_with_colour";
    public static final String SLOTS_NUMBER_FOR_CARS_WITH_COLOR = "slot_numbers_for_cars_with_colour";
    public static final String SLOTS_NUMBER_FOR_REG_NUMBER = "slot_number_for_registration_number";

    public static final int NOT_AVAILABLE = -1;
    public static final int VEHICLE_ALREADY_EXIST = -2;

    public static final int NOT_FOUND = -1;
    private static final Map<String, Integer> requestsParameterMap = new HashMap<String, Integer>();
    static {
        requestsParameterMap.put(CREATE_PARKING_LOT, 1);
        requestsParameterMap.put(PARK, 2);
        requestsParameterMap.put(LEAVE, 1);
        requestsParameterMap.put(STATUS, 0);
        requestsParameterMap.put(REG_NUMBER_FOR_CARS_WITH_COLOR, 1);
        requestsParameterMap.put(SLOTS_NUMBER_FOR_CARS_WITH_COLOR, 1);
        requestsParameterMap.put(SLOTS_NUMBER_FOR_REG_NUMBER, 1);
    }

    /**
     * @return requestsParameterMap
     */
    public static Map<String, Integer> getRequestsParameterMap(){
        return requestsParameterMap;
    }

    public static void addRequest(String request, int parameterCount) {
        requestsParameterMap.put(request, parameterCount);
    }
}
