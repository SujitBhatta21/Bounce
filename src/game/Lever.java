package game;

import city.cs.engine.*;

public class Lever extends StaticBody {
    private static String leverState = "off";
    private static Shape leverShape = new BoxShape(1, 1);
    private static BodyImage lever_off = new BodyImage("assets/images/physics/lever_off.png", 2);
    private static BodyImage lever_on = new BodyImage("assets/images/physics/lever_on.png", 13);

    Lever(World world) {
        super(world, leverShape);

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
        }
    }
}
