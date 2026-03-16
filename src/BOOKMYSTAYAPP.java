import java.util.*;
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
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
    public void displayReservation() {
        System.out.println("Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room Type: " + roomType);
    }
}
class BookingHistory {
    private List<Reservation> history = new ArrayList<>();
    public void addReservation(Reservation reservation) {
        history.add(reservation);
        System.out.println("Reservation " + reservation.getReservationId() + " added to history.");
    }
    public List<Reservation> getReservations() {
        return history;
    }
}
class BookingReportService {
    public void displayAllBookings(BookingHistory bookingHistory) {
        List<Reservation> reservations = bookingHistory.getReservations();
        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }
        System.out.println("\nBooking History Report:");
        for (Reservation r : reservations) {
            r.displayReservation();
        }
    }
    public void generateSummary(BookingHistory bookingHistory) {
        List<Reservation> reservations = bookingHistory.getReservations();
        Map<String, Integer> roomTypeCount = new HashMap<>();
        for (Reservation r : reservations) {
            roomTypeCount.put(
                    r.getRoomType(),
                    roomTypeCount.getOrDefault(r.getRoomType(), 0) + 1
            );
        }
        System.out.println("\nBooking Summary Report:");

        for (Map.Entry<String, Integer> entry : roomTypeCount.entrySet()) {
            System.out.println(entry.getKey() + " Rooms Booked: " + entry.getValue());
        }
    }
}
public class BOOKMYSTAYAPP {
    public static void main(String[] args) {
        BookingHistory bookingHistory = new BookingHistory();
        BookingReportService reportService = new BookingReportService();
        Reservation r1 = new Reservation("RES101", "Amit", "Deluxe");
        Reservation r2 = new Reservation("RES102", "Ravi", "Suite");
        Reservation r3 = new Reservation("RES103", "Neha", "Deluxe");
        bookingHistory.addReservation(r1);
        bookingHistory.addReservation(r2);
        bookingHistory.addReservation(r3);
        reportService.displayAllBookings(bookingHistory);
        reportService.generateSummary(bookingHistory);
    }
}