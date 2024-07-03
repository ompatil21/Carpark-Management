/**
 * Represents a parking Spot System.
 * @author [OM PATIL]
 * @version 1.0
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.geom.RoundRectangle2D;
import java.time.Duration;
import java.time.LocalDateTime;
import java.text.SimpleDateFormat;
import java.util.Date;


    /**
      * CarParkGUI class represents the graphical user interface (GUI) for a Car Park Management System.
      * It extends the JFrame class and provides functionality to manage parking spots, park cars,
      * find cars by registration number or make, remove cars, and reset the car park.
      */

    public class CarParkGUI extends JFrame {
        private CarPark carPark;
        private JPanel leftPanel;
        private JPanel rightPanel;

/**
 * Constructor for the CarParkGUI class.
 * Initializes the CarPark object, sets up the GUI components, and adds buttons to the left panel.
 */
    public CarParkGUI() {
        carPark = new CarPark();

        setTitle("Car Park Management System");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Apply margin to main panel
        add(mainPanel);

        // Create left panel for buttons
        leftPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        //leftPanel.setOpaque(false); // Remove this line to make the left panel transparent
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10)); // Apply margin to left panel
        mainPanel.add(leftPanel, BorderLayout.WEST);


        // Create right panel for spot information
        rightPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 20, 20)); // Use FlowLayout with left alignment
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Apply margin to right panel
        JScrollPane scrollPane = new JScrollPane(rightPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add buttons to the left panel
        addButton("Add Parking Spot", "Add a new parking spot.");
        addButton("Delete Parking Spot", "Delete an existing parking spot.");
        addButton("List All Parking Spots", "List all parking spots.");
        addButton("Park a Car", "Park a car in a parking spot.");
        addButton("Find Car by Registration Number", "Find a car by its registration number.");
        addButton("Remove Car", "Remove a car from a parking spot.");
        addButton("Find Cars by Make", "Find cars by their make.");
        addButton("Reset Car Park", "Reset the car park.");
        addButton("Exit", "Exit the program.");
    }

/**
 * RoundedButton is a custom JButton class that extends JButton and provides rounded corners
 * and background color changes based on button state (pressed or hovered).
 */
    private class RoundedButton extends JButton {
        private boolean isPressed = false;

        /**
         * Constructor for the RoundedButton class.
         * Initializes the button with the given text and tooltip, and sets up event listeners
         * for mouse events to update the button's appearance and state.
         *
         * @param text The text to be displayed on the button.
         * @param tooltip The tooltip text to be displayed when hovering over the button.
         */
        public RoundedButton(String text, String tooltip) {
            super(text);
            setContentAreaFilled(false);
            setOpaque(true);
            setForeground(Color.BLACK);
            setBorderPainted(false); // Disable default button border
            setToolTipText(tooltip); // Set tooltip for the button

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    isPressed = true;
                    repaint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    isPressed = false;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    isPressed = false;
                    repaint();
                }
            });
        }

        /**
         * Overrides the paintComponent method to draw the button with rounded corners
         * and a background color that changes based on the button's state (pressed or hovered).
         *
         * @param g The Graphics object used for rendering the button.
         */
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int arcRadius = 10;

            if (getModel().isRollover() || isPressed) {
                g2.setColor(new Color(172, 141, 186)); // Set background color to a darker shade when pressed or hovered
            } else {
                g2.setColor(new Color(202, 171, 216)); // Set background color to #caabd8 when not pressed or hovered
            }

            // Create a rounded rectangle shape
            RoundRectangle2D roundedRect = new RoundRectangle2D.Float(0, 0, width, height, arcRadius, arcRadius);

            // Set the clip to the rounded rectangle shape
            g2.setClip(roundedRect);

            // Fill the clipped area with the background color
            g2.fillRect(0, 0, width, height);

            g2.setColor(getForeground());
            g2.drawString(getText(), getWidth() / 2 - getFontMetrics(getFont()).stringWidth(getText()) / 2, getHeight() / 2 + getFontMetrics(getFont()).getAscent() / 2);

            g2.dispose();
        }

/**
 * Overrides the contains method to determine if a point (x, y) is inside the button's rounded rectangle shape.
 *
 * @param x The x-coordinate of the point to be tested.
 * @param y The y-coordinate of the point to be tested.
 * @return True if the point is inside the button's rounded rectangle shape, false otherwise.
 */
        @Override
        public boolean contains(int x, int y) {
            int width = getWidth();
            int height = getHeight();
            int arcRadius = 10;

            Shape shape = new RoundRectangle2D.Double(0, 0, width, height, arcRadius, arcRadius);
            return shape.contains(x, y);
        }
    }

/**
 * Method to add a button to the left panel with rounded corners.
 *
 * @param text The text to be displayed on the button.
 * @param tooltip The tooltip text to be displayed when hovering over the button.
 */
    private void addButton(String text, String tooltip) {
        RoundedButton button = new RoundedButton(text, tooltip); // Use the custom RoundedButton class
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleButtonClick(text);
            }
        });
        leftPanel.add(button);
    }
    
/**
 * Method to handle button click events.
 * Calls the appropriate method based on the clicked button's text.
 *
 * @param buttonText The text of the clicked button.
 */
    
    // Method to handle button click events
    private void handleButtonClick(String buttonText) {
        switch (buttonText) {
            case "Add Parking Spot":
                addParkingSpot();
                break;
            case "Delete Parking Spot":
                deleteParkingSpot();
                break;
            case "List All Parking Spots":
                listAllSpots();
                break;
            case "Park a Car":
                parkCar();
                break;
            case "Find Car by Registration Number":
                findCarByRegistration();
                break;
            case "Remove Car":
                removeCar();
                break;
            case "Find Cars by Make":
                findCarsByMake();
                break;
            case "Reset Car Park":
                resetCarPark();
                break;
            case "Exit":
                exitProgram();
                break;
            default:
                break;
        }
    }
    
// Implement functionalities for button actions

/**
 * This method handles the addition of a new parking spot to the car park.
 * It prompts the user to enter a spot ID, validates the format, checks for duplicates,
 * and creates a new ParkingSpot object if the input is valid. It then adds the new
 * spot to the car park and updates the spot information panel.
 */
private void addParkingSpot() {
    String spotID = JOptionPane.showInputDialog("Enter spot ID (Format: uppercase letter followed by 3 digits)");
    if (spotID != null && !spotID.isEmpty()) {
        if (!isValidSpotID(spotID)) {
            JOptionPane.showMessageDialog(this, "Invalid spot ID format. Please enter a valid spot ID (Format: uppercase letter followed by 3 digits).");
            return;
        }
        
        if (carPark.findParkingSpotById(spotID) != null) {
            JOptionPane.showMessageDialog(this, "Failed to add parking spot. Spot already exists.");
            return;
        }

        ParkingSpot spot = new ParkingSpot(spotID);
        carPark.addParkingSpot(spot);
        updateSpotInfoPanel(spotID); // Update the spot info panel immediately after adding the spot
    }
}

/**
 * Checks if the provided spot ID follows the valid format of an uppercase letter
 * followed by three digits.
 *
 * @param spotID The spot ID to be validated.
 * @return true if the spot ID format is valid, false otherwise.
 */  
    // Method to check if the spot ID format is valid
    private boolean isValidSpotID(String spotID) {
        return spotID.matches("[A-Z]\\d{3}");
    }


/**
 * This method handles the deletion of a parking spot from the car park.
 * It prompts the user to enter a spot ID, and if the spot exists, it removes
 * the spot from the car park, displays a success message, and removes the
 * spot information panel. If the spot does not exist, it displays an error message.
 */
private void deleteParkingSpot() {
    String spotID = JOptionPane.showInputDialog("Enter spot ID to delete(Format: uppercase letter followed by 3 digits)");
    if (spotID != null && !spotID.isEmpty()) {
        if (carPark.deleteParkingSpot(spotID)) {
            JOptionPane.showMessageDialog(this, "Parking spot " + spotID + " deleted successfully.");
            removeSpotInfoPanel(spotID);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete parking spot " + spotID + ".");
        }
    }
}

/**
 * This method displays a list of all parking spots in the car park.
 * For each spot, it shows the spot ID, occupancy status, and if occupied,
 * it displays the registration number, make, model, and year of the parked car.
 */
private void listAllSpots() {
    List<String> spotInfoList = new ArrayList<>();

    for (ParkingSpot spot : carPark.getParkingSpots()) {
        StringBuilder spotInfo = new StringBuilder();
        spotInfo.append("Spot ID: ").append(spot.getSpotID()).append(", Status: ");

        if (spot.isOccupied()) {
            Car car = spot.getParkedCar();
            spotInfo.append("Occupied by ").append(car.getRegistrationNumber())
                    .append(", Make: ").append(car.getMake())
                    .append(", Model: ").append(car.getModel())
                    .append(", Year: ").append(car.getYear())
                    .append(", Parking time: ").append(formatParkingTime(car.getParkingTime()))
                    .append(", Parking duration: ").append(calculateParkingDuration(car.getParkingTime()));
        } else {
            spotInfo.append("Unoccupied");
        }

        spotInfoList.add(spotInfo.toString());
    }

    StringBuilder spotList = new StringBuilder("List of all parking spots:\n");
    for (String spotInfo : spotInfoList) {
        spotList.append(spotInfo).append("\n");
    }

    JOptionPane.showMessageDialog(this, spotList.toString());
}

/**
 * This method handles the parking of a car in the car park.
 * It prompts the user to enter the car's registration number, make, model, year,
 * and the spot ID where the car should be parked. It validates the input formats,
 * creates a new Car object, and attempts to park the car in the specified spot.
 * If the spot does not exist, it prompts the user to add a new spot before parking.
 * If the parking is successful, it displays a success message and updates the spot
 * information panel. If there are any issues, it displays appropriate error messages.
 */
private void parkCar() {
    String regNumber = JOptionPane.showInputDialog("Enter registration number (Format: uppercase letter followed by 4 digits):");
    if (regNumber == null) {
        return; // Exit the method if cancel is pressed
    }
    if (!regNumber.matches("[A-Z]\\d{4}")) {
        JOptionPane.showMessageDialog(this, "Invalid registration number format. Please enter a valid registration number.");
        return; // Exit the method if input is invalid
    }

    // Check if the registration ID already exists
    if (carPark.findCarByRegistration(regNumber)!= null) {
        JOptionPane.showMessageDialog(this, "Registration ID is already taken. Please enter a unique registration ID.");
        return; // Exit the method if the registration ID is already taken
    }

    String make = JOptionPane.showInputDialog("Enter car make:");
    if (make == null) {
        return; // Exit the method if cancel is pressed
    }

    String model = JOptionPane.showInputDialog("Enter car model:");
    if (model == null) {
        return; // Exit the method if cancel is pressed
    }

    Integer year = null;
    while (year == null) {
        String yearInput = JOptionPane.showInputDialog("Enter car year (between 2004 and 2024):");
        if (yearInput == null) {
            return; // Exit the method if cancel is pressed
        }
        try {
            year = Integer.parseInt(yearInput);
            if (year < 2004 || year > 2024) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid year between 2004 and 2024.");
                year = null; // reset year if it's out of range
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a numeric year.");
        }
    }

    String spotID = JOptionPane.showInputDialog("Enter spot ID (Format: uppercase letter followed by 3 digits)");
    if (spotID == null) {
        return; // Exit the method if cancel is pressed
    }
    if (!isValidSpotID(spotID)) {
        JOptionPane.showMessageDialog(this, "Invalid spot ID format.");
        return; // Exit the method if input is invalid
    }

    Car car = new Car(regNumber, make, model, year);
    ParkingSpot spot = carPark.findParkingSpotById(spotID);
    if (spot == null) {
        String addSpot = JOptionPane.showInputDialog("Spot ID does not exist. Would you like to add this spot? (yes/no)");
        if (addSpot == null ||!addSpot.equalsIgnoreCase("yes")) {
            return; // Exit the method if cancel is pressed or user chooses not to add the spot
        }
        ParkingSpot newSpot = new ParkingSpot(spotID);
        carPark.addParkingSpot(newSpot);
        // Update or create spot info panel immediately after adding the spot
        updateOrCreateSpotInfoPanel(spotID);
        JOptionPane.showMessageDialog(this, "Parking spot " + spotID + " added successfully.");
    } else if (spot.isOccupied()) {
        JOptionPane.showMessageDialog(this, "Spot " + spotID + " is already occupied.");
        return; // Exit the method if spot is occupied
    } else if (carPark.parkCar(car, spotID)) {
        JOptionPane.showMessageDialog(this, "Car parked successfully in spot " + spotID + ".");
        updateSpotInfoPanel(spotID); // Update the spot info panel after parking
    } else {
        JOptionPane.showMessageDialog(this, "Failed to park car.");
    }
}

/**
 * This method updates the existing spot information panel if it exists, or creates a new one if it doesn't.
 *
 * @param spotID The ID of the parking spot to update or create the panel for.
 */
private void updateOrCreateSpotInfoPanel(String spotID) {
    // Check if the spot information panel already exists
    Component[] components = rightPanel.getComponents();
    for (Component component : components) {
        if (component instanceof JPanel) {
            JPanel panel = (JPanel) component;
            JLabel spotLabel = (JLabel) panel.getComponent(0);
            if (spotLabel.getText().equals("Spot ID: " + spotID)) {
                // Update the existing panel
                updateSpotInfoPanel(spotID);
                return;
            }
        }
    }

    // If the panel doesn't exist, create a new one
    updateSpotInfoPanel(spotID);
}

private void updateSpotInfoPanel(String spotID) {
    ParkingSpot spot = carPark.findParkingSpotById(spotID);
    Component[] components = rightPanel.getComponents();

    // Check if a panel for the given spot ID already exists
    JPanel existingPanel = null;
    for (Component component : components) {
        if (component instanceof JPanel) {
            JPanel panel = (JPanel) component;
            JLabel spotLabel = (JLabel) panel.getComponent(0);
            if (spotLabel.getText().equals("Spot ID: " + spotID)) {
                existingPanel = panel;
                break;
            }
        }
    }

    if (existingPanel != null) {
        updatePanelContent(existingPanel, spot);
    } else {
        // Create a new panel if it doesn't exist
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add a black border

        JLabel spotLabel = new JLabel("Spot ID: " + spotID);
        panel.add(spotLabel);

        // Set color based on occupancy
        updatePanelContent(panel, spot);

        panel.setPreferredSize(new Dimension(210, 140)); // Adjust size as needed
        rightPanel.add(panel);

        // Manage layout for better visibility
        int componentCount = rightPanel.getComponentCount();
        if (componentCount % 4 == 0) {
            rightPanel.add(Box.createHorizontalStrut(10)); // Add spacing between rows
        }

        addPanelMouseListener(panel, spotID);
        
        rightPanel.revalidate();
        rightPanel.repaint();
    }
}

private void addPanelMouseListener(JPanel panel, String spotID) {
    panel.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            JPopupMenu popupMenu = new JPopupMenu();
            JMenuItem addCarItem = new JMenuItem("Add Car");
            JMenuItem removeCarItem = new JMenuItem("Remove Car");
            JMenuItem deleteSpotItem = new JMenuItem("Delete Spot");

            addCarItem.addActionListener(event -> addCarToSpot(spotID));
            removeCarItem.addActionListener(event -> removeCarFromSpot(spotID));
            deleteSpotItem.addActionListener(event -> deleteSpot(spotID));

            popupMenu.add(addCarItem);
            popupMenu.add(removeCarItem);
            popupMenu.add(deleteSpotItem);

            popupMenu.show(panel, e.getX(), e.getY());
        }
    });
}




/**
 * This method finds a car by its registration number and displays the spot ID
 * where the car is parked. If the car is not found, it displays a "Car not found" message.
 */
private void findCarByRegistration() {
    String regNumber = JOptionPane.showInputDialog("Enter car registration number:");
    ParkingSpot spot = carPark.findCarByRegistration(regNumber);
    if (spot != null) {
    Car car = spot.getParkedCar();
    JOptionPane.showMessageDialog(this, "Car found in spot " + spot.getSpotID()
            + "\nRegistration number: " + car.getRegistrationNumber()
            + "\nCar make: " + car.getMake()
            + "\nCar model: " + car.getModel()
            + "\nCar year: " + car.getYear()
            + "\nParking time: " + formatParkingTime(car.getParkingTime())
            + "\nParking duration: " + calculateParkingDuration(car.getParkingTime()));
} else {
    JOptionPane.showMessageDialog(this, "Car not found.");
}
}


/**
 * Prompts the user to enter a car registration number and removes the car from the car park.
 * Displays a success or failure message and updates the spot information panel if the car is removed.
 */
private void removeCar() {
    String regNumber = JOptionPane.showInputDialog("Enter car registration number to remove:");
    if (regNumber == null) {
        return; // Exit the method if cancel is pressed
    }

    ParkingSpot spot = carPark.findCarByRegistration(regNumber);
    if (spot != null) {
        boolean success = carPark.removeCar(regNumber);
        if (success) {
            JOptionPane.showMessageDialog(this, "Car removed successfully.");
            updateSpotPanelToEmpty(getSpotPanelBySpotId(spot.getSpotID())); // Update only the panel for the removed car
        } else {
            JOptionPane.showMessageDialog(this, "Failed to remove car.");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Car not found.");
    }
}

// Helper method to get the JPanel for the given spot ID
private JPanel getSpotPanelBySpotId(String spotID) {
    Component[] components = rightPanel.getComponents();
    for (Component component : components) {
        if (component instanceof JPanel) {
            JPanel panel = (JPanel) component;
            Component[] innerComponents = panel.getComponents();
            for (Component innerComponent : innerComponents) {
                if (innerComponent instanceof JLabel) {
                    JLabel label = (JLabel) innerComponent;
                    if (label.getText().equals("Spot ID: " + spotID)) {
                        return panel;
                    }
                }
            }
        }
    }
    return null;
}


/**
 * Removes the spot information panel for the specified parking spot ID from the GUI.
 * It iterates through the components in the right panel, finds the panel containing
 * the label with the matching spot ID, and removes that panel from the right panel.
 *
 * @param spotID The ID of the parking spot whose information panel needs to be removed.
 */
// Method to remove spot information panel
    private void removeSpotInfoPanel(String spotID) {
        Component[] components = rightPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;
                Component[] innerComponents = panel.getComponents();
                for (Component innerComponent : innerComponents) {
                    if (innerComponent instanceof JLabel) {
                        JLabel label = (JLabel) innerComponent;
                        if (label.getText().equals("Spot ID: " + spotID)) {
                            rightPanel.remove(panel);
                            rightPanel.revalidate();
                            rightPanel.repaint();
                            return;
                        }
                    }
                }
            }
        }
    }

 /**
  * Prompts the user to enter a car make and displays a list of all parking spots
  * where cars of that make are parked.
  */
    private void findCarsByMake() {
        String make = JOptionPane.showInputDialog("Enter car make:");
        List<ParkingSpot> spots = carPark.findCarsByMake(make);
        if (!spots.isEmpty()) {
            StringBuilder result = new StringBuilder("Cars found with make " + make + ":\n");
        for (ParkingSpot spot : spots) {
            result.append("Spot ID: ").append(spot.getSpotID()).append("\n");
        }
        JOptionPane.showMessageDialog(this, result.toString());
    }
        else {
        JOptionPane.showMessageDialog(this, "No cars found with make " + make + ".");
    }
}


/**
 * Resets the car park by removing all parking spots and cars.
 * Displays a success message after resetting the car park.
 */
// Method to reset the car park
// Method to reset the car park
private void resetCarPark() {
    carPark.resetCarPark();
    
    // Clear the right panel by removing all components
    rightPanel.removeAll();
    rightPanel.revalidate();
    rightPanel.repaint();
    
    JOptionPane.showMessageDialog(this, "Car park reset successfully.");
}

// Helper method to update a spot panel to show it is empty
private void updateSpotPanelToEmpty(JPanel panel) {
    panel.setBackground(new Color(76, 175, 80)); // Green for unoccupied
    Component[] components = panel.getComponents();
    for (int i = 0; i < components.length; i++) {
        if (components[i] instanceof JLabel) {
            JLabel label = (JLabel) components[i];
            if (i == 0) {
                label.setText(label.getText().split(" - ")[0]); // Reset the spot ID label to show it's unoccupied
            } else {
                panel.remove(label); // Remove additional labels
            }
        }
    }
    panel.revalidate();
    panel.repaint();
}
   
/**
 * Prompts the user to confirm exit and closes the GUI if the user confirms.
 */   
    private void exitProgram() {
    int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        dispose(); // Close the JFrame
    }
}



    

private void updatePanelContent(JPanel panel, ParkingSpot spot) {
    panel.removeAll();
    JLabel spotLabel = new JLabel("Spot ID: " + spot.getSpotID());
    panel.add(spotLabel);

    if (spot.isOccupied()) {
        Car car = spot.getParkedCar();
        panel.setBackground(new Color(244, 67, 54)); // Red for occupied
        addOrUpdateLabel(panel, "Registration number: " + car.getRegistrationNumber(), 1);
        addOrUpdateLabel(panel, "Car make: " + car.getMake(), 2);
        addOrUpdateLabel(panel, "Car model: " + car.getModel(), 3);
        addOrUpdateLabel(panel, "Car year: " + car.getYear(), 4);
        addOrUpdateLabel(panel, "Parking time: " + formatParkingTime(car.getParkingTime()), 5);
        addOrUpdateLabel(panel, "Parking duration: " + calculateParkingDuration(car.getParkingTime()), 6);
    } else {
        panel.setBackground(new Color(76, 175, 80)); // Green for unoccupied
    }
    panel.revalidate();
    panel.repaint();
}



private void addCarToSpot(String spotID) {
    // Call the method from your existing GUI to add a car to the parking spot
    parkCar();
    
}

private void removeCarFromSpot(String spotID) {
    // Call the method from your existing GUI to remove a car from the parking spot
    removeCar();
}

private void deleteSpot(String spotID) {
    // Call the method from your existing GUI to delete the parking spot
     deleteParkingSpot();
}

/**
     * Helper method to add or update JLabels in the given panel.
     * If the index is within the panel's component count, it updates the existing label with the new text.
     * Otherwise, it adds a new label with the provided text to the panel.
     *
     * @param panel The JPanel to add or update the label in.
     * @param text  The text to set for the label.
     * @param index The index of the label to update or add.
     */
// Helper method to add or update JLabels in the panel
private void addOrUpdateLabel(JPanel panel, String text, int index) {
    if (index < panel.getComponentCount()) {
        JLabel label = (JLabel) panel.getComponent(index);
        label.setText(text);
    } else {
        JLabel label = new JLabel(text);
        panel.add(label);
    }
}

// Helper method to remove JLabel if it exists in the panel
private void removeLabelIfExists(JPanel panel, int index) {
    if (index < panel.getComponentCount()) {
        panel.remove(index);
    }
}

private String formatParkingTime(long parkingTime) {
    SimpleDateFormat formatter = new SimpleDateFormat(" HH:mm:ss");
    return formatter.format(new Date(parkingTime));
}

private String calculateParkingDuration(long parkingTime) {
    long currentTime = System.currentTimeMillis();
    long duration = currentTime - parkingTime;
    long hours = duration / (1000 * 60 * 60);
    long minutes = (duration / (1000 * 60)) % 60;

    if (hours == 0 && minutes == 0) {
        return "00:00"; // Handle the case when both hours and minutes are 0
    } else {
        return String.format("%02d:%02d", hours, minutes);
    }
}

/**
 * The main entry point of the application.
 * Creates an instance of the CarParkGUI class and makes it visible on the screen.
 *
 * @param args The command-line arguments (not used in this application).
 */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CarParkGUI gui = new CarParkGUI();
                gui.setVisible(true);
            }
        });
 
    
    }
}