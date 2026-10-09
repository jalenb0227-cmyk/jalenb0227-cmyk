package bbb.view;

/**
 * Launches the local CLI version of Backyard Bone Brawl.
 * 
 * Main:
 * - Creates the game object.
 * - Creates a TopDog object that plays the game.
 * - Starts the game loop.
 * 
 * Uncomment the code when your implementation is underway (and remove this
 * comment line).
 */

//import bbb.model.BackyardBoneBrawlGame;
import bbb.model.BackyardBoneBrawlGameImpl;

public class BackyardBoneBrawlLocal {
    public static void main(String[] args) {
        BackyardBoneBrawlGameImpl game = new BackyardBoneBrawlGameImpl();
        TopDog topDog = new TopDog(game);
        topDog.play();
    }
}

