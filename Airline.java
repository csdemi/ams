package travelpackage;

import java.util.ArrayList;

/**
 * Created by Cody on 2/22/2016.
 */
public class Airline
{
    private final String airlineName;
    private final ArrayList<Flight> flights;

    public Airline(String name) {
        this.airlineName=name;
        this.flights=new ArrayList<>();
    }

    public Airline() {
        this.airlineName=null;
        flights= new ArrayList<>();
    }

    public Airline containsName(String name,ArrayList<Airline> airliner) {
        for(Airline o : airliner)
            if (o.getName().equals(name))
                return o;
        return null;
    }

    private String getName(){
        return this.airlineName;}

    public ArrayList<Flight> getFlights(){
        return flights;
    }

    public Flight containsID(String flID) {
        for(Flight o : flights)
            if (o.getID()==flID)
                return o;
        return null;
    }

    public String toString(){
        return getName()+flights.toString()+"\n";
    }

    public Flight findFlight(String orgin, String dest) {
        for(Flight o: flights){
            if(o.getOrgin().equals(orgin)&&o.getFdestination().equals(dest))
                return o;
        }
        return null;
    }

    public Flight containsID(String flID, ArrayList<Airline> airliner) {
        for(Airline o:airliner){
            for(Flight f:o.getFlights()){
                if(f.getID().equals(flID))
                    return f;
            }
        }
        return null;
    }
}

