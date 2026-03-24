import java.util.*;
class BookingRequest {
    private String guestName;
    private int roomsRequested;

    public BookingRequest(String guestName, int roomsRequested) {
        this.guestName = guestName;
        this.roomsRequested = roomsRequested;
    }

    public String getGuestName() {
        return guestName;
    }

    public int getRoomsRequested() {
        return roomsRequested;
    }
}

class BookingQueue {
    private Queue<BookingRequest> queue = new LinkedList<>();

    public synchronized void addRequest(BookingRequest request) {
        queue.add(request);
        System.out.println(request.getGuestName() + " added request for "
                + request.getRoomsRequested() + " room(s)");
    }

    public synchronized BookingRequest getRequest() {
        return queue.poll();
    }
}

class HotelInventory {
    private int availableRooms;

    public HotelInventory(int rooms) {
        this.availableRooms = rooms;
    }

    public synchronized boolean allocateRooms(String guestName, int requestedRooms) {
        if (availableRooms >= requestedRooms) {
            System.out.println("Allocating " + requestedRooms + " room(s) to " + guestName);
            availableRooms -= requestedRooms;

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Booking confirmed for " + guestName +
                    ". Remaining rooms: " + availableRooms);
            return true;
        } else {
            System.out.println("Booking failed for " + guestName +
                    " (Not enough rooms). Remaining: " + availableRooms);
            return false;
        }
    }
}
class BookingProcessor extends Thread {
    private BookingQueue queue;
    private HotelInventory inventory;

    public BookingProcessor(BookingQueue queue, HotelInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        while (true) {
            BookingRequest request;

            // synchronized access to queue
            synchronized (queue) {
                request = queue.getRequest();
            }

            if (request == null) {
                break;
            }

            inventory.allocateRooms(
                    request.getGuestName(),
                    request.getRoomsRequested()
            );
        }
    }
}

public class BOOKMYSTAYAPP {
    public static void main(String[] args) {

        BookingQueue queue = new BookingQueue();
        HotelInventory inventory = new HotelInventory(5); // Total rooms

        queue.addRequest(new BookingRequest("Alice", 2));
        queue.addRequest(new BookingRequest("Bob", 2));
        queue.addRequest(new BookingRequest("Charlie", 2));
        queue.addRequest(new BookingRequest("David", 1));
        queue.addRequest(new BookingRequest("Eve", 1));

        BookingProcessor t1 = new BookingProcessor(queue, inventory);
        BookingProcessor t2 = new BookingProcessor(queue, inventory);
        BookingProcessor t3 = new BookingProcessor(queue, inventory);
        t1.start();
        t2.start();
        t3.start();
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("All booking requests processed safely.");
    }
}