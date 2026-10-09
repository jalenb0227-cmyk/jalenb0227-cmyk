package bbb.server;



import bbb.httpserver.*;
import bbb.model.*;

public class BackyardBoneBrawlRequestHandler implements RequestHandler {

    private BackyardBoneBrawlGame game;

    public BackyardBoneBrawlRequestHandler()  {
        game = new BackyardBoneBrawlGameImpl();
    }
    
    @Override
    public String handleRequest(HttpRequest request) {

        if (request.getMethod() == HttpRequest.Method.GET) { 
            if (request.getUri().equals("/gamestate")) 
                return game.getGameState().toString();
            if (request.getUri().equals("/playeryard")) 
                return game.getPlayerYard();
            if (request.getUri().equals("/cyberpupyard"))
                return game.getCyberPupYard();
        }
        if (request.getMethod() == HttpRequest.Method.POST && request.getUri().equals("/taketurn")) {
            try {
                String body = request.getBody().trim();
                String[] parts = body.split(" ");
                
                YardCoordinate coord = new YardCoordinate(parts[1]);
                TurnResult result = game.takeTurn(coord);

                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        } 
        return "";
        }
    }
