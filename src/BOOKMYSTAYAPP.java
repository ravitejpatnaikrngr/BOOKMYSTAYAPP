import java.io.*;
import java.util.*;
class Booking implements Serializable {
    private static final long serialVersionUID = 1L;

    private String guestName;
    private int roomsBooked;

    public Booking(String guestName, int roomsBooked) {
        this.guestName = guestName;
        this.roomsBooked = roomsBooked;
    }

    public String getGuestName() {
        return guestName;
    }

    public int getRoomsBooked() {
        return roomsBooked;
    }

    @Override
    public String toString() {
        return guestName + " booked " + roomsBooked + " room(s)";
    }
}

class HotelInventory implements Serializable {
    private static final long serialVersionUID = 1L;

    private int availableRooms;

    public HotelInventory(int rooms) {
        this.availableRooms = rooms;
    }

    public int getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(int rooms) {
        this.availableRooms = rooms;
    }
}

class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Booking> bookings;
    private HotelInventory inventory;

    public SystemState(List<Booking> bookings, HotelInventory inventory) {
        this.bookings = bookings;
        this.inventory = inventory;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public HotelInventory getInventory() {
        return inventory;
    }
}

class PersistenceService {
    private static final String FILE_NAME = "hotel_state.ser";

    public static void saveState(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving system state: " + e.getMessage());
        }
    }

    public static SystemState loadState() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("No previous state found. Starting fresh.");
            return null;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) ois.readObject();
            System.out.println("System state restored successfully.");
            return state;

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load state. Starting with default values.");
            return null;
        }
    }
}

public class BOOKMYSTAYAPP {

    public static void main(String[] args) {

        List<Booking> bookingList;
        HotelInventory inventory;

        SystemState restoredState = PersistenceService.loadState();

        if (restoredState != null) {
            bookingList = restoredState.getBookings();
            inventory = restoredState.getInventory();
        } else {
            bookingList = new ArrayList<>();
            inventory = new HotelInventory(5);
        }

        System.out.println("\nProcessing new bookings...\n");

        addBooking("Alice", 2, bookingList, inventory);
        addBooking("Bob", 1, bookingList, inventory);
        addBooking("Charlie", 3, bookingList, inventory);

        System.out.println("\nCurrent Bookings:");
        for (Booking b : bookingList) {
            System.out.println(b);
        }

        System.out.println("Available Rooms: " + inventory.getAvailableRooms());

        SystemState currentState = new SystemState(bookingList, inventory);
        PersistenceService.saveState(currentState);

        System.out.println("\nSystem shutdown complete.");
    }
    private static void addBooking(String name, int rooms,
                                   List<Booking> bookingList,
                                   HotelInventory inventory) {

        if (inventory.getAvailableRooms() >= rooms) {
            bookingList.add(new Booking(name, rooms));
            inventory.setAvailableRooms(
                    inventory.getAvailableRooms() - rooms
            );
            System.out.println("Booking confirmed for " + name);
        } else {
            System.out.println("Booking failed for " + name +
                    " (Insufficient rooms)");
        }
    }
}