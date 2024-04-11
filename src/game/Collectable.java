package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

public class Collectable extends StaticBody {
    private float x, y;
    private StaticBody collectableBody;
    private static int coin_count = 0;
    private static int max_coin_count = 3;

    private BodyImage image = new BodyImage("assets/images/physics/key.gif", 1f);
    private static final Shape collectableShape = new BoxShape(1, 0.5f);

    public Collectable(MyWorld world, float x, float y) {
        super(world, collectableShape);
        setPosition(new Vec2(x, y));

        // Setting level specific max coin count.
        // this.max_coin_count = Game.getLevel().getCollectableList().size();

        // Load an image for the collectable
        addImage(new BodyImage("assets/images/physics/key.gif"));
    }

    // setters and getters

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

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
