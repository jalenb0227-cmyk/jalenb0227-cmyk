package bbb.model;

public enum Symbol {
    GRASS('#'),
    HOLE('O'),
    PART('@'),
    STICK('S'),
    BONE('B'),
    SQUEAKER('Q'),
    SHOE('H'),
    CHEW_ROPE('C');

    private char asciiChar;

    private Symbol(char asciiChar) {
        this.asciiChar = asciiChar;
    }

    public char getChar() {
        return asciiChar;
    }


}
