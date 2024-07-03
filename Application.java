import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * This class represents the user interface for interacting with the car park system.
 *  @author [OM PATIL]
 * @version 1.0
 */
public class Application {
    private static CarPark carPark;
    private static Scanner scanner;

    /**
     * Main method to run the application.
     */
    public static void main(String[] args) {
        carPark = new CarPark();
        scanner = new Scanner(System.in);

        displayMenu();
        int choice = getUserChoice();

        while (choice != 9) {
            switch (choice) {
                case 1:
                    addParkingSpot();
                    break;
                case 2:
                    deleteParkingSpot();
                    break;
                case 3:
                    listAllSpots();
                    break;
                case 4:
                    parkCar();
                    break;
                case 5:
                    findCarByRegistration();
                    break;
                case 6:
                    removeCar();
                    break;
                case 7:
                    findCarsByMake();
                    break;
                case 8:
                    resetCarPark();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            displayMenu();
            choice = getUserChoice();
        }

        System.out.println("Program ends!");
        scanner.close();
    }

    /**
     * Display the menu options.
     */
    private static void displayMenu() {
        System.out.println("Menu:");
        System.out.println("1. Add a parking spot");
        System.out.println("2. Delete a parking spot");
        System.out.println("3. List all spots");
        System.out.println("4. Park a car");
        System.out.println("5. Find a car by registration number");
        System.out.println("6. Remove a car");
        System.out.println("7. Find cars by make");
        System.out.println("8. Reset car park");
        System.out.println("9. Exit");
        System.out.print("Enter your choice: ");
    }

    /**
     * Get user choice from the menu.
     */
    private static int getUserChoice() {
        int choice = 0;
        while (true) {
            try {
                choice = scanner.nextInt();
                if (choice < 1 || choice > 9) {
                    throw new InputMismatchException();
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer choice (1-9).");
                scanner.nextLine(); // Consume the invalid input
                displayMenu(); // Display menu again
            }
        }
        return choice;
    }

    /**
     * Add a parking spot to the car park.
     */
    private static void addParkingSpot() {
        scanner.nextLine(); // Consume newline
        System.out.print("Enter spot ID (Format: uppercase letter followed by 3 digits): ");
        String spotID = scanner.nextLine().toUpperCase();

        if (!isValidSpotID(spotID)) {
            System.out.println("Failed to add parking spot. Invalid spot ID format.");
            return;
        }

        if (carPark.findParkingSpotById(spotID) != null) {
            System.out.println("Failed to add parking spot. Spot already exists.");
            return;
        }

        ParkingSpot spot = new ParkingSpot(spotID);
        carPark.addParkingSpot(spot);
        System.out.println("Parking spot " + spotID + " added successfully.");
    }

    /**
     * Delete a parking spot from the car park.
     */
    private static void deleteParkingSpot() {
        scanner.nextLine(); // Consume newline
        System.out.print("Enter spot ID to delete: ");
        String spotID = scanner.nextLine().toUpperCase();

        if (!isValidSpotID(spotID)) {
            System.out.println("Failed to delete parking spot. Invalid spot ID format.");
            return;
        }

        ParkingSpot spot = carPark.findParkingSpotById(spotID);
        if (spot == null) {
            System.out.println("Failed to delete parking spot. Spot does not exist.");
            return;
        }

        if (spot.isOccupied()) {
            System.out.println("Failed to delete parking spot. Spot is occupied.");
            return;
        }

        carPark.deleteParkingSpot(spotID);
        System.out.println("Parking spot " + spotID + " deleted successfully.");
    }

    /**
     * List all parking spots in the car park.
     */
    private static void listAllSpots() {
        carPark.listAllSpots();
    }

private static void parkCar() {
    scanner.nextLine(); // Consume newline
    System.out.print("Enter car details:\n" +
            "Registration Number (Format: uppercase letter followed by 4 digits): "); // Prompt for registration number
    String registrationNumber = scanner.nextLine().toUpperCase(); // Read registration number from user

    System.out.print("Make: "); // Prompt for car make
    String make = scanner.nextLine(); // Read car make from user

    System.out.print("Model: "); // Prompt for car model
    String model = scanner.nextLine(); // Read car model from user

    int year;
    while (true) {
        System.out.print("Enter car year (between 2004 and 2024): "); // Prompt for car year
        try {
            year = scanner.nextInt(); // Read car year from user
            if (year < 2004 || year > 2024) { // Validate car year
                throw new InputMismatchException(); // Throw exception for invalid year
            }
            break; // Break out of loop if input is valid
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid year between 2004 and 2024."); // Error message for invalid year
            scanner.nextLine(); // Consume invalid input
        }
    }

    scanner.nextLine(); // Consume newline
    String spotID;
    boolean success = false; // Flag to track if car was successfully parked
    while (true) {
        System.out.print("Enter spot ID (Format: uppercase letter followed by 3 digits): "); // Prompt for spot ID
        spotID = scanner.nextLine().toUpperCase(); // Read spot ID from user
        if (!isValidSpotID(spotID)) { // Validate spot ID format
            System.out.println("Failed to park car. Invalid spot ID format."); // Error message for invalid spot ID format
            continue; // Prompt user again
        }
        ParkingSpot spot = carPark.findParkingSpotById(spotID); // Find spot by ID
        if (spot == null) { // Check if spot exists
            System.out.print("Spot ID does not exist. Would you like to add this spot? (yes/no): ");
            String addSpot = scanner.nextLine().toLowerCase();
            if (addSpot.equals("yes")) {
                ParkingSpot newSpot = new ParkingSpot(spotID); // Create new spot
                carPark.addParkingSpot(newSpot); // Add new spot to car park
                System.out.println("Parking spot " + spotID + " added successfully.");
                success = true; // Set success flag to true
                break; // Exit the loop after adding the spot
            } else {
                continue; // Prompt user again
            }
        } else if (spot.isOccupied()) { // Check if spot is occupied
            System.out.println("Spot " + spotID + " is already occupied."); // Error message for occupied spot
            continue; // Prompt user again
        } else {
            // Proceed with parking the car
            success = carPark.parkCar(new Car(registrationNumber, make, model, year), spotID); // Park car in spot
            break; // Exit the loop after attempting to park the car
        }
    }

    if (success) {
       // System.out.println("Car parked successfully in spot " + spotID + "."); // Success message
    } else {
        System.out.println("Failed to park car."); // Error message
    }
}



    /**
     * Find a car by its registration number.
     */
    private static void findCarByRegistration() {
        scanner.nextLine(); // Consume newline
        System.out.print("Enter car registration number: ");
        String registrationNumber = scanner.nextLine().toUpperCase();
        carPark.findCarByRegistration(registrationNumber);
    }

    /**
     * Remove a car from the car park.
     */
    private static void removeCar() {
        scanner.nextLine(); // Consume newline
        System.out.print("Enter car registration number: ");
        String registrationNumber = scanner.nextLine().toUpperCase();
        carPark.removeCar(registrationNumber);
    }

    /**
     * Find cars by make.
     */
    private static void findCarsByMake() {
        scanner.nextLine(); // Consume newline
        System.out.print("Enter car make: ");
        String make = scanner.nextLine();
        carPark.findCarsByMake(make);
    }

    /**
     * Reset the car park.
     */
    private static void resetCarPark() {
        carPark.resetCarPark();
    }

    /**
     * Check if the spot ID format is valid.
     */
    private static boolean isValidSpotID(String spotID) {
        return spotID.matches("[A-Z]\\d{3}");
    }
}