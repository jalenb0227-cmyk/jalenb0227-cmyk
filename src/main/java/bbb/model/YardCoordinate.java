package bbb.model;

public class YardCoordinate {
    private final char row;
    private final int col;
    private Symbol shown;
    private Symbol hidden;
    private boolean hasBeenGuessed = false;

    public YardCoordinate(char row, int column) {
        this.row = row;
        this.col = column;
        this.shown = Symbol.GRASS;
        this.hidden = Symbol.HOLE;
    }

    public YardCoordinate(String coordinate) {
        this.row = Character.toUpperCase(coordinate.charAt(0));
        this.col = Integer.parseInt(coordinate.substring(1)); 
        this.shown = Symbol.GRASS;
        this.hidden = Symbol.HOLE;
    }

    public boolean getGuessed(){
        return hasBeenGuessed;
    }
    public void setGuessed(boolean value){
        this.hasBeenGuessed = value;
    }

    public char getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
    public String getCord(){
        return (""+row + col);
    }
    public Symbol getShown(){
        return shown;
    }
    public void setShown(Symbol newSym){
        shown = newSym;
    }
    public Symbol getHidden(){
        return hidden;
    }
    public void setHidden(Symbol newSym){
        hidden = newSym;
    }
    public String toString(){
        return String.valueOf(row) + col;
    }
}