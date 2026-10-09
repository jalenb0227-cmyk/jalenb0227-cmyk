package bbb.model;

import java.util.List;

public interface BackyardBoneBrawlGame {
    public TurnResult takeTurn(YardCoordinate playerDigCoordinate);
    public String getPlayerYard();
    public String getCyberPupYard();
    public GameState getGameState();
    public void setGameState(GameState state);
    public int getTurnCount();
    public List<TurnResult> getTurnHistory();
}
