package travelpackage;

import java.util.ArrayList;

/**
 * Created by Cody on 3/16/2016.
 */
public enum  SeatLayoutEnum {
    S(1), M(2), W(3);

    SeatLayoutEnum(int i) {
        int value = i;
    }
    public String toString(){
        switch (this){
            case S:
                return "S";
            case M:
                return "M";
            case W:
                return "W";
            default:
                return "Layout not found";
        }
    }
    public int getCols(){
        switch (this){
            case S:
                return 3;
            case M:
                return 4;
            case W:
                return 10;
            default:
                return 0;
        }
    }
    public static SeatLayoutEnum toLayout(String layout){
        if(layout.equals("S"))
            return S;
        if(layout.equals("M"))
            return M;
        if(layout.equals("W"))
            return W;
        return null;
    }
    public ArrayList<Integer> getAisleRows(){
        ArrayList<Integer> aisleRows = new ArrayList<>(0);
        switch (this){
            case S:
                aisleRows.add(0);
                aisleRows.add(1);
                break;
            case M:
                aisleRows.add(1);
                aisleRows.add(2);
                break;
            case W:
                aisleRows.add(2);
                aisleRows.add(3);
                aisleRows.add(6);
                aisleRows.add(7);
                break;
            default:
                break;
        }
        return aisleRows;
    }
    public ArrayList<Integer> getWindowRows(){
        ArrayList<Integer> windowRows = new ArrayList<>(0);
        switch (this){
            case S:
                windowRows.add(0);
                windowRows.add(2);
                break;
            case M:
                windowRows.add(0);
                windowRows.add(4);
                break;
            case W:
                windowRows.add(0);
                windowRows.add(9);
                break;
            default:
                break;
        }
        return windowRows;
    }
}
