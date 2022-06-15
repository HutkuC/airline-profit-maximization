package project.airline.aircraft.concrete;

import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

public class PropPassengerAircraft extends PassengerAircraft {
    public PropPassengerAircraft(Airport currentAirport, double operationFee, int ID) {
        super(currentAirport, 14000, 23000, 6000, 60, 0.6, 0.9, operationFee, ID);
    }

    public double getFuelConsumption(double distance) {
        return bathtubCoefficient(distance/2000.0) * distance + weight * 0.08 / fuelWeight;
    }

    public double getFlightCostConstant(){
        return 0.1;
    }
}
