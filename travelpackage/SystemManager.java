package travelpackage;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Cody on 2/22/2016.
 */
class SystemManager extends ArrayList {
    /*
    IF STATEMENT FOR AIRPORT, BOAT, OR TRAIN(BRINGS IN A STRING CONVERTS THE FACADE INTO ABSTRACT FACTORY...
     */
    private final Airport airport;
    private final Airline airline;
    private final ArrayList<Airline> airliner = new ArrayList<>();
    private final ArrayList<Airport> airports = new ArrayList<>();

    public SystemManager() {
        this.airport = new Airport();
        this.airline = new Airline();
    }

    public void createAirline(String name) {
        if (name.length() > 6)
            System.out.println("Invalid length, please make sure it is less than 6 characters in length.");
        else {
            if (airline.containsName(name, airliner) == null)
                airliner.add(new Airline(name));
            else
                System.out.println("Airline already exists.");
        }
    }//BOAT=CRUISE LINE,TRAIN=RAIL LINE...?

    public void createAirport(String name) {
        if (name.length() != 3)
            System.out.println("Invalid name, make sure length is 3 characters long.");
        else {
            if (airport.containsName(name, airports) != true)
                airports.add(new Airport(name));
            else
                System.out.println("Airport already exists.");
        }

    }//BOAT=PORT,TRAIN=STATION

    public void createFlight(String name, String orig, String dest, int year, int month, int day, String id, int hr, int minute) {
        if (airport.containsName(orig, airports) && airport.containsName(dest, airports)) {
            Airline air = airline.containsName(name, airliner);
            if (air != null) {
                Flight flight1 = air.containsID(id);
                if (flight1 == null) {
                    if (month > 0 && month < 13 && day > 0 && day < 32 && year > 1970 && year < 2020) {
                        air.getFlights().add(new Flight(name, orig, dest, year, month, day, id, hr, minute));
                    } else
                        System.out.println("Invalid date");
                }
            } else
                System.out.println("invalid airline name");
        } else
            System.out.println("Invalid airport(s)");
    }//BOAT=CRUISE,TRAIN=ROUTE

    public void createSection(String name, String flID, int rows, int cols, SeatClass s, int value,SeatLayoutEnum sle) {
        Airline air = airline.containsName(name, airliner);
        if (air != null) {
            Flight flight1 = air.containsID(flID);
            if (flight1 != null) {
                ArrayList<Seat> section = flight1.containsSeatType(s);
                if (section != null) {
                    System.out.println("Section already exists with current flight");
                } else {
                    flight1.createFlightSection(rows, cols, s, value,sle);
                }
            } else {
                System.out.println("Invalid flight id");
            }
        } else {
            System.out.println("Invalid airline");
        }
    }//BOAT=DECKS,TRAIN=CARS

    public void bookSeat() {
        Scanner kb = new Scanner(System.in);
        boolean seatFound = false;
        try {
            System.out.println("Enter flight id:");
            String flID = kb.nextLine().toUpperCase();
            Flight flight = airline.containsID(flID, airliner);
            if (flight != null) {
                System.out.println("Enter an seat type(Economy,Business,First)");
                String type = kb.nextLine().toUpperCase();
                SeatClass classType = SeatClass.toType(type.substring(0, 1));
                Section area = flight.containsType(classType);
                if (area != null) {
                    area.printSection(classType);
                    System.out.println("What seat would you like to book?");
                    String seat = kb.nextLine().toUpperCase();
                    char num = seat.charAt(0);
                    char pos = seat.charAt(1);
                    int number = Integer.parseInt(num + "");
                    area.getAvaibility(classType, number, pos);
                } else {
                    System.out.println("Section doesnt exist");
                }
            } else
                System.out.println("Invalid airline");

        } catch (Exception e) {
            System.out.print(e);
        }
    }//BOAT=CABIN,TRAIN=BIRTH

    public void displaySystemDetails() {
        System.out.print(airports.toString());
        System.out.print("{" + airliner.toString().substring(1, airliner.toString().length() - 2) + "}");
        System.out.println();

    }//SAME JUST FOR SPECIFIC BOATS AND TRAINS

    public void findAvailableFlights(String orgin, String dest) {
        boolean flightFound = false;
        if (airport.containsName(orgin, airports) != true || airport.containsName(dest, airports) != true) {
            System.out.println("NOT FOUND.");
        } else {
            for (Airline anAirliner : airliner) {
                ArrayList<Flight> flight = anAirliner.getFlights();
                for (Flight aFlight : flight) {
                    if (aFlight.getOrgin() == orgin && aFlight.getFdestination() == dest) {
                        System.out.println("Flight found!");
                        flightFound = true;
                    }
                }
            }
            if (flightFound)
                System.out.println("Flight not avaible");
        }

    }//SAME AS BOATS AND TRAINS

    public void changeFlightSectionPrice(String orgin, String dest, String airlineName, String type, int price) {
        SeatClass s = SeatClass.toType(type.substring(0, 1));
        Airline air = airline.containsName(airlineName, airliner);
        if (air != null) {
            if (orgin.length() != 3 || dest.length() != 3)
                System.out.println("Invalid Airport name, make sure length is 3 characters long.");
            else {
                if (airport.containsName(orgin, airports) == true && airport.containsName(dest, airports) == true) {
                    Flight flight1 = air.findFlight(orgin, dest);
                    if (flight1 != null) {
                        Section section = flight1.containsType(s);
                        if (section != null) {
                            section.setPrice(price);
                        }
                    }
                } else
                    System.out.println("Flight doesn't exists.");
            }
        }
    }

    public void writeFile() throws FileNotFoundException {
        PrintWriter write = new PrintWriter("./src/travelpackage/ams.txt");
        write.println("{" + airliner.toString().substring(1, airliner.toString().length() - 2) + "}");
        write.close();
    }

    public void queryFlights(int month, int day, int year, String orgin, String destination, String type) {
        ArrayList<Flight>flights = new ArrayList(0);
        SeatClass seatClass = SeatClass.toType(type);
        for (Airline a:airliner){
            a.getFlights().stream().filter(flight -> flight.getOrgin().equals(orgin) && flight.getFdestination().equals(destination) && flight.getFYear() == year && flight.getFmonth() == month && flight.getFday() == day).forEach(flight -> {
                ArrayList<Seat> seats = flight.containsSeatType(seatClass);
                for (Seat seat : seats) {
                    if (seat.isAvaible() && !flights.contains(flight)) {
                        flights.add(flight);
                    }
                }
            });
        }
        System.out.println(flights);
    }

    public void seatByPref(String fid, String type, String pref) {
        SeatClass seatClass = SeatClass.toType(type);
        boolean seatFound = false;
        Flight flight = airline.containsID(fid,airliner);
        if(!seatFound)
        for(Seat seat:flight.containsSeatType(seatClass)){
            if(seat.isAvaible() && ((seat.isAsile()&&pref.equals("asile")) || ((seat.isWindow()&&pref.equals("window"))))){
                seat.setAvaible(false);
                System.out.println("Booked Seat "+seat);
                seatFound = true;
                break;
            }

        }
        if(!seatFound)
            for(Seat seat:flight.containsSeatType(seatClass)){
                if(seat.isAvaible()){
                    seat.setAvaible(false);
                    System.out.println("Booked Seat "+seat);
                    seatFound = true;
                    break;
                }

            }
        if(!seatFound)
            System.out.println("Seat Not Available");

    }
}
