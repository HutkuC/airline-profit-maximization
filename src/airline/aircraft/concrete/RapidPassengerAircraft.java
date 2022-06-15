package project.airline.aircraft.concrete;

import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

public class RapidPassengerAircraft extends PassengerAircraft {

    public RapidPassengerAircraft(Airport currentAirport, double operationFee, int ID) {
        super(currentAirport, 80000, 185000, 120000, 120, 5.3, 1.9, operationFee, ID);
    }

    public double getFuelConsumption(double distance) {
        return bathtubCoefficient(distance/7000.0) * distance + weight * 0.1 / fuelWeight;
    }

    public double getFlightCostConstant(){
        return 0.2;
    }
}
