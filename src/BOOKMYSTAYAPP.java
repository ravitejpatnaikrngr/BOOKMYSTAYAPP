import java.util.*;
class Reservation {
    private String guestName;
    private String roomType;
    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}
class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();
    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }
    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }
    public void decrementRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " rooms available: " + entry.getValue());
        }
    }
}
class BookingService {
    private Queue<Reservation> requestQueue;
    private InventoryService inventoryService;
    private Set<String> allocatedRoomIds = new HashSet<>();
    private Map<String, Set<String>> roomAllocationMap = new HashMap<>();

    private int roomCounter = 1;

    public BookingService(Queue<Reservation> requestQueue, InventoryService inventoryService) {
        this.requestQueue = requestQueue;
        this.inventoryService = inventoryService;
    }

    public void processBookings() {

        while (!requestQueue.isEmpty()) {

            Reservation reservation = requestQueue.poll();
            String roomType = reservation.getRoomType();
            System.out.println("\nProcessing booking for " + reservation.getGuestName());
            if (inventoryService.isAvailable(roomType)) {
                String roomId = roomType.substring(0, 2).toUpperCase() + roomCounter++;
                if (!allocatedRoomIds.contains(roomId)) {

                    allocatedRoomIds.add(roomId);

                    roomAllocationMap
                            .computeIfAbsent(roomType, k -> new HashSet<>())
                            .add(roomId);
                    inventoryService.decrementRoom(roomType);

                    System.out.println("Reservation Confirmed!");
                    System.out.println("Guest: " + reservation.getGuestName());
                    System.out.println("Room Type: " + roomType);
                    System.out.println("Assigned Room ID: " + roomId);

                }

            } else {
                System.out.println("Booking Failed: No " + roomType + " rooms available.");
            }
        }
    }
}
public class BOOKMYSTAYAPP {
    public static void main(String[] args) {
        Queue<Reservation> bookingQueue = new LinkedList<>();

        bookingQueue.add(new Reservation("Amit", "Deluxe"));
        bookingQueue.add(new Reservation("Ravi", "Suite"));
        bookingQueue.add(new Reservation("Neha", "Deluxe"));
        InventoryService inventory = new InventoryService();
        inventory.addRoomType("Deluxe", 2);
        inventory.addRoomType("Suite", 1);
        inventory.displayInventory();
        BookingService bookingService = new BookingService(bookingQueue, inventory);
        bookingService.processBookings();

        inventory.displayInventory();
    }
}