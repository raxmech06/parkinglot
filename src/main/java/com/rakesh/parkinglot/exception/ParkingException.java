package com.rakesh.parkinglot.exception;

public class ParkingException extends Exception {
    private String errorCode = null;
    private Object[] errorParameters = null;

    public ParkingException(String message) {
        super(message);
    }

    public ParkingException(Throwable throwable) {
        super(throwable);
    }

    public ParkingException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ParkingException(String errorCode, String message, Object[] errorParameters) {
        super(message);
        this.setErrorCode(errorCode);
        this.setErrorParameters(errorParameters);
    }

    public ParkingException(String errorCode, String message, Throwable throwable) {
        super(message, throwable);
        this.setErrorCode(errorCode);
    }

    public ParkingException(String errorCode, String message, Object[] errorParameters, Throwable throwable) {
        super(message, throwable);
        this.setErrorCode(errorCode);
        this.setErrorParameters(errorParameters);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Object[] getErrorParameters() {
        return errorParameters;
    }

    public void setErrorParameters(Object[] errorParameters) {
        this.errorParameters = errorParameters;
    }

    public enum ErrorCode {
        PARKING_ALREADY_EXIST("Sorry Parking Already Created, It CAN NOT be again recreated."),
        PARKING_NOT_EXIST_ERROR("Sorry, Car Parking Does not Exist"),
        INVALID_VALUE("{variable} value is incorrect"),
        INVALID_FILE("Invalid File"),
        PROCESSING_ERROR("Processing Error "),
        INVALID_REQUEST("Invalid Request");

        private String message = "";

        /**
         * @param message
         */
        private ErrorCode(String message) {
            this.message = message;
        }

        /**
         * @return String
         */
        public String getMessage() {
            return message;
        }
    }
}
