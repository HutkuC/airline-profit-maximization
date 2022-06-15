package project.passenger;

import project.airport.Airport;
import project.airport.HubAirport;
import project.airport.MajorAirport;

public class FirstClassPassenger extends Passenger{

    public FirstClassPassenger(Airport airport, long ID, double weight, int baggageCount) {
        super(airport, ID, weight, baggageCount);
    }

    public double calculateTicketPrice(Airport toAirport, double airCraftTypeMultiplier) {
        double airportMultiplier;
        if(getCurrentAirport() instanceof HubAirport){
            if(toAirport instanceof HubAirport) airportMultiplier = 0.5;
            else if(toAirport instanceof MajorAirport) airportMultiplier = 0.7;
            else airportMultiplier = 1.0;
        }
        else if(getCurrentAirport() instanceof MajorAirport){
            if(toAirport instanceof HubAirport) airportMultiplier = 0.6;
            else if(toAirport instanceof MajorAirport) airportMultiplier = 0.8;
            else airportMultiplier = 1.8;
        }
        else{
            if(toAirport instanceof HubAirport) airportMultiplier = 0.9;
            else if(toAirport instanceof MajorAirport) airportMultiplier = 1.6;
            else airportMultiplier = 3.0;
        }

        double passengerMultiplier = 3.2;

        double distance = getCurrentAirport().getDistance(toAirport);

        double seatMultiplier = getSeatMultiplier();

        double connectionMultiplier = getConnectionMultiplier();

        double ticketPrice = distance * airCraftTypeMultiplier * airportMultiplier *
                                passengerMultiplier * seatMultiplier * connectionMultiplier;

        ticketPrice *= Math.pow(1.05, getBaggageCount());

        return ticketPrice;
    }}
