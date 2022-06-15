package project.airline.aircraft.concrete;

import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

public class WidebodyPassengerAircraft extends PassengerAircraft {

    public WidebodyPassengerAircraft(Airport currentAirport, double operationFee, int ID) {
        super(currentAirport, 135000, 250000, 140000, 450, 3.0, 0.7, operationFee, ID);
    }

    public double getFuelConsumption(double distance) {
        return bathtubCoefficient(distance/14000.0) * distance + weight * 0.1 / fuelWeight;
    }

    public double getFlightCostConstant(){
        return 0.15;
    }
}
