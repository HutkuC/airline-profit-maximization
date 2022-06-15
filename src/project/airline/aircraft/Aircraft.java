package project.airline.aircraft;

import project.airport.Airport;
import project.passenger.Passenger;

import static java.lang.Math.min;

public abstract class Aircraft {
    protected Airport currentAirport;
    protected int ID;
    protected double weight;
    protected double maxWeight;
    protected double fuelWeight = 0.7;
    protected double fuel;
    protected double fuelCapacity;
    protected double fuelConsumption;
    protected double aircraftTypeMultiplier;
    protected double operationFee;

    public Aircraft(Airport currentAirport, double weight, double maxWeight, double fuelCapacity, double fuelConsumption, double aircraftTypeMultiplier, double operationFee, int ID) {
        this.currentAirport = currentAirport;
        this.weight = weight;
        this.maxWeight = maxWeight;
        this.fuel = 0;
        this.fuelCapacity = fuelCapacity;
        this.fuelConsumption = fuelConsumption;
        this.aircraftTypeMultiplier = aircraftTypeMultiplier;
        this.operationFee = operationFee;
        this.ID = ID;
    }

    public double getWeight() { return weight; }

    public double getMaxWeight() { return maxWeight; }

    public double getFuel() { return fuel; }

    public void setFuel(double amount) {
        weight += (amount - this.fuel)*0.7;
        this.fuel = amount;
    }

    public double getFuelCapacity() { return fuelCapacity; }

    public Airport getCurrentAirport() { return currentAirport; }

    public double getOperationFee() { return operationFee; }

    public double getAircraftTypeMultiplier() { return aircraftTypeMultiplier; }

    public int getID() { return ID; }

    public abstract int getEconomySeats();

    public double bathtubCoefficient(double distanceRatio){
        return 25.9324 * Math.pow(distanceRatio, 4) - 50.5633 * Math.pow(distanceRatio, 3) + 35.0554 * Math.pow(distanceRatio, 2) - 9.90346 * distanceRatio + 1.97413;
    }

    public boolean canFly(Airport toAirport){
        if(toAirport.isFull()) return false;
        double distance = currentAirport.getDistance(toAirport);
        double fuelConsumption = getFuelConsumption(distance);
        return hasFuel(fuelConsumption);
    }

    public abstract double getFuelConsumption(double distance);

    public abstract double getFullness();

    public abstract double getFlightCostConstant();

    public abstract double loadPassenger(Passenger passenger);

    public abstract double unloadPassenger(Passenger passenger);

    public abstract void setSeats(int economy, int business, int firstClass);

    public abstract void setRemainingEconomy();

    public double fly(Airport toAirport) {
        double consumption = getFuelConsumption(currentAirport.getDistance(toAirport));
        setFuel(fuel-consumption);
        double flightOperationCost = currentAirport.getDistance(toAirport) * getFullness() * getFlightCostConstant();
        double departureCost = currentAirport.departAircraft(this);
        double landingCost = toAirport.landAircraft(this);
        currentAirport = toAirport;
        return departureCost + flightOperationCost + landingCost;
    }

    public double addFuel(double amount) {
        if(fuel + amount <= fuelCapacity && amount * fuelWeight + weight <= maxWeight) {
            fuel += amount;
            this.weight += amount * fuelWeight;
            return amount * currentAirport.getFuelCost();
        }
        return 0;
    }

    public double fillUp() {
        double amount = Double.min(fuelCapacity - fuel, (maxWeight-weight)/fuelWeight);
        return addFuel(amount);
    }

    public boolean hasFuel(double amount) {
        if (fuel >= amount)
            return true;
        return false;
    }

    public double getWeightRatio() {
        return this.weight/this.maxWeight;
    }


}
