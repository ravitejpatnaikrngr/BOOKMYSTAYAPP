import java.util.*;

// Room class representing room details
class Room {
    private String roomType;
    private double price;
    private String amenities;

    public Room(String roomType, double price, String amenities) {
        this.roomType = roomType;
        this.price = price;
        this.amenities = amenities;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    public String getAmenities() {
        return amenities;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Price: $" + price);
        System.out.println("Amenities: " + amenities);
        System.out.println("---------------------------");
    }
}

// Inventory class holding the room availability
class Inventory {
    private Map<Room, Integer> roomAvailability;

    public Inventory() {
        roomAvailability = new HashMap<>();
    }

    // Add a room type with initial availability
    public void addRoom(Room room, int count) {
        roomAvailability.put(room, count);
    }

    // Get a read-only copy of availability
    public Map<Room, Integer> getAvailability() {
        return Collections.unmodifiableMap(roomAvailability);
    }
}

// SearchService for read-only room search
class SearchService {
    private Inventory inventory;

    public SearchService(Inventory inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms() {
        Map<Room, Integer> availability = inventory.getAvailability();

        boolean roomsFound = false;
        System.out.println("Available Rooms:");
        System.out.println("================");

        for (Map.Entry<Room, Integer> entry : availability.entrySet()) {
            Room room = entry.getKey();
            int count = entry.getValue();

            if (count > 0) { // Only show rooms with availability
                room.displayDetails();
                roomsFound = true;
            }
        }

        if (!roomsFound) {
            System.out.println("No rooms available at the moment.");
        }
    }
}

// Main class to run Use Case 4
public class BOOKMYSTAYAPP {
    public static void main(String[] args) {
        // Create rooms
        Room deluxeRoom = new Room("Deluxe", 1200.0, "WiFi, TV, AC");
        Room suiteRoom = new Room("Suite", 2500.0, "WiFi, TV, AC, Mini Bar");
        Room standardRoom = new Room("Standard", 800.0, "WiFi, TV");

        // Setup inventory
        Inventory inventory = new Inventory();
        inventory.addRoom(deluxeRoom, 5);
        inventory.addRoom(suiteRoom, 0); // unavailable
        inventory.addRoom(standardRoom, 10);

        // Search available rooms
        SearchService searchService = new SearchService(inventory);
        searchService.searchAvailableRooms();
    }
}