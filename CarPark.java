import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CarPark {
    private List<ParkingSpot> parkingSpots;
    private static final Pattern SPOT_ID_PATTERN = Pattern.compile("[A-Z]\\d{3}");

    public CarPark() {
        parkingSpots = new ArrayList<>();
    }
   /* // Method to check if all parking spots are occupied
    public boolean isFull() {
        for (ParkingSpot spot : parkingSpots) {
            if (!spot.isOccupied()) {
                return false; // If any spot is unoccupied, return false
            }
        }
        return true; // All spots are occupied
    }*/
    

    public void addParkingSpot(ParkingSpot spot) {
        if (isSpotIdValid(spot.getSpotID()) && isSpotIdUnique(spot.getSpotID())) {
            parkingSpots.add(spot);
            System.out.println("Parking spot " + spot.getSpotID() + " added successfully");
        } else {
            System.out.println("Failed to add parking spot. Invalid spot ID or spot ID already exists.");
        }
    }

    public boolean deleteParkingSpot(String spotID) {
        for (ParkingSpot spot : parkingSpots) {
            if (spot.getSpotID().equals(spotID)) {
                if (spot.isOccupied()) {
                    System.out.println("Failed to delete parking spot " + spotID + ". Spot is occupied.");
                    return false;
                }
                parkingSpots.remove(spot);
                System.out.println("Parking spot " + spotID + " deleted successfully");
                return true;
            }
        }
        System.out.println("Failed to delete parking spot " + spotID + ". Spot not found.");
        return false;
    }

    public void listAllSpots() {
        System.out.println("List of all parking spots:");
        for (ParkingSpot spot : parkingSpots) {
            String status = spot.isOccupied() ? "Occupied" : "Unoccupied";
            System.out.println("Spot ID: " + spot.getSpotID() + ", Status: " + status);
        }
    }

    public boolean isSpotIdValid(String spotID) {
        return SPOT_ID_PATTERN.matcher(spotID).matches();
    }

    public boolean isSpotIdUnique(String spotID) {
        for (ParkingSpot spot : parkingSpots) {
            if (spot.getSpotID().equals(spotID)) {
                return false;
            }
        }
        return true;
    }

    public ParkingSpot findParkingSpotById(String spotID) {
        for (ParkingSpot spot : parkingSpots) {
            if (spot.getSpotID().equals(spotID)) {
                return spot;
            }
        }
        return null; // Return null if spot with given ID is not found
    }

    public List<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }
    
    
   
    public void resetCarPark() {
    // Remove cars parked in all parking spots
    for (ParkingSpot spot : parkingSpots) {
        spot.removeCar();
    }
    
    // Clear the list of parking spots
    parkingSpots.clear();
    
    System.out.println("Car park reset. All cars removed and all parking spots deleted.");
}



public ParkingSpot findCarByRegistration(String registrationNumber) {
    for (ParkingSpot spot : parkingSpots) {
        if (spot.isOccupied() && spot.getParkedCar().getRegistrationNumber().equals(registrationNumber)) {
            System.out.println("Car with registration number " + registrationNumber + " found in spot " + spot.getSpotID());
            return spot;
        }
    }
    System.out.println("Car with registration number " + registrationNumber + " not found in any spot.");
    return null;
}
/**
 * Parks a car in an available parking spot and records the current time.
 *
 * @param car    The car to be parked.
 * @param spotID The ID of the parking spot.
 * @return True if the car is successfully parked, false otherwise.
 */
public boolean parkCar(Car car, String spotID) {
    if (!isSpotIdValid(spotID)) {
        System.out.println("Failed to park car. Invalid spot ID.");
        return false;
    }
    for (ParkingSpot spot : parkingSpots) {
        if (spot.getSpotID().equals(spotID)) {
            if (spot.isOccupied()) {
                System.out.println("Failed to park car. Spot " + spotID + " is occupied.");
                return false;
            }
            spot.parkCar(car);
            spot.recordParkingTime();
            System.out.println("Car parked successfully in spot " + spotID);
            return true;
        }
    }
    System.out.println("Failed to park car. Spot " + spotID + " not found.");
    return false;
}
/**
 * Removes a car from the car park by its registration number.
 *
 * @param registrationNumber The registration number of the car to be removed.
 * @return True if the car is successfully removed, false otherwise.
 */
public boolean removeCar(String registrationNumber) {
    for (ParkingSpot spot : parkingSpots) {
        if (spot.isOccupied() && spot.getParkedCar().getRegistrationNumber().equals(registrationNumber)) {
            spot.removeCar();
            System.out.println("Car with registration number " + registrationNumber + " removed from spot " + spot.getSpotID());
            return true;
        }
    }
    System.out.println("Car with registration number " + registrationNumber + " not found in any spot.");
    return false;
}
/**
 * Finds cars in the car park by their make.
 *
 * @param make The make of the cars to find.
 * @return A list of parking spots where cars of the specified make are parked.
 */
public List<ParkingSpot> findCarsByMake(String make) {
    List<ParkingSpot> spotsWithCarsByMake = new ArrayList<>();
    for (ParkingSpot spot : parkingSpots) {
        if (spot.isOccupied() && spot.getParkedCar().getMake().equalsIgnoreCase(make)) {
            spotsWithCarsByMake.add(spot);
        }
    }
    
    if (spotsWithCarsByMake.isEmpty()) {
        System.out.println("No cars found with make " + make);
    } else {
        System.out.println("Cars found with make " + make + " in the following spots:");
        for (ParkingSpot spot : spotsWithCarsByMake) {
            System.out.println("Spot ID: " + spot.getSpotID());
        }
    }
    return spotsWithCarsByMake;
}




}