package project.executable;

import project.airline.Airline;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        int maxAircraftCount, airportNumber, passengerNumber;
        double prop, widebody, rapid, jet, operationFee;

        Scanner sc = new Scanner(new File("/Users/utku/Desktop/shared/test cases/scatter1/input2.txt"));

        maxAircraftCount = sc.nextInt();
        airportNumber = sc.nextInt();
        passengerNumber = sc.nextInt();

        prop = sc.nextDouble();
        widebody = sc.nextDouble();
        rapid = sc.nextDouble();
        jet = sc.nextDouble();
        operationFee = sc.nextDouble();

        Airline myAirline = new Airline(maxAircraftCount, operationFee, airportNumber, passengerNumber,
                                        prop, widebody, rapid, jet, "/Users/utku/Desktop/shared/dir/outputlog.txt");

        for(int i=0;i<=airportNumber;i++){
            String line = sc.nextLine();
            if(i==0)
                continue;
            String[] elements= line.split( "[ ,:]+");
            String type = elements[0];
            long ID = Integer.parseInt(elements[1]);
            double x = Double.parseDouble(elements[2]);
            double y = Double.parseDouble(elements[3]);
            double fuelCost = Double.parseDouble(elements[4]);
            double airportOperationFee = Double.parseDouble(elements[5]);
            int aircraftCapacity = Integer.parseInt(elements[6]);

            myAirline.newAirport(type, ID, x, y, fuelCost, airportOperationFee, aircraftCapacity);
        }

        for(int i=0; i<passengerNumber; i++){
            String line = sc.nextLine();
            String[] elements= line.split( "[ ,:\\[\\]]+");
            String type = elements[0];
            long ID = Long.parseLong(elements[1]);
            double weight = Double.parseDouble(elements[2]);
            int baggageCount = Integer.parseInt(elements[3]);
            int currentAirportId = Integer.parseInt(elements[4]);
            ArrayList<Integer> destinations = new ArrayList<>();
            for (int j=5; j<elements.length; j++){
                destinations.add(Integer.parseInt(elements[j]));
            }

            myAirline.newPassenger(type, ID, weight, baggageCount, currentAirportId, destinations);
        }
        sc.close();

        boolean canFly = true;

        while(canFly){
            canFly = myAirline.findNextFlight();
        }

        System.out.println("revenues = " + myAirline.getRevenues());
        System.out.println("expenses = " + myAirline.getExpenses());
    }
}
