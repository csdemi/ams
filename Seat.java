package travelpackage;

/**
 * Created by Cody on 3/4/2016.
 */
class Seat {
    private final int row;
    private final char col;
    private boolean isAvaible,isAsile,isWindow;

    public Seat(int row, char col,boolean asile,boolean window){
        this.row = row;
        this.col = col;
        this.isAvaible = true;
        this.isWindow=window;
        this.isAsile=asile;
    }

    public int getRow(){
        return this.row;
    }

    public char getCol(){
        return this.col;
    }

    public boolean isAvaible(){
        return isAvaible;
    }

    public void setAvaible(boolean avaible){
        this.isAvaible = avaible;
    }

    public void setAsile(boolean asile){this.isAsile=asile;}
    public void setWindow(boolean window){this.isWindow=window;}

    public String toString(){
        return row+""+col;
    }

    public boolean isAsile() {
        return isAsile;
    }
    public boolean isWindow() {
        return isWindow;
    }
}
