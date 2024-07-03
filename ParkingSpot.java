import java.time.LocalDateTime;
import java.time.Duration;

/**
 * Represents a parking spot in a car park.
 * @author [OM PATIL]
 * @version 1.0
 */
public class ParkingSpot {
    private String spotID;
    private boolean occupied;
    private Car parkedCar;
    private LocalDateTime parkingStartTime;

    /**
     * Constructs a ParkingSpot object with the given spot ID.
     *
     * @param spotID The spot ID of the parking spot.
     */
    public ParkingSpot(String spotID) {
        this.spotID = spotID;
        this.occupied = false;
    }

    /**
     * Parks a car in the parking spot.
     *
     * @param car The car to be parked in the spot.
     * @return True if the car is successfully parked, false otherwise.
     */
    public boolean parkCar(Car car) {
        if (!occupied) {
            this.parkedCar = car;
            this.occupied = true;
            System.out.println("Car parked successfully in spot " + spotID);
            return true;
        } else {
            System.out.println("Spot " + spotID + " is already occupied");
            return false;
        }
    }

    /**
     * Records the current time when a car is parked in the spot.
     */
    public void recordParkingTime() {
        parkingStartTime = LocalDateTime.now();
    }

    /**
     * Calculates and returns the parking time length for the occupied parking spot.
     *
     * @return The parking time length as a string in the format "X hours Y minutes Z seconds".
     */
    public String getParkingTimeLength() {
        if (occupied) {
            LocalDateTime currentTime = LocalDateTime.now();
            Duration duration = Duration.between(parkingStartTime, currentTime);

            long hours = duration.toHours();
            long minutes = duration.toMinutesPart();
            long seconds = duration.toSecondsPart();

            return hours + " hours " + minutes + " minutes " + seconds + " seconds";
        } else {
            return "Spot is unoccupied";
        }
    }

    /**
     * Removes the parked car from the spot.
     *
     * @return The car removed from the spot, or null if the spot was empty.
     */
    public Car removeCar() {
        if (occupied) {
            Car removedCar = this.parkedCar;
            this.parkedCar = null;
            this.occupied = false;
            System.out.println("Car removed from spot " + spotID);
            return removedCar;
        } else {
            System.out.println("Spot " + spotID + " is already empty");
            return null;
        }
    }

    /**
     * Checks if the parking spot is occupied.
     *
     * @return True if the spot is occupied, false otherwise.
     */
    public boolean isOccupied() {
        return occupied;
    }

    /**
     * Gets the spot ID of the parking spot.
     *
     * @return The spot ID of the parking spot.
     */
    public String getSpotID() {
        return spotID;
    }

    /**
     * Gets the car parked in the spot.
     *
     * @return The car parked in the spot, or null if the spot is empty.
     */
    public Car getParkedCar() {
        return parkedCar;
    }
}