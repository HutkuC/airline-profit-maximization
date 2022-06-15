package project.airport;

public class HubAirport extends Airport{

    public HubAirport(long ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
        super(ID, x, y, fuelCost, operationFee, aircraftCapacity);
    }

    public double getDepartureConstant() { return 0.7; }

    public double getLandingConstant() { return 0.8; }
}
