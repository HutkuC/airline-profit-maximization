package project.airline;

import project.airline.aircraft.Aircraft;
import project.airline.aircraft.concrete.JetPassengerAircraft;
import project.airline.aircraft.concrete.PropPassengerAircraft;
import project.airline.aircraft.concrete.RapidPassengerAircraft;
import project.airline.aircraft.concrete.WidebodyPassengerAircraft;
import project.airport.Airport;
import project.airport.HubAirport;
import project.airport.MajorAirport;
import project.airport.RegionalAirport;
import project.passenger.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Airline {
    double revenues = 0;
    double expenses = 0;
    int maxAircraftCount;
    int aircraftCount = 0;
    double operationalCost;
    ArrayList<Aircraft> aircrafts;
    ArrayList<Airport> airports;
    ArrayList<Passenger> economyPassengers;
    ArrayList<Passenger> businessPassengers;
    ArrayList<Passenger> firstClassPassengers;
    ArrayList<Passenger> luxuryPassengers;
    int airportNumber;
    int passengerNumber;
    double propOperationFee;
    double widebodyOperationFee;
    double rapidOperationFee;
    double jetOperationFee;
    HashMap<Long, Airport> airportHashMap;
    FileWriter wr;

    public Airline(int maxAircraftCount, double operationalCost, int airportNumber, int passengerNumber,
                   double propOperationFee, double widebodyOperationFee, double rapidOperationFee, double jetOperationFee, String outputPath) throws IOException {
        this.maxAircraftCount = maxAircraftCount;
        this.operationalCost = operationalCost;
        this.airportNumber = airportNumber;
        this.passengerNumber = passengerNumber;
        this.propOperationFee = propOperationFee;
        this.widebodyOperationFee = widebodyOperationFee;
        this.rapidOperationFee = rapidOperationFee;
        this.jetOperationFee = jetOperationFee;
        aircrafts = new ArrayList<>();
        airports = new ArrayList<>();
        economyPassengers = new ArrayList<>();
        businessPassengers = new ArrayList<>();
        firstClassPassengers = new ArrayList<>();
        luxuryPassengers = new ArrayList<>();
        airportHashMap = new HashMap<>();
        wr = new FileWriter(outputPath);
    }

    public double getRevenues() { return revenues; }

    public double getExpenses() { return expenses; }

    public void makeExpense(double amount) { expenses += amount; }

    public void makeRevenue(double amount) { revenues += amount; }

    int getMaxAircraftCount() { return maxAircraftCount; }

    int getAircraftCount() { return aircraftCount; }

    double getOperationalCost() { return operationalCost; }

    public HashMap<Long, Airport> getAirportHashMap(){ return airportHashMap; }

    boolean buyAircraft(Aircraft aircraft) {
        if (aircraftCount < maxAircraftCount) {
            if(aircraft.getCurrentAirport().canLand()){
                aircraft.getCurrentAirport().addAircraft(aircraft);
                aircrafts.add(aircraft);
                return true;
            }
        }
        return false;
    }

    boolean fly(Airport toAirport, int aircraftId) {
        //makeExpense(operationalCost * aircraftCount);
        if(aircrafts.get(aircraftId).canFly((toAirport))){
            double flightCost = aircrafts.get(aircraftId).fly(toAirport);
            makeExpense(flightCost);
            return true;
        }
        return false;
    }

    boolean loadPassenger(Passenger passenger, Airport airport, int aircraftId) {
        if(aircrafts.get(aircraftId).getCurrentAirport() != airport) return false;
        double expense = aircrafts.get(aircraftId).loadPassenger(passenger);
        makeExpense(expense);
        return expense != aircrafts.get(aircraftId).getOperationFee();
    }

    boolean unloadPassenger(Passenger passenger,int aircraftId) {
        double revenue = aircrafts.get(aircraftId).unloadPassenger(passenger);
        if(revenue < 0){
            makeExpense(-revenue);
            return false;
        }
        makeRevenue(revenue);
        return true;
    }

    public void newAirport(String type, long ID, double x, double y, double fuelCost, double airportOperationFee, int aircraftCapacity){
        if(Objects.equals(type, "hub")){
            Airport newAirport = new HubAirport(ID, x, y, fuelCost, airportOperationFee, aircraftCapacity);
            airports.add(newAirport);
            airportHashMap.put(ID, newAirport);
        }
        else if(Objects.equals(type, "major")){
            Airport newAirport = new MajorAirport(ID, x, y, fuelCost, airportOperationFee, aircraftCapacity);
            airports.add(newAirport);
            airportHashMap.put(ID, newAirport);
        }
        else if(Objects.equals(type, "regional")){
            Airport newAirport = new RegionalAirport(ID, x, y, fuelCost, airportOperationFee, aircraftCapacity);
            airports.add(newAirport);
            airportHashMap.put(ID, newAirport);
        }
    }

    public void newPassenger(String type, long ID, double weight, int baggageCount, long currentAirportID, ArrayList<Integer> destinations){
        if(Objects.equals(type, "economy")){
            Passenger newPassenger = new EconomyPassenger(airportHashMap.get(currentAirportID), ID, weight, baggageCount);
            for(long id:destinations){
                newPassenger.addDestination(airportHashMap.get(id));
            }
            economyPassengers.add(newPassenger);
            airportHashMap.get(currentAirportID).addPassenger(newPassenger);
        }
        else if(Objects.equals(type, "business")){
            Passenger newPassenger = new BusinessPassenger(airportHashMap.get(currentAirportID), ID, weight, baggageCount);
            for(long id:destinations){
                newPassenger.addDestination(airportHashMap.get(id));
            }
            businessPassengers.add(newPassenger);
            airportHashMap.get(currentAirportID).addPassenger(newPassenger);
        }
        else if(Objects.equals(type, "first")){
            Passenger newPassenger = new FirstClassPassenger(airportHashMap.get(currentAirportID), ID, weight, baggageCount);
            for(long id:destinations){
                newPassenger.addDestination(airportHashMap.get(id));
            }
            firstClassPassengers.add(newPassenger);
            airportHashMap.get(currentAirportID).addPassenger(newPassenger);
        }
        else if(Objects.equals(type, "luxury")){
            Passenger newPassenger = new LuxuryPassenger(airportHashMap.get(currentAirportID), ID, weight, baggageCount);
            for(long id:destinations){
                newPassenger.addDestination(airportHashMap.get(id));
            }
            luxuryPassengers.add(newPassenger);
            airportHashMap.get(currentAirportID).addPassenger(newPassenger);
        }
    }

    public Aircraft newAircraft(String type, Airport airport){
        if(maxAircraftCount == aircraftCount || airport.getAircraftCapacity()==airport.getAircraftCount())
            return null;
        Aircraft newAircraft;
        if (Objects.equals(type, "jet")){
            newAircraft = new JetPassengerAircraft(airport, jetOperationFee, aircraftCount);
        }
        else if (Objects.equals(type, "prop")){
            newAircraft = new PropPassengerAircraft(airport, propOperationFee, aircraftCount);
        }
        else if (Objects.equals(type, "widebody")){
            newAircraft = new WidebodyPassengerAircraft(airport, widebodyOperationFee, aircraftCount);
        }
        else{
            newAircraft = new RapidPassengerAircraft(airport, rapidOperationFee, aircraftCount);
        }
        airport.addAircraft(newAircraft);
        aircrafts.add(newAircraft);
        aircraftCount++;
        return newAircraft;
    }

    public double calculateRange(Aircraft aircraft){
        double l = 0.0, r = 100000.0;
        while(l<r){
            if (r-l < 0.0001){
                return l;
            }
            double mid = (l+r)/2.0;
            double fuelConsumption = aircraft.getFuelConsumption(mid);
            if(Math.abs(aircraft.getFuel()-fuelConsumption) < 0.01)
                return mid;
            if(fuelConsumption <= aircraft.getFuel()){
                l = mid;
            }
            else{
                r = mid;
            }
        }
        return l;
    }

    public ArrayList<Airport> findRoute(Airport fromAirport, Airport toAirport, double range){
        Queue<Airport> s = new LinkedList<>();
        s.add(fromAirport);
        HashMap<Long, Long> parent = new HashMap<>();
        HashMap<Long, Boolean> visit = new HashMap<>();
        for(Airport airport:airports){
            visit.put(airport.getID(), false);
        }
        visit.replace(fromAirport.getID(), true);
        while(!s.isEmpty()){
            Airport curr = s.remove();
            if(curr == toAirport)
                break;
            for(Airport airport:airports){
                if(!visit.get(airport.getID()) && curr.getDistance(airport) <= range){
                    parent.put(airport.getID(), curr.getID());
                    visit.replace(airport.getID(), true);
                    s.add(airport);
                }
            }
        }
        if(parent.get(toAirport.getID()) == null){
            return null;
        }
        ArrayList<Airport> route = new ArrayList<>();
        Long currID = toAirport.getID();
        while(currID != fromAirport.getID()){
            route.add(airportHashMap.get(currID));
            currID = parent.get(currID);
        }

        ArrayList<Airport> route2 = new ArrayList<>();
        for(int i=route.size()-1; i>=0; i--){
            route2.add(route.get(i));
        }
        return route2;
    }

    public double calculateRequiredFuel(Aircraft aircraft, double distance){
        double l = 0, r = Double.min(aircraft.getFuelCapacity(), (aircraft.getMaxWeight()-aircraft.getWeight())/0.7);
        while(l<r){
            if(r - l < 0.1)
                return r;
            double mid = (l+r)/2.0;
            aircraft.setFuel(mid);
            double consumption = aircraft.getFuelConsumption(distance);
            if(mid - consumption < 1 && mid > consumption)
                return mid;
            if(consumption < mid)
                r = mid;
            else
                l = mid;
        }
        return l;
    }

    public int makeFlight(Airport fromAirport, Airport toAirport) throws IOException {
        //System.out.println(fromAirport.getID() + " " + toAirport.getID());
        ArrayList<Passenger> passengersToFly = new ArrayList<>();
        int requiredEconomy = 0;
        int requiredBusiness = 0;
        int requiredFirstClass = 0;

        for(Passenger passenger:fromAirport.getPassengers()){
            int destinationIndex = passenger.getDestinationIndex();
            for(; destinationIndex < passenger.getDestinations().size(); destinationIndex++){
                if(passenger.getDestinations().get(destinationIndex) == toAirport){
                    passengersToFly.add(passenger);
                    if(passenger instanceof EconomyPassenger) requiredEconomy ++;
                    else if(passenger instanceof BusinessPassenger) requiredBusiness ++;
                    else if(passenger instanceof FirstClassPassenger || passenger instanceof LuxuryPassenger) requiredFirstClass ++;
                    break;
                }
            }
        }

        Aircraft aircraft = null;
        boolean flag = false;
        if(requiredEconomy + requiredBusiness * 3 + requiredFirstClass * 8 <= 30){
            for(Aircraft aircraft1:fromAirport.getAircrafts()){
                if(aircraft1 instanceof JetPassengerAircraft){
                    aircraft = aircraft1;
                    flag = true;
                    break;
                }
            }
            if(!flag && fromAirport.canLand() && aircraftCount < maxAircraftCount){
                aircraft = newAircraft("jet", fromAirport);
                wr.write("0 " + fromAirport.getID() + " 3" + " = " + "0.0" + "\n");
                flag = true;

            }
        }
        else if(requiredEconomy + requiredBusiness * 3 + requiredFirstClass * 8 <= 60){
            for(Aircraft aircraft1:fromAirport.getAircrafts()){
                if(aircraft1 instanceof PropPassengerAircraft){
                    aircraft = aircraft1;
                    flag = true;
                    break;
                }
            }
            if(!flag && fromAirport.canLand() && aircraftCount < maxAircraftCount){
                aircraft = newAircraft("prop", fromAirport);
                wr.write("0 " + fromAirport.getID() + " 0" + " = " + "0.0" + "\n");
                flag = true;

            }
        }
        else if(requiredEconomy + requiredBusiness * 3 + requiredFirstClass * 8 <= 120){
            for(Aircraft aircraft1:fromAirport.getAircrafts()){
                if(aircraft1 instanceof RapidPassengerAircraft){
                    aircraft = aircraft1;
                    flag = true;
                    break;
                }
            }
            if(!flag && fromAirport.canLand() && aircraftCount < maxAircraftCount){
                aircraft = newAircraft("rapid", fromAirport);
                wr.write("0 " + fromAirport.getID() + " 2" + " = " + "0.0" + "\n");
                flag = true;
            }
        }
        else if(requiredEconomy + requiredBusiness * 3 + requiredFirstClass * 8 <= 450){
            for(Aircraft aircraft1:fromAirport.getAircrafts()){
                if(aircraft1 instanceof WidebodyPassengerAircraft){
                    aircraft = aircraft1;
                    flag = true;
                    break;
                }
            }
            if(!flag && fromAirport.canLand() && aircraftCount < maxAircraftCount){
                aircraft = newAircraft("widebody", fromAirport);
                wr.write("0 " + fromAirport.getID() + " 1" + " = " + "0.0" + "\n");
                flag = true;

            }
        }

        if(!flag){
            return 0;
        }

        double currentFuel = aircraft.getFuel();
        aircraft.fillUp();
        double range = calculateRange(aircraft);
        aircraft.setFuel(currentFuel);

        ArrayList<Airport> route = findRoute(fromAirport, toAirport, range);

        if(route == null)
            return 0;

        aircraft.setSeats(requiredEconomy, requiredBusiness, requiredFirstClass);
        aircraft.setRemainingEconomy();

        wr.write("2 " + aircraft.getID() + " " + aircraft.getEconomySeats() + " " + requiredBusiness + " " + requiredFirstClass + " = " + "0.0" + "\n");

        for(Passenger passenger:passengersToFly){
            double expense = aircraft.loadPassenger(passenger);
            wr.write("4 " + passenger.getID() + " " + aircraft.getID() + " " + fromAirport.getID() + " = " + (-expense) + "\n");
            makeExpense(expense);
        }


        for(Airport airport:route){
            double fuelAmount = aircraft.getFuel();
            aircraft.setFuel(0);
            double optimisedFuel = calculateRequiredFuel(aircraft, aircraft.getCurrentAirport().getDistance(airport));
            aircraft.setFuel(fuelAmount);
            double expense = aircraft.addFuel(Double.max(0, optimisedFuel - fuelAmount));
            makeExpense(expense);
            wr.write("3 " + aircraft.getID() + " " + Double.max(0, optimisedFuel - fuelAmount) + " = " + (-expense) + "\n");
            double profit1=getRevenues()-getExpenses();
            fly(airport, aircraft.getID());
            double profit2=getRevenues()-getExpenses();
            wr.write("1 " + airport.getID() + " " + aircraft.getID() + " = " + (profit2-profit1) + "\n");
        }


        for (Passenger passenger:passengersToFly){
            double revenue = passenger.disembark(toAirport, aircraft.getAircraftTypeMultiplier());
            makeRevenue(revenue);
            wr.write("5 " + passenger.getID() + " " + aircraft.getID() + " " + toAirport.getID() + " = " + revenue + "\n");
        }

        return 1;
    }

    public boolean findNextFlight() throws IOException {
        for(Airport airport:airports){
            int flightsMade = 0;
            HashMap<Long, Integer> airportCounter = new HashMap<>();
            for(Passenger passenger:airport.getPassengers()){
                int destinationIndex = passenger.getDestinationIndex();
                for(; destinationIndex < passenger.getDestinations().size(); destinationIndex++){
                    if(airportCounter.containsKey(passenger.getDestinations().get(destinationIndex).getID())){
                        airportCounter.replace(passenger.getDestinations().get(destinationIndex).getID(),airportCounter.get(passenger.getDestinations().get(destinationIndex).getID())+1);
                    }
                    else{
                        airportCounter.put(passenger.getDestinations().get(destinationIndex).getID(),1);
                    }
                }
            }
            for(Airport airport1:airports){
                if(airportCounter.containsKey(airport1.getID())){
                    if(airportCounter.get(airport1.getID())>10){
                        flightsMade += makeFlight(airport, airport1);
                    }
                }
            }
            if(flightsMade > 0)
                return true;
        }
        wr.write(getRevenues()-getExpenses() + "\n");
        wr.close();
        return false;
    }



}
