
abstract class Room {

    protected String roomType;
    protected int numberOfBeds;
    protected double pricePerNight;

    public Room(String roomType, int numberOfBeds, double pricePerNight) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.pricePerNight = pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Number of Beds: " + numberOfBeds);
        System.out.println("Price per Night: $" + pricePerNight);
    }
}

// Single Room
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 100.0);
    }
}

// Double Room
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 180.0);
    }
}

// Suite Room
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 350.0);
    }
}

// Main Application
public class BOOKMYSTAYAPP {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("        BOOK MY STAY APP         ");
        System.out.println("           Version 2.1           ");
        System.out.println("=================================");

        // Create room objects
        Room single = new SingleRoom();
        Room doubleroom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println("\n--- Room Details & Availability ---\n");

        single.displayRoomDetails();
        System.out.println("Available Rooms: " + singleAvailable);
        System.out.println();

        doubleroom.displayRoomDetails();
        System.out.println("Available Rooms: " + doubleAvailable);
        System.out.println();

        suite.displayRoomDetails();
        System.out.println("Available Rooms: " + suiteAvailable);
    }
}