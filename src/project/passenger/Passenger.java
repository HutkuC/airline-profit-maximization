package project.passenger;

import project.airport.Airport;

import java.util.ArrayList;

public abstract class Passenger {

    private final long ID;
    private final double weight;
    private final int baggageCount;
    private ArrayList<Airport> destinations;
    private Airport currentAirport;
    private double seatMultiplier;
    private double connectionMultiplier;
    private int seatType;
    private int destinationIndex;

    public Passenger(Airport airport, long ID, double weight, int baggageCount) {
        this.currentAirport = airport;
        this.ID = ID;
        this.weight = weight;
        this.baggageCount = baggageCount;
        this.destinationIndex = 0;
        this.connectionMultiplier = 1;
        this.seatMultiplier = 1;
        destinations = new ArrayList<>();
    }

    public long getID() { return ID; }

    public double getWeight() { return weight; }

    public int getBaggageCount() { return baggageCount; }

    public Airport getCurrentAirport() { return currentAirport; }

    public double getSeatMultiplier() { return seatMultiplier; }

    public double getConnectionMultiplier() { return connectionMultiplier; }

    public int getSeatType() { return seatType; }

    public void setSeatType(int seatType) { this.seatType = seatType; }

    public void setConnectionMultiplier(double connectionMultiplier) { this.connectionMultiplier = connectionMultiplier; }

    public void multiplyConnectionMultiplier(double multiplier) { this.connectionMultiplier *= multiplier; }

    public void setSeatMultiplier(double seatMultiplier) { this.seatMultiplier = seatMultiplier; }

    public void multiplySeatMultiplier(double multiplier) { this.seatMultiplier *= multiplier; }

    public void addDestination(Airport airport) {
        destinations.add(airport);
    }

    public ArrayList<Airport> getDestinations(){ return destinations; }

    public int getDestinationIndex(){ return destinationIndex; }

    public void board(int seatType){
        if(seatType == 1)  multiplySeatMultiplier(0.6);
        else if(seatType == 2) multiplySeatMultiplier(1.2);
        else if(seatType == 3) multiplySeatMultiplier(3.2);
        setSeatType(seatType);
    }

    public double disembark(Airport airport, double airCraftTypeMultiplier){
        for(int i=destinationIndex; i<destinations.size(); i++){
            if(destinations.get(i).getID() == airport.getID()){
                destinationIndex = i + 1;
                double ticketPrice = calculateTicketPrice(airport, airCraftTypeMultiplier);
                currentAirport = airport;
                setConnectionMultiplier(1);
                setSeatMultiplier(1);
                return ticketPrice;
            }
        }
        return 0;
    }

    abstract double calculateTicketPrice(Airport airport, double airCraftTypeMultiplier);
}
