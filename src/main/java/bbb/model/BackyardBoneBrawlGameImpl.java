package bbb.model;
import java.util.Random;

import bbb.view.AnsiColorCodes;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class BackyardBoneBrawlGameImpl implements BackyardBoneBrawlGame {
    public final int GRID_SIZE;
    private GameState state;
    private YardCoordinate[][] playerYard;
    private YardCoordinate[][] cyberPupYard;
    private List<TurnResult> turnHistory = new ArrayList<>(); 
    private int turnCount = 0;

    public BackyardBoneBrawlGameImpl(){
        GRID_SIZE = 10;
        state = GameState.IN_PROGRESS;
        playerYard = new YardCoordinate[GRID_SIZE][GRID_SIZE];
        cyberPupYard = new YardCoordinate[GRID_SIZE][GRID_SIZE];

        fillYard(playerYard, "src/main/java/bbb/data/player_yard.txt");
        fillYard(cyberPupYard, "src/main/java/bbb/data/player_yard.txt"); // don't think cyberpup yard should be filled with the same path
        layToys(cyberPupYard);
    }
    @Override
    public TurnResult takeTurn(YardCoordinate playerDigCoordinate){
        DigResult result = genDigResult(playerDigCoordinate, cyberPupYard);

        if (result == DigResult.FULL_TOY) { 
            if (allToysFound(cyberPupYard)) {
                state = GameState.PLAYER_WON;
            }
        
        }
        Random rand = new Random();
        int cordLetNum = rand.nextInt(GRID_SIZE);
        int cordNum = rand.nextInt(GRID_SIZE);
        char cordLet = (char)(65+cordLetNum);
        YardCoordinate pupGuess = new YardCoordinate(cordLet,cordNum);
        int[] guess = new int[2];
        guess = translateDigCoordianate(pupGuess);
        while(playerYard[guess[0]][guess[1]].getGuessed() == true){
            cordLetNum = rand.nextInt(GRID_SIZE);
            cordNum = rand.nextInt(GRID_SIZE);
            cordLet = (char)(65+cordLetNum);
            pupGuess = new YardCoordinate(cordLet,cordNum);  
            guess = translateDigCoordianate(pupGuess);
        }
        DigResult doggyDig = genDigResult(pupGuess, playerYard);

        if (doggyDig == DigResult.FULL_TOY) {
            if (allToysFound(playerYard)) {
                state = GameState.CYBERPUP_WON;
            }
        }
        TurnResult takenTurn = new TurnResult(playerDigCoordinate,result,pupGuess, doggyDig);
        turnCount++;
        turnHistory.add(takenTurn);
        return takenTurn;
    }
    @Override 
    public String getPlayerYard(){
        String output ="";
        int rowCount = 0;
        output += "* 0 1 2 3 4 5 6 7 8 9\n";
        for(YardCoordinate[] row : playerYard){
            output += (char)(65+rowCount);
            rowCount++;
            for(YardCoordinate peice: row){
                switch (peice.getHidden()) {
                    case Symbol.HOLE:
                        if (peice.getShown() ==Symbol.GRASS){output+= " " +AnsiColorCodes.GREEN+peice.getShown().getChar()+AnsiColorCodes.RESET;}
                        else{output+= " "+AnsiColorCodes.BROWN+peice.getShown().getChar()+AnsiColorCodes.RESET;}
                        break; 
                    case Symbol.PART:
                        output+= " "+AnsiColorCodes.LT_GRAY+peice.getShown().getChar()+AnsiColorCodes.RESET;
                        break; 
                    default:
                        output+= " "+AnsiColorCodes.PURPLE+peice.getHidden().getChar()+AnsiColorCodes.RESET;
                        break;
                }
            }
            output += "\n";
        }
        return output;
    }
    @Override
    public String getCyberPupYard(){
        String output ="";
        int rowCount = 0;
        output += "* 0 1 2 3 4 5 6 7 8 9\n";
        for(YardCoordinate[] row : cyberPupYard){
            output += (char)(65+rowCount);
            rowCount++;   
            for(YardCoordinate peice: row){
                
                switch (peice.getShown()) {
                    case Symbol.HOLE:
                        output+= " " +AnsiColorCodes.BROWN+peice.getShown().getChar()+AnsiColorCodes.RESET;
                        break;
                    case Symbol.GRASS:
                        output+= " " +AnsiColorCodes.GREEN+peice.getShown().getChar()+AnsiColorCodes.RESET;
                        break;  
                    default:
                        output+= " " +AnsiColorCodes.RED+Symbol.PART.getChar()+AnsiColorCodes.RESET;
                        break;
                }

            }
            output += "\n";
        }
        return output;
    }

    @Override
    public int getTurnCount() {
        return turnCount;
    }

    @Override
    public List<TurnResult> getTurnHistory() {
        return turnHistory;
    }
    @Override
    public GameState getGameState(){return state;}
    public void setGameState(GameState state){this.state = state;}    
    public int[] translateDigCoordianate(YardCoordinate yc){// to translate String coord into int array for index to be used in yard 2d array
        int[] out = new int[2];
        out[0] = (int)yc.getRow()-65;
        out[1] = yc.getCol();
        return out;
    }
    private DigResult genDigResult(YardCoordinate DigCoordinate, YardCoordinate[][] yard){
        int[] cord = translateDigCoordianate(DigCoordinate);

        Symbol symAtDig = yard[cord[0]][cord[1]].getHidden();

        if(symAtDig == null){
            return DigResult.INVALID;
        }

        if(symAtDig == Symbol.HOLE){
            yard[cord[0]][cord[1]].setShown(Symbol.HOLE);
            return DigResult.NOTHING;
        }

        yard[cord[0]][cord[1]].setShown(symAtDig);

        boolean moreParts = false;

        for(YardCoordinate[] row : yard){
            for(YardCoordinate piece : row){

                if(piece.getHidden() == symAtDig &&
                piece.getShown() != symAtDig){
                    moreParts = true;
                }

            }
        }

        if(moreParts){
            return DigResult.TOY_PART;
        }

    return DigResult.FULL_TOY;
}
    private void fillYard(YardCoordinate[][] yard, String filepath){
        if (filepath.equals("src/main/java/bbb/data/player_yard.txt")) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(filepath));
                String line;
                int row = 0;
                br.readLine();
                while ((line = br.readLine()) != null && row < GRID_SIZE) {
                    char rowChar = (char)(65 + row);
                    for (int col = 0; col < GRID_SIZE; col++) {
                        char c = line.charAt(col + 1);
                        Symbol hiddenSym = getSymbolFromChar(c);
                        yard[row][col] = new YardCoordinate(rowChar, col);
                        yard[row][col].setHidden(hiddenSym);
                    }
                    row++;
                }
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            for(int letter =0; letter<GRID_SIZE;letter++){
                for(int number = 0; number <GRID_SIZE; number++) {
                    String cord = Character.toString(65+letter) + number;
                    yard[letter][number] = new YardCoordinate(cord);
            }
        }
        layToys(yard);
        }

    }
    private void layToys(YardCoordinate[][] yard){
        for (Toy toy : Toy.values()) {
            Boolean placed =false;// to keep track of if the toy has been placed
            Random rand = new Random();
            int flip = rand.nextInt(1,3);// generate a 1 or 2 to decide if the toy will be placed vertically or horizontally
            int[] dex = new int[2];
            while(!placed){
                    Boolean allNull = true;
                    int cordLetNum =0;
                    int cordNum=0;
                    while (allNull){
                        if(flip%2==1){//gen for vereticallity bounds
                            cordLetNum = rand.nextInt(GRID_SIZE-toy.getLen());
                            cordNum = rand.nextInt(GRID_SIZE);
                        }
                        if(flip%2==0){//gen for horizontal bounds
                            cordLetNum = rand.nextInt(GRID_SIZE);
                            cordNum = rand.nextInt(GRID_SIZE-toy.getLen());
                        }
                        char cordLet = (char)(65+cordLetNum);
                        YardCoordinate randomSpot = new YardCoordinate(cordLet,cordNum);
                        dex = translateDigCoordianate(randomSpot);
                        for(int i =0; i <toy.getLen(); i++){
                            if(flip%2==1){// if odd, place the peice vertically
                                if(yard[dex[0]+i][dex[1]].getHidden()!=Symbol.HOLE){
                                    allNull = false;
                                }
                            }
                            if(flip%2==0){// if odd, place the peice horziontal
                                if(yard[dex[0]][dex[1]+i].getHidden()!=Symbol.HOLE){
                                    allNull = false;
                                }
                            }
                        }
                        if(allNull){break;}
                    }
                    if(allNull == true){
                        placed = true;
                        if(flip%2==1){// if odd, place the peice vertically
                            for(int i =0; i <toy.getLen(); i++){
                                yard[dex[0]+i][dex[1]].setHidden(Symbol.valueOf(toy.name()));
                            }
                        }
                        if(flip%2==0){// if even, place the peice horzontally
                            for(int i =0; i <toy.getLen(); i++){
                                yard[dex[0]][dex[1]+i].setHidden(Symbol.valueOf(toy.name()));
                            }
                        }
                    }
                }
        }
    }

    private boolean allToysFound(YardCoordinate[][] yard) {
        for (YardCoordinate[] row : yard) {
            for (YardCoordinate piece : row) {
                if(piece.getHidden() != Symbol.HOLE && piece.getShown() != piece.getHidden()) {
                    return false;
                }
            }
        }
        return true;
    }

    private Symbol getSymbolFromChar(char c) {
        switch (c) {
            case 'S': return Symbol.STICK;
            case 'B': return Symbol.BONE;
            case 'Q': return Symbol.SQUEAKER;
            case 'H': return Symbol.SHOE;
            case 'C': return Symbol.CHEW_ROPE;
            case '#': return Symbol.HOLE;
            default: return Symbol.HOLE;
        }
    }
}
