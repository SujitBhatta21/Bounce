package game;

import city.cs.engine.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.File;

import java.util.List;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MyUserView extends UserView {
    public World world;
    private Image background;
    private final int width, height;
    private final Timer timer;
    private static int timeLeft = 100;
    private static String gameState = "intro";
    private final static List<Collectable> keys = Game.getCollectableList();
    public static final Font STATUS_FONT = new Font("Monospaced", Font.PLAIN, 20);
    public static final Font helpFont = new Font("Monospaced", Font.PLAIN, 10);
    private boolean helpClicked = false, wonTheGame = false, lostTheGame = false;

    public MyUserView(World world, int width, int height) {
        super(world, width, height);
        this.world = world;
        this.width = width;
        this.height = height;
        try {
            background = ImageIO.read(new File("assets/images/background/background.jpg")); // replace with your image path
            background = background.getScaledInstance(width, height, Image.SCALE_SMOOTH); // resizing image to screen size
        } catch (IOException e) {
            System.out.println("Error: failed to load the background image.");
        }

        // Create a Timer that fires an event every second
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeLeft > 0) {
                    timeLeft--;
                } else {
                    // Time's up, set lostTheGame to true
                    lostTheGame = true;
                    ((Timer)e.getSource()).stop();
                }
            }
        });
    }

    @Override
    protected void paintForeground(Graphics2D g) {
         if (gameState == "play") {
             timer.start();

             g.setColor(Color.RED);
             g.setFont(STATUS_FONT);
             g.drawString("Coin count: " + keys.get(0).getCoin_count() + "/" + keys.get(0).getMax_coin_count(), 10, 25); // Max coin different on different levels. Consider that.
             g.drawString("Timer:" + this.timeLeft, 375, 25);

             int currentBallHealth = Game.getBall().getBallHealth();
             int maxBallHealth = Game.getBall().getBallMaxHealth();

             if (currentBallHealth != 0) {
                 double healthPercentage = (double) currentBallHealth / maxBallHealth;

                 if (healthPercentage > 0.7) {
                     g.setColor(Color.GREEN);
                 } else if (healthPercentage > 0.3) {
                     g.setColor(Color.ORANGE);
                 } else {
                     g.setColor(Color.RED);
                 }

                 int healthBarWidth = 150;
                 int healthBarHeight = 20;
                 int healthBarX = 10;
                 int healthBarY = 30;

                 int healthWidth = (int) ((currentBallHealth / (double) maxBallHealth) * healthBarWidth);

                 g.fillRect(healthBarX, healthBarY, healthWidth, healthBarHeight);

                 // Draw border
                 g.setColor(Color.BLACK);
                 g.drawRect(healthBarX, healthBarY, healthBarWidth, healthBarHeight);

                 // Draw the text Health on top of bar.
                 g.setColor(Color.BLACK);
                 g.setFont(helpFont);
                 g.drawString("Health", 15, 45);
             }

            // Displaying rectangle shape.
             int rectWidth = getWidth() / 2;
             int rectHeight = getHeight() / 2;
             int rectX = (getWidth() - rectWidth) / 2;
             int rectY = (getHeight() - rectHeight) / 2;

             if (helpClicked && !wonTheGame && !lostTheGame) {

                // Draw a red rectangle in the middle of the screen
                g.setColor(Color.YELLOW);
                g.fillRect(rectX, rectY, rectWidth, rectHeight);

                // Display the help text
                g.setFont(helpFont);
                g.setColor(Color.BLACK);
                g.drawString("Help template", rectX + 90, rectY + 20);
                g.drawString("Your goal in this level is to free ", rectX + 20, rectY + 40);
                g.drawString("your old trusty friend THE ROCK!!!", rectX + 20, rectY + 60);
                g.drawString("Collect all keys to open the cage.", rectX + 20, rectY + 80);

                g.drawString("How to play:", rectX + 20, rectY + 120);
                g.drawString("Use arrow keys to move BOUNCE / RED", rectX + 20, rectY + 140);
                g.drawString("Press R to restart the game.", rectX + 20, rectY + 160);
                g.drawString("New features coming soon!!!", rectX + 20, rectY + 180);
                g.drawString("Click the help again to exit this", rectX + 20, rectY + 220);
            }

            else if (wonTheGame) {
                timer.stop();
                // Draw a red rectangle in the middle of the screen
                g.setColor(Color.GREEN);
                g.fillRect(rectX, rectY, rectWidth, rectHeight);

                // Display congratulation screen.
                g.setFont(STATUS_FONT);
                g.setColor(Color.BLACK);
                g.drawString("Congratulations!!!", rectX + 20, rectY + 80);
                g.drawString("You saved your pal", rectX + 20, rectY + 120);
                g.drawString("ROOCKKKYYY", rectX + 20, rectY + 160);
            }

            else if (lostTheGame) {
                // Draw a red rectangle in the middle of the screen
                g.setColor(Color.RED);
                g.fillRect(rectX, rectY, rectWidth, rectHeight);

                // Display congratulation screen.
                g.setFont(STATUS_FONT);
                g.setColor(Color.WHITE);
                g.drawString("Mehhh", rectX + 20, rectY + 80);
                g.drawString("Try Again!!!", rectX + 20, rectY + 120);
                world.stop();
            }
        }
    }

    @Override
    protected void paintBackground(Graphics2D g) {
        g.drawImage(background, 0, 0, this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the original paintComponent method to do the default painting

        if (gameState == "intro") {
            Image image = null;
            try {
                image = ImageIO.read(new File("assets/images/GameLogo.png"));
                // Change some parameter to increase its size...
                image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            } catch (IOException e) {
                e.printStackTrace();
            }
            g.drawImage(image, 80, 60, 300, 100, this);
        }
        else if (gameState == "play") {
            Image image = null;
            try {
                image = ImageIO.read(new File("assets/images/cage.gif"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (image != null) {
                g.drawImage(image, 60, 60, 65, 55, this);
            }
        }

    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public static List<Collectable> getKeys() {
        return keys;
    }

    public boolean getHelpClicked() {
        return helpClicked;
    }
    public void setHelpClicked(boolean helpClicked) {
        this.helpClicked = helpClicked;
    }
    public boolean getWonTheGame() {
        return wonTheGame;
    }
    public void setWonTheGame(boolean wonTheGame) {
        this.wonTheGame = wonTheGame;
    }

    public boolean isLostTheGame() {
        return lostTheGame;
    }

    public void setLostTheGame(boolean lostTheGame) {
        this.lostTheGame = lostTheGame;
    }

    public static String getGameState() {
        return gameState;
    }

    public void setGameState(String gameState) {
        this.gameState = gameState;
    }
}
