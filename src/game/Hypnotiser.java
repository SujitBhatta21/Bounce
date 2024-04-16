package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

public class Hypnotiser extends StaticBody {
    private static float width = 2f ,height = 1.2f;
    private static float x_pos, y_pos;
    private static int ballHits = 0;
    private static final Shape boxShape = new BoxShape(width, height);

    private BodyImage image1 = new BodyImage("assets/images/enemy/Hypnotiser_1.png", 2.7f*height);
    private BodyImage image2 = new BodyImage("assets/images/enemy/Hypnotiser_2.png", 2.7f*height);
    private BodyImage image3 = new BodyImage("assets/images/enemy/Hypnotiser_3.png", 2.7f*height);
    private BodyImage image4 = new BodyImage("assets/images/enemy/Hypnotiser_4.png", 2.7f*height);

    public Hypnotiser(MyWorld world, float x_pos, float y_pos) {
        super(world, boxShape);
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        setPosition(new Vec2(x_pos, y_pos));
        addImage(image1);
    }

    public static int getBallHits() {
        return ballHits;
    }

    public void setBallHits(int ballHits) {
        this.ballHits = ballHits;
    }

    public static float getXPos() {
        return x_pos;
    }

    public static float getYPos() {
        return y_pos;
    }

    public void updateImage() {
        if (this.ballHits == 2) {
            removeAllImages();
            addImage(image2);
        } else if (this.ballHits == 4) {
            removeAllImages();
            addImage(image3);
        } else if (this.ballHits == 6) {
            removeAllImages();
            addImage(image4);
        }
    }
}