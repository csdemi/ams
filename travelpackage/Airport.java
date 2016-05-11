package travelpackage;

import java.util.ArrayList;

/**
 * Created by Cody on 2/22/2016.
 */
class Airport
{
    private final String airportName;

    public Airport()
    {
        this.airportName = null;
    }

    public Airport(String n)
    {
        this.airportName=n;
    }

    private String getName() {
        return this.airportName;
    }

    public boolean containsName(String name, ArrayList<Airport> airport) {
        for(Airport o : airport)
            if (o.getName().equals(name))
                return true;
        return false;
    }

    public String toString(){
        return getName();
    }
}
