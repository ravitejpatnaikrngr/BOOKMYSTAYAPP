import java.util.*;

class Service {
    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    public void displayService() {
        System.out.println(serviceName + " - $" + cost);
    }
}
class AddOnServiceManager {
    private Map<String, List<Service>> reservationServices = new HashMap<>();
    public void addService(String reservationId, Service service) {

        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println("Service '" + service.getServiceName() +
                "' added to Reservation " + reservationId);
    }
    public void displayServices(String reservationId) {

        List<Service> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected for reservation " + reservationId);
            return;
        }

        System.out.println("\nServices for Reservation " + reservationId + ":");

        for (Service s : services) {
            s.displayService();
        }
    }
    public double calculateTotalCost(String reservationId) {

        List<Service> services = reservationServices.get(reservationId);

        if (services == null)
            return 0;

        double total = 0;

        for (Service s : services) {
            total += s.getCost();
        }

        return total;
    }
}
public class BOOKMYSTAYAPP {
    public static void main(String[] args) {
        AddOnServiceManager manager = new AddOnServiceManager();
        String reservation1 = "RES101";
        String reservation2 = "RES102";
        Service breakfast = new Service("Breakfast", 20);
        Service airportPickup = new Service("Airport Pickup", 50);
        Service spa = new Service("Spa Access", 80);
        manager.addService(reservation1, breakfast);
        manager.addService(reservation1, spa);
        manager.addService(reservation2, airportPickup);
        manager.displayServices(reservation1);
        manager.displayServices(reservation2);
        System.out.println("\nTotal Add-On Cost for " + reservation1 + ": $" +
                manager.calculateTotalCost(reservation1));

        System.out.println("Total Add-On Cost for " + reservation2 + ": $" +
                manager.calculateTotalCost(reservation2));
    }
}