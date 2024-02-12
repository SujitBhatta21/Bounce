package game;

public class GlobalVariables {
    private static float x_pos = 4;
    private static float y_pos = -5;

    public static float getXPos() {
        return x_pos;
    }

    public static float getYPos() {
        return y_pos;
    }

    public static void setXPos(float newXPos) {
        x_pos = newXPos;
    }

    public static void setYPos(float newYPos) {
        y_pos = newYPos;
    }
}
