package bbb.server;


import bbb.httpserver.*;
import java.io.IOException;

public class BackyardBoneBrawlServer {
    public static void main(String[] args) throws IOException {
        HttpWebServer server = new HttpWebServer(8081, () -> new BackyardBoneBrawlRequestHandler());
        server.start();
    }
}
