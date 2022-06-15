package project.airport;

import project.airline.aircraft.Aircraft;
import project.passenger.Passenger;

import java.util.HashSet;
import java.util.Set;

public abstract class Airport{
    private final long ID;
    private final double x, y;
    protected double fuelCost;
    protected double operationFee;
    protected int aircraftCapacity;
    protected int aircraftCount = 0;
    protected Set<Aircraft> aircrafts;
    Set<Passenger> passengers;

    public Airport(long ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.fuelCost = fuelCost;
        this.operationFee = operationFee;
        this.aircraftCapacity = aircraftCapacity;
        aircrafts = new HashSet<>();
        passengers = new HashSet<>();
    }

    public long getID() { return ID; }

    public double getX() { return x; }

    public double getY() { return y; }

    public double getFuelCost() { return fuelCost; }

    public double getOperationFee() { return operationFee; }

    public int getAircraftCapacity() { return aircraftCapacity; }

    public int getAircraftCount() { return aircraftCount; }

    public Set<Passenger> getPassengers(){ return passengers; }

    public Set<Aircraft> getAircrafts(){ return aircrafts; }

    public double getDistance(Airport toAirport){
        return Math.sqrt(Math.pow(toAirport.getX() - x, 2) + Math.pow(toAirport.getY() - y, 2));
    }

    public boolean canLand(){ return aircraftCount < aircraftCapacity; }

    public boolean isFull(){ return aircraftCount == aircraftCapacity; }

    public void addAircraft(Aircraft aircraft){
        if(canLand()){
            aircrafts.add(aircraft);
            aircraftCount++;
        }
    }


    double getAircraftRatio(){
        return (double) aircraftCount / (double) aircraftCapacity;
    }

    public abstract double getDepartureConstant();

    public abstract double getLandingConstant();

    public double getDepartureCost(Aircraft aircraft){
        double fullnessCoefficient = 0.6 * Math.exp(getAircraftRatio());
        double aircraftWeightRatio = aircraft.getWeightRatio();
        double departureConstant = getDepartureConstant();

        System.out.println("aircraftWeightRatio = " + aircraftWeightRatio);

        return fullnessCoefficient * aircraftWeightRatio * departureConstant * operationFee;
    }

    public double getLandingCost(Aircraft aircraft){
        double fullnessCoefficient = 0.6 * Math.exp(getAircraftRatio());
        double aircraftWeightRatio = aircraft.getWeightRatio();
        double landingConstant = getLandingConstant();

        return fullnessCoefficient * aircraftWeightRatio * landingConstant * operationFee;
    }

    public double departAircraft(Aircraft aircraft){

        double departureCost = getDepartureCost(aircraft);

        aircrafts.remove(aircraft);
        aircraftCount--;

        return departureCost;
    }

    public double landAircraft(Aircraft aircraft){

        double landingCost = getLandingCost(aircraft);

        aircrafts.add(aircraft);
        aircraftCount++;

        return landingCost;
    }

    public void addPassenger(Passenger passenger){ passengers.add(passenger); }
}
