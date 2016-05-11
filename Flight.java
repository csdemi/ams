package travelpackage;

import java.util.ArrayList;

/**
 * Created by Cody on 2/26/2016.
 */
public class Flight
{
    private final String forgin;
    private final String fdestination;
    private final String fid;
    private final int fmonth;
    private final int fday;
    private final int fyear;
    private final int fhr;
    private final int fmin;
    private final ArrayList<Section>sections = new ArrayList<>();

    public Flight(String name, String orig, String dest, int year, int month, int day, String id, int hr, int minute) {
        this.forgin = orig;
        this.fdestination = dest;
        this.fmonth = month;
        this.fday = day;
        this.fyear = year;
        this.fid=id;
        this.fhr=hr;
        this.fmin=minute;
    }

    public String getID()
    {
        return this.fid;
    }

    private String getInfo() {
        return this.fid+"|"+ this.fyear+", "+ this.fmonth+", "+ this.fday+", "+ this.fhr+", "+ this.fmin+"|"+ this.getFdestination()+"|"+ this.getOrgin();
    }

    public void createFlightSection(int rows, int e, SeatClass s, int value,SeatLayoutEnum sle) {
        Section section = new Section(rows,e,s,value,sle);
        sections.add(section);
    }

    public ArrayList<Seat> containsSeatType(SeatClass s) {
        for (Section section : sections) {
            SeatClass seatClass = section.getSeatClass();
            if (seatClass == s) {
                return section.getSectionType();
            }
        }
        return null;
    }

    public String toString(){
        return getInfo()+sections.toString();
    }

    public String getOrgin(){
        return forgin;}

    public String getFdestination(){return fdestination;
    }

    public Section containsType(SeatClass s) {
        for(Section o: sections){
            if(o.getSeatClass()==s){
                return o;
            }
        }
        return null;
    }

    public int getFYear() {
        return fyear;
    }
    public int getFmonth(){return fmonth;}
    public int getFday(){return fday;}
}
