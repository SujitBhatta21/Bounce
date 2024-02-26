package game;

import city.cs.engine.*;

public class Collectable extends StaticBody{
    private static int coin_count = 0;
    private static int max_coin_count = 3;

    private BodyImage image = new BodyImage("assets/images/physics/key.gif", 1f);
    private static final Shape collectableShape = new BoxShape(1, 0.5f);

    Collectable (World world) {
        super(world, collectableShape);
        addImage(image);
    }

    // setters and getters
    public int getCoin_count() {
        return coin_count;
    }
    public void setCoin_count(int coin_count) {
        this.coin_count = coin_count;
    }
    public int getMax_coin_count() {
        return max_coin_count;
    }

    public void setMax_coin_count(int max_coin_count) {
        this.max_coin_count = max_coin_count;
    }
}
