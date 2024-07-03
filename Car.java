public class Car {
    private String registrationNumber;
    private String make;
    private String model;
    private int year;
    private long parkingTime;
    private long parkingStartTime;


    /**
     * Constructs a Car object with the given parameters.
     *
     * @param registrationNumber The registration number of the car.
     * @param make               The make of the car.
     * @param model              The model of the car.
     * @param year               The year of the car.
     * @author [OM PATIL]
     * @version 1.0
     */
    public Car(String registrationNumber, String make, String model, int year) {
    this.registrationNumber = registrationNumber;
    this.make = make;
    this.model = model;
    this.year = year;
    this.parkingStartTime = System.currentTimeMillis();
    this.parkingTime = System.currentTimeMillis(); // Set the parking time to the current time in milliseconds
}

public long getParkingStartTime() {
    return parkingStartTime;
}


    /**
     * Sets the parking time of the car.
     *
     * @param parkingTime The parking time of the car.
     */
    public void setParkingTime(long parkingTime) {
        this.parkingTime = parkingTime;
    }

    /**
     * Gets the registration number of the car.
     *
     * @return The registration number of the car.
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Gets the make of the car.
     *
     * @return The make of the car.
     */
    public String getMake() {
        return make;
    }

    /**
     * Gets the model of the car.
     *
     * @return The model of the car.
     */
    public String getModel() {
        return model;
    }

    /**
     * Gets the year of the car.
     *
     * @return The year of the car.
     */
    public int getYear() {
        return year;
    }

    /**
     * Gets the parking time of the car.
     *
     * @return The parking time of the car.
     */
    public long getParkingTime() {
        return parkingTime;
    }

    /**
     * Returns a string representation of the car.
     *
     * @return A string representation of the car.
     */
    @Override
    public String toString() {
        return "Car{" +
                "registrationNumber='" + registrationNumber + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                '}';
    }
    
}