package project.airport;

public class RegionalAirport extends Airport{

    public RegionalAirport(long ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
        super(ID, x, y, fuelCost, operationFee, aircraftCapacity);
    }

    public double getDepartureConstant() { return 1.2; }

    public double getLandingConstant() { return 1.3; }

}
