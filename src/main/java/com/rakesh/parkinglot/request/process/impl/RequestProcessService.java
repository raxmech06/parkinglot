package com.rakesh.parkinglot.request.process.impl;

import com.rakesh.parkinglot.datamodel.Car;
import com.rakesh.parkinglot.exception.ParkingException;
import com.rakesh.parkinglot.request.process.api.IRequestProcessService;
import com.rakesh.parkinglot.service.api.IParkingService;
import com.rakesh.parkinglot.util.ParkingConstantUtil;

public class RequestProcessService implements IRequestProcessService {
    private IParkingService parkingService;
    @Override
    public void setParkingService(IParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @Override
    public void executeRequest(String request) throws ParkingException {
        int level = 0; //Tomorrow this parking lot can be extended to a multi storey parking.
        String[] requestParts = request.split(" ");
        String keyAction = requestParts[0];
        switch (keyAction){
            case ParkingConstantUtil.CREATE_PARKING_LOT :
                int maxCapacity = Integer.parseInt(requestParts[1]);
                parkingService.createParkingLot(level, maxCapacity);
                break;
            case ParkingConstantUtil.PARK :
                parkingService.park(level, new Car(requestParts[1], requestParts[2]));
                break;
            case ParkingConstantUtil.LEAVE :
                int parkingSlotNumber = Integer.parseInt(requestParts[1]);
                parkingService.unPark(level, parkingSlotNumber);
                break;
            case ParkingConstantUtil.REG_NUMBER_FOR_CARS_WITH_COLOR :
                parkingService.getRegNumberForColor(level, requestParts[1]);
                break;
            case ParkingConstantUtil.SLOTS_NUMBER_FOR_CARS_WITH_COLOR:
                parkingService.getSlotNumbersFromColor(level, requestParts[1]);
                break;
            case ParkingConstantUtil.SLOTS_NUMBER_FOR_REG_NUMBER:
                parkingService.getSlotNoFromRegistrationNumber(level, requestParts[1]);
                break;
            default:
                break;
        }
    }
}
