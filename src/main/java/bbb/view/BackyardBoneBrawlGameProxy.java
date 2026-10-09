package bbb.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import bbb.model.*;
import bbb.httpserver.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



public class BackyardBoneBrawlGameProxy implements BackyardBoneBrawlGame {

        private String sessionId;
        private HttpStream httpStream;
        private GameState state;
        private int turnCount = 0;
        private final String host = "localhost";
        private final int port = 8081;
        private List<TurnResult> turnHistory; 

    
    
    public BackyardBoneBrawlGameProxy() {     
        sessionId = UUID.randomUUID().toString();
        turnHistory = new ArrayList<>();
    }
    @Override
    public TurnResult takeTurn(YardCoordinate playerDigCoordinate) {
        try (Socket socket = new Socket(host, port);
             PrintWriter writer = new PrintWriter(socket.getOutputStream())) {

            String body = "dig " + playerDigCoordinate.toString().trim();
            String request = HttpRequestBuilder.buildPostRequest("/taketurn", sessionId, body);
            System.out.println(request);
            writer.print(request);
            writer.flush();

            httpStream = new HttpStream(socket.getInputStream());


            HttpResponse response = HttpResponseParser.parseResponse(httpStream);
            String responseBody = response.getBody();

            if (responseBody == null) {
                throw new IOException("Empty response body from server for /taketurn");
            }

            String[] parts = responseBody.split(",");
            

            TurnResult result = new TurnResult(
                new YardCoordinate(parts[0].trim()),
                DigResult.valueOf(parts[1].trim()),
                new YardCoordinate(parts[2].trim()),
                DigResult.valueOf(parts[3].trim())
            );

            turnHistory.add(result);
            return result;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new RuntimeException(ioe);
        }
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
    public String getPlayerYard(){
        try {
            String request = HttpRequestBuilder.buildGetRequest("/playeryard", sessionId);
            Socket socket = new Socket(host, port);
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            httpStream = new HttpStream(socket.getInputStream());
            writer.print(request);
            writer.flush();
            HttpResponse response = HttpResponseParser.parseResponse(httpStream);
            socket.close();
            return response.getBody();
        } catch (IOException ioe) {
            return ioe.getMessage();
        }
    }
    @Override
    public String getCyberPupYard(){
        try {
            String request = HttpRequestBuilder.buildGetRequest("/cyberpupyard", sessionId);
            Socket socket = new Socket(host, port);
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            httpStream = new HttpStream(socket.getInputStream());
            writer.print(request);
            writer.flush();
            HttpResponse response = HttpResponseParser.parseResponse(httpStream);
            socket.close();
            return response.getBody();
        } catch (IOException ioe) {
            return ioe.getMessage();
        }
    }
    @Override
    public GameState getGameState(){
        try {
            String request = HttpRequestBuilder.buildGetRequest("/gamestate", sessionId);
            Socket socket = new Socket(host, port);
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            httpStream = new HttpStream(socket.getInputStream());
            writer.print(request);
            writer.flush();
            HttpResponse response = HttpResponseParser.parseResponse(httpStream);
            response.getBody();
            socket.close();
            return GameState.valueOf(response.getBody());
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    
    public void setGameState(GameState state){
        this.state = state;
    }    
    public int[] translateDigCoordianate(YardCoordinate yc){ // to translate String coord into int array for index to be used in yard 2d array
        int[] out = new int[2];
        out[0] = (int)yc.getRow()-65;
        out[1] = yc.getCol();
        return out;
    }

}