package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

public class Lever extends StaticBody {
    private World world;
    private int lever_count = 0;
    private static String leverState = "off";
    private static Shape leverShape = new BoxShape(1, 1);
    private static BodyImage lever_off = new BodyImage("assets/images/physics/lever_off.png", 2);
    private static BodyImage lever_on = new BodyImage("assets/images/physics/lever_on.png", 13);

    Lever(World world) {
        super(world, leverShape);
        this.world = world;
        addImage(lever_off);
    }

    public String getLeverState() {
        return leverState;
    }
    public void setLeverState(String leverState) {
        this.leverState = leverState;
        removeAllImages();    // Removes previous lever image.
        changeImage();        // Sets new image based on lever state.
    }
    public void changeImage() {
        if (leverState == "off") {
            addImage(lever_off);
        }
        else {
            addImage(lever_on);
            if (lever_count < 1) {
                draw_falling_rod(world);
            }
        }
    }

    public void draw_falling_rod(World world) {
        Shape rodShape = new BoxShape(0.5f, 5);
        DynamicBody rod = new DynamicBody(world, rodShape);
        rod.setPosition(new Vec2(0, 12.5f + (5)));
        lever_count++;
    }
}
