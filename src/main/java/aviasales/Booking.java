package aviasales;

import java.util.List;

public class Booking {
    private List<String> departureAirports;
    private List<String> destinationAirports;
    private int adultsAmount;
    private int kidsAmount;
    private String serviceClass;
    private boolean hasBuggage;
    private int transfersAmount;
    private int[] durations;
    private int numberOfRoutes;

    public int getNumberOfRoutes() {
        return numberOfRoutes;
    }

    public Booking withNumberOfRoutes(int numberOfRoutes) {
        this.numberOfRoutes = numberOfRoutes;
        return this;
    }

    public int[] getDurations() {
        return durations;
    }

    public Booking withDurations(int[] durations) {
        this.durations = durations;
        return this;
    }

    public boolean hasBuggage() {
        return hasBuggage;
    }

    public Booking withBuggage(boolean hasBuggage) {
        this.hasBuggage = hasBuggage;
        return this;
    }

    public int getTransfersAmount() {
        return transfersAmount;
    }

    public Booking withTransfersAmount(int transfersAmount) {
        this.transfersAmount = transfersAmount;
        return this;
    }

    public List<String> getDestinationAirports() {
        return destinationAirports;
    }

    public Booking withDestinationAirports(List<String> destinationAirports) {
        this.destinationAirports = destinationAirports;
        return this;
    }

    public List<String> getDepartureAirports() {
        return departureAirports;
    }

    public Booking withDepartureAirports(List<String> departureAirports) {
        this.departureAirports = departureAirports;
        return this;
    }

    public int getAdultsAmount() {
        return adultsAmount;
    }

    public Booking withAdultsAmount(int adultsAmount) {
        this.adultsAmount = adultsAmount;
        return this;
    }

    public int getKidsAmount() {
        return kidsAmount;
    }

    public Booking withKidsAmount(int kidsAmount) {
        this.kidsAmount = kidsAmount;
        return this;
    }

    public String getServiceClass() {
        return serviceClass;
    }

    public Booking withServiceClass(String comfortLevel) {
        this.serviceClass = comfortLevel;
        return this;
    }
}
