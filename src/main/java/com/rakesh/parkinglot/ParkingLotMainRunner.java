package com.rakesh.parkinglot;

import com.rakesh.parkinglot.exception.ParkingException;
import com.rakesh.parkinglot.request.process.api.IRequestProcessService;
import com.rakesh.parkinglot.request.process.impl.RequestProcessService;
import com.rakesh.parkinglot.service.impl.ParkingService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ParkingLotMainRunner {
    private static Map<Integer, String> requestMap = new HashMap<>();

    static {
        requestMap.put(1, "create_parking_lot 6");
        requestMap.put(2, "park KA-01-HH-1234 White");
        requestMap.put(3, "park KA-01-HH-9999 White");
        requestMap.put(4, "park KA-01-BB-0001 Black");
        requestMap.put(5, "park KA-01-HH-7777 Red");
        requestMap.put(6, "park KA-01-HH-2701 Blue");
        requestMap.put(7, "park KA-01-HH-3141 Black");
        requestMap.put(8, "leave 4");
        requestMap.put(9, "status");
        requestMap.put(10, "park KA-01-P-333 White");
        requestMap.put(11, "park DL-12-AA-9999 White");
        requestMap.put(12, "registration_numbers_for_cars_with_colour White");
        requestMap.put(13, "slot_numbers_for_cars_with_colour White");
        requestMap.put(14, "slot_number_for_registration_number KA-01-HH-3141");
        requestMap.put(15, "slot_number_for_registration_number MH-04-AY-1111");
        requestMap.put(16, "exit");
    }

    private static void runCommands() {
        IRequestProcessService requestProcessService = new RequestProcessService();
        requestProcessService.setParkingService(new ParkingService());
        String request;
        for (Integer i : requestMap.keySet()) {
            request = requestMap.get(i);
            try {
                requestProcessService.executeRequest(request.trim());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public static void main(String[] args) throws ParkingException {
        //To execute all requests uncomment the following line
        //runCommands();
        IRequestProcessService requestProcessService = new RequestProcessService();
        requestProcessService.setParkingService(new ParkingService());
        String input = null;
        Scanner scanner = new Scanner(System.in);
        try {
            printMessageForUser();
            switch (args.length) {
                case 0: {
                    System.out.println("Please enter 'exit' to end execution");
                    System.out.println("Request:");
                    while (true) {
                        input = scanner.nextLine();
                        if (input.equalsIgnoreCase("exit")) {
                            break;
                        } else {
                            if (requestProcessService.validateRequest(input)) {
                                try {
                                    requestProcessService.executeRequest(input.trim());
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            } else {
                                printMessageForUser();
                            }
                        }
                    }
                    break;
                }
                case 1: {
                    File fileInput = new File(args[0]);
                    BufferedReader bufferReader = null;
                    try {
                        bufferReader = new BufferedReader(new FileReader(fileInput));
                        int lineNo = 1;
                        while ((input = bufferReader.readLine()) != null) {
                            input = input.trim();
                            if (requestProcessService.validateRequest(input)) {
                                try {
                                    requestProcessService.executeRequest(input);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            } else
                                System.out.println("Incorrect Command Found at line: " + lineNo + " ,Input: " + input);
                            lineNo++;
                        }
                    } catch (Exception e) {
                        throw new ParkingException(ParkingException.ErrorCode.INVALID_FILE.getMessage(), e);
                    } finally {
                        try {
                            if (bufferReader != null)
                                bufferReader.close();
                        } catch (IOException e) {
                        }
                    }
                    break;
                }
                default:
                    System.out.println("Invalid input. Usage Style: java -jar <jar_file_path> <input_file_path>");
            }
        } catch (ParkingException exp) {
            exp.printStackTrace();
            System.out.println(exp.getMessage());
        }

    }

    private static void printMessageForUser() {
        StringBuilder sb = new StringBuilder();
        sb = sb.append("=====Commands : {variable} to input=====").append("\n");
        sb = sb.append("A) For creating parking lot of size n => create_parking_lot {capacity}").append("\n");
        sb = sb.append("B) To park a car => park <<car_number>> {car_clour}").append("\n");
        sb = sb.append("C) Remove(Unpark) car from parking => leave {slot_number}").append("\n");
        sb = sb.append("D) Print status of parking slot => status").append("\n");
        sb = sb.append("E) Get cars registration no for the given car color => registration_numbers_for_cars_with_color " +
                "{car_color}").append("\n");
        sb = sb.append("F) Get slot numbers for the given car color => slot_numbers_for_cars_with_color {car_color}")
                .append("\n");
        sb = sb.append("G) Get slot number for the given car number => slot_number_for_registration_number " +
                "{car_number}").append("\n");
        System.out.println(sb.toString());
    }
}
