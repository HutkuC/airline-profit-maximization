package project.airline.aircraft.concrete;

import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

public class JetPassengerAircraft extends PassengerAircraft {

    public JetPassengerAircraft(Airport currentAirport, double operationFee, int ID) {
        super(currentAirport, 10000, 18000, 10000, 30, 0.7, 5.0, operationFee, ID);
    }

    public double getFuelConsumption(double distance) {
        return bathtubCoefficient(distance/5000.0) * distance + weight * 0.1 / fuelWeight;
    }

    public double getFlightCostConstant(){
        return 0.08;
    }
}
