import java.util.*;
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;
    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }
    public String getReservationId() {
        return reservationId;
    }
    public String getGuestName() {
        return guestName;
    }
    public String getRoomType() {
        return roomType;
    }
    public String getRoomId() {
        return roomId;
    }
    public void display() {
        System.out.println("Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room Type: " + roomType +
                " | Room ID: " + roomId);
    }
}
class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();
    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }
    public void incrementRoom(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }
    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
class CancellationService {
    private Map<String, Reservation> confirmedBookings;
    private Stack<String> rollbackStack;
    private InventoryService inventoryService;
    public CancellationService(Map<String, Reservation> confirmedBookings,
                               InventoryService inventoryService) {
        this.confirmedBookings = confirmedBookings;
        this.inventoryService = inventoryService;
        this.rollbackStack = new Stack<>();
    }
    public void cancelBooking(String reservationId) {
        if (!confirmedBookings.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Reservation not found.");
            return;
        }
        Reservation reservation = confirmedBookings.remove(reservationId);
        rollbackStack.push(reservation.getRoomId());
        inventoryService.incrementRoom(reservation.getRoomType());
        System.out.println("Booking Cancelled Successfully:");
        reservation.display();
        System.out.println("Room ID released back to inventory: " + reservation.getRoomId());
    }
    public void showRollbackHistory() {
        System.out.println("\nRecently Released Room IDs (Rollback Stack):");
        if (rollbackStack.isEmpty()) {
            System.out.println("No cancellations recorded.");
            return;
        }
        for (String roomId : rollbackStack) {
            System.out.println(roomId);
        }
    }
}
public class BOOKMYSTAYAPP {
    public static void main(String[] args) {
        InventoryService inventory = new InventoryService();
        inventory.addRoomType("Deluxe", 0);
        inventory.addRoomType("Suite", 0);
        Map<String, Reservation> confirmedBookings = new HashMap<>();
        Reservation r1 = new Reservation("RES101", "Amit", "Deluxe", "DE101");
        Reservation r2 = new Reservation("RES102", "Ravi", "Suite", "SU201");
        confirmedBookings.put(r1.getReservationId(), r1);
        confirmedBookings.put(r2.getReservationId(), r2);
        CancellationService cancellationService =
                new CancellationService(confirmedBookings, inventory);
        cancellationService.cancelBooking("RES101");
        cancellationService.cancelBooking("RES999");
        cancellationService.showRollbackHistory();
        inventory.displayInventory();
    }
}