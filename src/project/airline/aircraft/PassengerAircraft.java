package project.airline.aircraft;

import project.airport.Airport;
import project.passenger.*;

import java.util.HashSet;
import java.util.Set;

public abstract class PassengerAircraft extends Aircraft {

    protected double floorArea;
    private int economySeats, businessSeats, firstClassSeats;
    private int occupiedEconomySeats, occupiedBusinessSeats, occupiedFirstClassSeats;
    Set<Passenger> passengers;

    public PassengerAircraft(Airport currentAirport, double weight, double maxWeight, double fuelCapacity, double floorArea, double fuelConsumption, double aircraftTypeMultiplier, double operationFee, int ID) {
        super(currentAirport, weight, maxWeight, fuelCapacity, fuelConsumption, aircraftTypeMultiplier, operationFee, ID);
        this.floorArea = floorArea;
        this.passengers = new HashSet<>();
    }

    public int getEconomySeats(){ return economySeats; }

    public double loadPassenger(Passenger passenger) {
        double loadingFee = operationFee * aircraftTypeMultiplier;
        if(passenger instanceof EconomyPassenger) {
            if (hasEmptySeat(1)) {
                passenger.board(1);
                passengers.add(passenger);
                occupiedEconomySeats++;
                loadingFee *= 1.2;
                return loadingFee;
            }
            else return operationFee;
        }
        else if(passenger instanceof BusinessPassenger) {
            if(hasEmptySeat(2)) {
                passenger.board(2);
                passengers.add(passenger);
                loadingFee *= 1.5;
                occupiedBusinessSeats++;
                return loadingFee;
            }
            else if(hasEmptySeat(1)) {
                passenger.board(1);
                passengers.add(passenger);
                occupiedEconomySeats++;
                loadingFee *= 1.2;
                return loadingFee;
            }
            else return operationFee;
        }
        else if(passenger instanceof FirstClassPassenger || passenger instanceof LuxuryPassenger) {
            if(hasEmptySeat(3)) {
                passenger.board(3);
                passengers.add(passenger);
                occupiedFirstClassSeats++;
                loadingFee *= 2.5;
                return loadingFee;
            }
            else if(hasEmptySeat(2)) {
                passenger.board(2);
                passengers.add(passenger);
                occupiedBusinessSeats++;
                loadingFee *= 1.5;
                return loadingFee;
            }
            else if(hasEmptySeat(1)) {
                passenger.board(1);
                passengers.add(passenger);
                occupiedEconomySeats++;
                loadingFee *= 1.2;
                return loadingFee;
            }
            else return operationFee;
        }
        return operationFee;
    }

    public double unloadPassenger(Passenger passenger) {
        double ticketPrice = passenger.disembark(getCurrentAirport(), getAircraftTypeMultiplier());
        if(ticketPrice != 0){
            passengers.remove(passenger);
            if(passenger.getSeatType() == 1) occupiedEconomySeats--;
            else if(passenger.getSeatType() == 2) occupiedBusinessSeats--;
            else if(passenger.getSeatType() == 3) occupiedFirstClassSeats--;
            return ticketPrice;
        }
        return -operationFee;
    }

    public boolean hasEmptySeat(int seatType) {
        if(seatType == 1) return occupiedEconomySeats < economySeats;
        if(seatType == 2) return occupiedBusinessSeats < businessSeats;
        if(seatType == 3) return occupiedFirstClassSeats < firstClassSeats;
        return false;
    }

    public void setSeats(int economy, int business, int firstClass) {
        economySeats = economy;
        businessSeats = business;
        firstClassSeats = firstClass;
    }

    public void setRemainingEconomy() {
        economySeats = (int) floorArea - 3 * businessSeats - 8 * firstClassSeats;
    }

    public double getFullness() {
        int occupied = occupiedBusinessSeats + occupiedEconomySeats + occupiedFirstClassSeats;
        int total = economySeats + businessSeats + firstClassSeats;
        return (double) occupied / total;
    }
}
