import java.util.*;
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}
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

    public void validateRoomType(String roomType) throws InvalidBookingException {
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid Room Type: " + roomType);
        }
    }

    public void allocateRoom(String roomType) throws InvalidBookingException {

        int available = inventory.get(roomType);

        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }

        inventory.put(roomType, available - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
class BookingProcessor {

    private InventoryService inventoryService;

    public BookingProcessor(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void processBooking(Reservation reservation) {

        try {

            if (reservation.getGuestName() == null || reservation.getGuestName().isEmpty()) {
                throw new InvalidBookingException("Guest name cannot be empty.");
            }

            inventoryService.validateRoomType(reservation.getRoomType());

            inventoryService.allocateRoom(reservation.getRoomType());

            System.out.println("Booking confirmed for " +
                    reservation.getGuestName() +
                    " (Room Type: " + reservation.getRoomType() + ")");

        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}
public class BOOKMYSTAYAPP {

    public static void main(String[] args) {

        InventoryService inventory = new InventoryService();

        inventory.addRoomType("Deluxe", 2);
        inventory.addRoomType("Suite", 1);

        BookingProcessor processor = new BookingProcessor(inventory);

        Reservation r1 = new Reservation("Amit", "Deluxe");
        Reservation r2 = new Reservation("", "Suite");          // invalid guest name
        Reservation r3 = new Reservation("Ravi", "Luxury");     // invalid room type
        Reservation r4 = new Reservation("Neha", "Suite");
        Reservation r5 = new Reservation("Rahul", "Suite");     // no rooms left

        processor.processBooking(r1);
        processor.processBooking(r2);
        processor.processBooking(r3);
        processor.processBooking(r4);
        processor.processBooking(r5);

        inventory.displayInventory();
    }
}