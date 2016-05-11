package travelpackage;

import java.util.ArrayList;

/**
 * Created by Cody on 2/27/2016.
 */
public class Section
{
    private final int row;
    private final int col;
    private int price;
    private SeatClass sectionType;
    private ArrayList<Seat> Business;
    private ArrayList<Seat> Economy;
    private ArrayList<Seat> First;

    public Section(int row, int layout, SeatClass seatClass, int value, SeatLayoutEnum sle){
        this.row=row;
        this.col=layout;
        this.sectionType = seatClass;
        this.price=value;
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if (seatClass==SeatClass.economy) {
            Economy=new ArrayList<>();
            for(int i = 0;i<row;i++){
                for(int j=0;j<col;j++){
                    boolean isAsile = sle.getAisleRows().contains(j);
                    boolean isWindow = sle.getWindowRows().contains(j);
                    Economy.add(new Seat(i + 1, alpha.charAt(j),isAsile,isWindow));
                    sectionType=seatClass;
                }
            }
        }
        if(seatClass==SeatClass.business){
            Business=new ArrayList<>();
            for(int i = 0;i<row;i++){
                for(int j=0;j<col;j++){
                    boolean isAsile = sle.getAisleRows().contains(j);
                    boolean isWindow = sle.getWindowRows().contains(j);
                    Business.add(new Seat(i+1, alpha.charAt(j),isAsile,isWindow));
                    sectionType=seatClass;
                }
            }
        }
        if(seatClass==SeatClass.first) {
            First=new ArrayList<>();
            for(int i = 0;i<row;i++){
                for(int j=0;j<col;j++){
                    boolean isAsile = sle.getAisleRows().contains(j);
                    boolean isWindow = sle.getWindowRows().contains(j);
                    First.add(new Seat(i+1, alpha.charAt(j),isAsile,isWindow));
                    sectionType=seatClass;
                }
            }
        }
    }

    private int getRows(){return row;}

    private int getCols(){return col;}

    public SeatClass getSeatClass(){return sectionType;}

    public ArrayList<Seat> getSectionType(){
        if(sectionType==SeatClass.economy)
            return Economy;
        else if(sectionType==SeatClass.business)
            return Business;
        else if(sectionType==SeatClass.first)
            return First;
        return null;
    }

    public String toString(){
        return getInfo()+":"+getType()+":"+getRows();
    }
    private String getType(){
        if(col==3)
            return "S";
        if(col==4)
            return "M";
        if(col==10)
            return "W";
        return null;
    }
    private String getInfo() {
        String s="";
        if(getSeatClass().equals(SeatClass.economy))
            s="E";
        if(getSeatClass().equals(SeatClass.business))
            s="B";
        if(getSeatClass().equals(SeatClass.first))
            s="F";
        return s+":"+price;
    }
    public void setPrice(int price){
        this.price=price;
    }


    public void printSection(SeatClass classType) {
        if(classType==SeatClass.economy){
            Economy.forEach(System.out::println);
        }
        if(classType==SeatClass.business){
            Business.forEach(System.out::println);
        }
        if(classType==SeatClass.first){
            First.forEach(System.out::println);
        }
    }

    public void getAvaibility(SeatClass area, int number, char pos) {
        if(area==SeatClass.economy){
            Economy.stream().filter(aEconomy -> aEconomy.getRow() == number && aEconomy.getCol() == pos && aEconomy.isAvaible()).forEach(aEconomy -> {
                aEconomy.setAvaible(false);
                System.out.println("Seat was booked.");
            });
        }
        else
            System.out.println("Section Unavailable");
    }
}
