package bbb.view;

import java.util.Scanner;

import bbb.model.BackyardBoneBrawlGame;
import bbb.model.GameState;
import bbb.model.TurnResult;
import bbb.model.YardCoordinate;

public class TopDog {
    private BackyardBoneBrawlGame game;
    
    TopDog(BackyardBoneBrawlGame game){
        this.game = game;
    }

    public void play(){
        System.out.println("Welcome to Backyard Bone Brawl! \nCan you dig it?");
        Scanner s = new Scanner(System.in);
        System.out.println("CyberPup's Yard: ");
        System.out.println(game.getCyberPupYard());
        System.out.println("Player's Yard: ");
        System.out.println(game.getPlayerYard());
        while (game.getGameState() == GameState.IN_PROGRESS) {
            System.out.println("Turn: "+ game.getTurnCount());
            System.out.println("Enter Command");
            String playerInput = s.nextLine();
            String[] splitInput = playerInput.split(" ");
            switch (splitInput[0]) {
                case "help":
                    System.out.println("dig <coordinate> - digs at the given coordinate, e.g. dig A5\n" + //
                                                "turn history - displays the turn history in the game so far (see Tracking Turns below)\n" + //
                                                "yards - displays the CyberPup's and Player's current yards\n" + //
                                                "help - lists the commands available\n" + //
                                                "quit - quits the game");
                    break;
                case "turn":
                    try{
                        if(splitInput[1].equalsIgnoreCase("history")){
                            System.out.println(game.getTurnHistory().toString());
                            //game.getTurnHistory().stream().forEach(System.out::print);
                            break;//needs tracking turns implementation    
                        }
                        else{
                        System.out.println("Invalid command, please try again.");
                        break;
                        }
                    }catch(ArrayIndexOutOfBoundsException e){System.out.println("Invalid error, please try again."); break;}
                   
                    
                case "yards":
                    System.out.println("CyberPup's Yard: ");
                    System.out.println(game.getCyberPupYard());
                    System.out.println("Player's Yard: ");
                    System.out.println(game.getPlayerYard());
                    break;
                case "dig":
                    try {
                        YardCoordinate playerGuess = new YardCoordinate(splitInput[1]);
                        TurnResult resultOfTurn = game.takeTurn(playerGuess);
                        System.out.println("CyberPup's Yard: ");
                        System.out.println(game.getCyberPupYard());
                        System.out.println("Player's Yard: ");
                        System.out.println(game.getPlayerYard());
                        System.out.println("You dug at " + resultOfTurn.getPlayerDigCoordinate().getCord()+ ": "+ resultOfTurn.getPlayerDigResult() +"("+ resultOfTurn.getPlayerDigCoordinate()+ ")");
                        System.out.println("CyberPup dug at " + resultOfTurn.getCyberPupDigCoordinate().getCord()+ ": "+ resultOfTurn.getCyberPupDigResult() +"("+ resultOfTurn.getCyberPupDigCoordinate()+ ")");
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Invalid coordinates, please try again.");
                    }
                    break;
                default:
                    System.out.println("DEFAULT REACHED:Invalid command, please try again.");
                    break;
            }
            if(playerInput.equalsIgnoreCase("quit")){
                break;
            }
            
            if (game.getGameState() == GameState.PLAYER_WON) {
                System.out.println("CyberPup's Yard: ");
                System.out.println(game.getCyberPupYard());
                System.out.println("Hot diggity dog! You win!");
            }
            if (game.getGameState() == GameState.CYBERPUP_WON) {
                System.out.println("Player's Yard: ");
                System.out.println(game.getPlayerYard());
                System.out.println("Try again next time! Cyberpup wins!");
            } 
        
        }
        s.close();
        }
    }
