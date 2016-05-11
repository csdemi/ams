package travelpackage;

/**
 * Created by Cody on 2/27/2016.
 */
public enum  SeatClass {
    first,
    economy,
    business;

    public static SeatClass toType(String s){
        if(s.equals("E"))
            return economy;
        if(s.equals("F"))
            return first;
        if(s.equals("B"))
            return business;
        return null;
    }
}
