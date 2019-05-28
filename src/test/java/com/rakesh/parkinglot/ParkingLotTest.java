package com.rakesh.parkinglot;

import com.rakesh.parkinglot.datamodel.Car;
import com.rakesh.parkinglot.exception.ParkingException;
import com.rakesh.parkinglot.service.api.IParkingService;
import com.rakesh.parkinglot.service.impl.ParkingService;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;

/**
 * JUnit test cases for Parking lot App.
 *
 */
public class ParkingLotTest {
    private int level;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void initialize(){
        this.level = 1;
        System.setOut(new PrintStream(outputStream));
    }

    @After
    public void doSystemCleanUp(){
        System.setOut(null);
    }
    @Test
    public void createParkingLot() throws ParkingException {
        IParkingService parkingServiceInstance = new ParkingService();
        parkingServiceInstance.createParkingLot(level, 15);
        assertTrue (outputStream.toString().trim().equalsIgnoreCase("Created a parking lot with 15 slots"));
        parkingServiceInstance.doSystemCleanup();
    }

    @Test
    public void parkCarWithoutParkinglot() throws Exception {
        IParkingService parkingServiceInstance = new ParkingService();
        thrown.expect(ParkingException.class);
        thrown.expectMessage (ParkingException.ErrorCode.PARKING_NOT_EXIST_ERROR.getMessage());
        parkingServiceInstance.park(level, new Car("KA-01-HH-1234", "White") );
        parkingServiceInstance.doSystemCleanup();
    }

    @Test
    public void parkCar() throws ParkingException {
        IParkingService parkingServiceInstance = new ParkingService();
        parkingServiceInstance.createParkingLot(level, 15);
        parkingServiceInstance.park(level, new Car("KA-01-HH-1234", "White") );
        assertTrue (outputStream.toString().trim().equalsIgnoreCase("Created a parking lot with 15 slots\n" +
                "Allocated slot number: 1"));
        parkingServiceInstance.doSystemCleanup();
    }

    @Test
    public void parkingFull() throws ParkingException {
        IParkingService parkingServiceInstance = new ParkingService();
        parkingServiceInstance.createParkingLot(level, 1);
        parkingServiceInstance.park(level, new Car("KA-01-HH-1234", "White") );
        parkingServiceInstance.park(level, new Car("KA-01-HH-9999", "White") );
        assertTrue(outputStream.toString().trim().equalsIgnoreCase("Created a parking lot with 1 slots\n" +
                "Allocated slot number: 1\n" +
                "Sorry, parking lot is full"));
        parkingServiceInstance.doSystemCleanup();
    }

    @Test
    public void status() throws ParkingException {
        IParkingService parkingServiceInstance = new ParkingService();
        parkingServiceInstance.createParkingLot(level, 1);
        parkingServiceInstance.park(level, new Car("KA-01-HH-1234", "White") );
        parkingServiceInstance.getStatus(level);
        assertTrue(outputStream.toString().trim().equalsIgnoreCase("Created a parking lot with 1 slots\n" +
                "Allocated slot number: 1\n" +
                "Slot No. \t  Registration No \t Colour\n" +
                "1\t\t\tKA-01-HH-1234\t\t\tWhite"));
        parkingServiceInstance.doSystemCleanup();
    }

    @Test
    public void unPark() throws ParkingException {
        IParkingService parkingServiceInstance = new ParkingService();
        parkingServiceInstance.createParkingLot(level, 1);
        parkingServiceInstance.park(level, new Car("KA-01-HH-1234", "White") );
        parkingServiceInstance.unPark(level, 1);
        assertTrue(outputStream.toString().trim().equalsIgnoreCase("Created a parking lot with 1 slots\n" +
                "Allocated slot number: 1\n" +
                "Slot number 1 is free"));
        parkingServiceInstance.doSystemCleanup();
    }

    @Test
    public void registrationNumberWithColor() throws ParkingException {
        IParkingService parkingServiceInstance = new ParkingService();
        parkingServiceInstance.createParkingLot(level, 2);
        parkingServiceInstance.park(level, new Car("KA-01-HH-1234", "White") );
        parkingServiceInstance.park(level, new Car("KA-01-HH-9999", "Black") );
        parkingServiceInstance.getRegNumberForColor(level, "White");
        assertTrue(outputStream.toString().trim().equalsIgnoreCase("Created a parking lot with 2 slots\n" +
                "Allocated slot number: 1\n" +
                "Allocated slot number: 2\n" +
                "KA-01-HH-1234"));
        parkingServiceInstance.doSystemCleanup();
    }

    @Test
    public void slotNumberWithColor() throws ParkingException {
        IParkingService parkingServiceInstance = new ParkingService();
        parkingServiceInstance.createParkingLot(level, 2);
        parkingServiceInstance.park(level, new Car("KA-01-HH-1234", "White") );
        parkingServiceInstance.park(level, new Car("KA-01-HH-9999", "Black") );
        parkingServiceInstance.getSlotNumbersFromColor(level, "White");
        assertTrue(outputStream.toString().trim().equalsIgnoreCase("Created a parking lot with 2 slots\n" +
                "Allocated slot number: 1\n" +
                "Allocated slot number: 2\n" +
                "1"));
        parkingServiceInstance.doSystemCleanup();
    }

    @Test
    public void slotNumberForRegNo() throws ParkingException {
        IParkingService parkingServiceInstance = new ParkingService();
        parkingServiceInstance.createParkingLot(level, 1);
        parkingServiceInstance.park(level, new Car("KA-01-HH-9999", "Black") );
        parkingServiceInstance.getSlotNoFromRegistrationNumber(level, "KA-01-HH-9999");
        assertTrue(outputStream.toString().trim().equalsIgnoreCase("Created a parking lot with 1 slots\n" +
                "Allocated slot number: 1\n" +
                "1"));
        parkingServiceInstance.doSystemCleanup();
    }

    @Test
    public void slotNumberForUnparkedRegNo() throws ParkingException {
        IParkingService parkingServiceInstance = new ParkingService();
        parkingServiceInstance.createParkingLot(level, 1);
        parkingServiceInstance.park(level, new Car("KA-01-HH-1234", "White") );
        parkingServiceInstance.getSlotNoFromRegistrationNumber(level, "KA-01-HH-9997");
        assertTrue(outputStream.toString().trim().equalsIgnoreCase("Created a parking lot with 1 slots\n" +
                "Allocated slot number: 1\n" +
                "Not Found"));
        parkingServiceInstance.doSystemCleanup();
    }

    @Test
    public void availableCount() throws ParkingException {
        IParkingService parkingServiceInstance = new ParkingService();
        parkingServiceInstance.createParkingLot(level, 2);
        parkingServiceInstance.park(level, new Car("KA-01-HH-1234", "White") );
        parkingServiceInstance.getAvailableSlotsCount(level);
        assertTrue(outputStream.toString().trim().equalsIgnoreCase("Created a parking lot with 2 slots\n" +
                "Allocated slot number: 1\n" +
                "1"));
        parkingServiceInstance.doSystemCleanup();
    }

}
