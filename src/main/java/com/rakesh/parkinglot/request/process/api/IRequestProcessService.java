package com.rakesh.parkinglot.request.process.api;

import com.rakesh.parkinglot.service.api.IParkingService;
import com.rakesh.parkinglot.util.ParkingConstantUtil;

import java.util.HashMap;
import java.util.Map;

public interface IRequestProcessService {
    public static Map<String, Integer> commandsParameterMap = new HashMap<String, Integer>();
    public void setParkingService(IParkingService parkingService);
    public void executeRequest(String request);
    public default boolean validateRequest(String request) {
        boolean validRequest = true;
        try {
            String[] inputs = request.split(" ");
            int params = ParkingConstantUtil.getRequestsParameterMap().get(inputs[0]);
            switch (inputs.length) {
                case 1:
                    if (params != 0) // e.g status -> inputs = 1
                        validRequest = false;
                    break;
                case 2:
                    if (params != 1) // create_parking_lot 6 -> inputs = 2
                        validRequest = false;
                    break;
                case 3:
                    if (params != 2) // park KA-01-P-333 White -> inputs = 3
                        validRequest = false;
                    break;
                default:
                    validRequest = false;
            }
        } catch (Exception e) {
            validRequest = false;
        }
        return validRequest;
    }
}
