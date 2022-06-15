package project.airport;

public class MajorAirport extends Airport{

    public MajorAirport(long ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
        super(ID, x, y, fuelCost, operationFee, aircraftCapacity);
    }

    public double getDepartureConstant() { return 0.9; }

    public double getLandingConstant() { return 1.0; }
}
