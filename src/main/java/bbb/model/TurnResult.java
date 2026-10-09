package bbb.model;

public class TurnResult {
    private YardCoordinate playerDigCoordinate;
    private DigResult playerDigResult;
    private YardCoordinate cyberPupDigCoordinate;
    private DigResult cyberPupDigResult;

    public TurnResult(YardCoordinate playerDigCoordinate, DigResult playerDigResult, YardCoordinate cyberPupDigCoordinate, DigResult cyberPupDigResult) {
        this.playerDigCoordinate = playerDigCoordinate;
        this.playerDigResult = playerDigResult;
        this.cyberPupDigCoordinate = cyberPupDigCoordinate;
        this.cyberPupDigResult = cyberPupDigResult;
    }



    public YardCoordinate getPlayerDigCoordinate() {
        return playerDigCoordinate;
    }

    public DigResult getPlayerDigResult() {
        return playerDigResult;
    }

    public YardCoordinate getCyberPupDigCoordinate() {
        return cyberPupDigCoordinate;
    }

    public DigResult getCyberPupDigResult() {
        return cyberPupDigResult;
    }
    @Override
    public String toString(){
        return playerDigCoordinate + "," + playerDigResult + "," + cyberPupDigCoordinate + "," + cyberPupDigResult;
    }

}
