/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ABUBAKAR
 */
import java.util.ArrayList;
import java.util.List;

// Data Layer: Transport Option
class TransportOption {
    private String mode;
    private double cost;
    private boolean flexibleSchedule;

    public TransportOption(String mode, double cost, boolean flexibleSchedule) {
        this.mode = mode;
        this.cost = cost;
        this.flexibleSchedule = flexibleSchedule;
    }

    public boolean isFlexibleSchedule() {
        return flexibleSchedule;
    }

    public String getMode() {
        return mode;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Mode: " + mode + ", Cost: $" + cost + ", Flexible Schedule: " + flexibleSchedule;
    }
}

// Observer Interface
interface Observer {
    void update(String message);
}

// Concrete Observer: Friend
class Friend implements Observer {
    private String name;

    public Friend(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println(name + " received notification: " + message);
    }
}

// Subject Interface
interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(String message);
}

// Service Layer: Transport Service
class TransportService implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private List<TransportOption> transportOptions = new ArrayList<>();

    public void addTransportOption(TransportOption option) {
        transportOptions.add(option);
    }

    public List<TransportOption> filterTransport(boolean flexibleOnly) {
        List<TransportOption> filtered = new ArrayList<>();
        for (TransportOption option : transportOptions) {
            if (!flexibleOnly || option.isFlexibleSchedule()) {
                filtered.add(option);
            }
        }
        return filtered;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public void ownConvinceTransport(String mode, double budget, boolean flexibleSchedule) {
        for (TransportOption option : filterTransport(flexibleSchedule)) {
            if (option.getMode().equalsIgnoreCase(mode) && option.getCost() <= budget) {
                notifyObservers("Selected Transport: " + option);
                return;
            }
        }
        notifyObservers("No suitable transport option found.");
    }
}

// Presentation Layer: Main Application
public class MainApp {
    public static void main(String[] args) {
        TransportService service = new TransportService();

        // Add transport options
        service.addTransportOption(new TransportOption("Car", 50.0, true));
        service.addTransportOption(new TransportOption("Bike", 20.0, false));
        service.addTransportOption(new TransportOption("Bus", 10.0, true));

        // Add friends as observers
        Friend alice = new Friend("Alice");
        Friend bob = new Friend("Bob");
        service.addObserver(alice);
        service.addObserver(bob);

        // User selects transport
        System.out.println("Processing transport selection...");
        service.ownConvinceTransport("Car", 60.0, true);

        // Display filtered transport options
        System.out.println("\nAvailable transport options:");
        for (TransportOption option : service.filterTransport(true)) {
            System.out.println(option);
        }
    }
}
