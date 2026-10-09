package bbb.view;


public class BackyardBoneBrawlNet {
    public static void main(String[] args)  {
        BackyardBoneBrawlGameProxy game = new BackyardBoneBrawlGameProxy();
        TopDog topDog = new TopDog(game);
        topDog.play(); 
    }
}
