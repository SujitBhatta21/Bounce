package game;

import city.cs.engine.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.*;
import java.io.IOException;
import java.io.File;

import java.util.List;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents a UserView in the game. Things that user sees in the screen.
 *
 * @author      Sujit Bhatta, sujit.bhatta@city.ac.uk
 * @version     1.0
 * @since       1.0
 */
public class MyUserView extends UserView {
    /**
     * The world of the game.
     */
    private MyWorld world;

    /**
     * The view of the user.
     */
    private static MyUserView view;

    /**
     * The background image.
     */
    private Image background, snowBackground, scaledSnowImage;

    /**
     * The width and height of the view.
     */
    private final int width, height;

    /**
     * The timer for the game.
     */
    private final Timer timer;

    /**
     * The time left in the game.
     */
    private static int timeLeft = 100;

    /**
     * The state of the game.
     */
    private static String gameState = "intro";

    /**
     * The keys in the game.
     */
    private static List<Collectable> keys;

    /**
     * The current level of the game.
     */
    private GameLevel currentLevel;

    /**
     * The font for the status.
     */
    public static final Font STATUS_FONT = new Font("Monospaced", Font.PLAIN, 20);

    /**
     * The font for the help.
     */
    public static final Font helpFont = new Font("Monospaced", Font.PLAIN, 10);

    /**
     * The state of the help click, isWonTheGame and isLostTheGame.
     */
    private boolean helpClicked = false, wonTheGame = false, lostTheGame = false;

    /**
     * Constructor for the MyUserView class.
     *
     * @param  world The world of the game.
     * @param  width The width of the view.
     * @param  height The height of the view.
     */
    public MyUserView(MyWorld world, int width, int height) {
        super(world, width, height);
        this.view = this;
        this.world = world;
        this.width = width;
        this.height = height;
        try {
            background = ImageIO.read(new File("assets/images/background/background.jpg"));
            background = background.getScaledInstance(width, height, Image.SCALE_SMOOTH); // resizing image to screen size
        } catch (IOException e) {
            System.out.println("Error: failed to load the background image.");
        }

        try {
            snowBackground = ImageIO.read(new File("assets/images/background/snowyBackground.png"));
            snowBackground = snowBackground.getScaledInstance(width, height, Image.SCALE_SMOOTH); // resizing image to screen size
        } catch (IOException e) {
            System.out.println("Error: failed to load the background image.");
        }

        // Adding snow GIF in background for Level3.
        ImageIcon snowIcon = new ImageIcon("assets/images/background/snowFalling.gif");
        Image snowImage = snowIcon.getImage();
        scaledSnowImage = snowImage.getScaledInstance(width, height - 110, Image.SCALE_DEFAULT);

        // Create a Timer that fires an event every second
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyWorld world = currentLevel.getLevelWorld();
                if (timeLeft > 0 && !getHelpClicked() && !world.isStopped()) {
                    timeLeft--;
                } else if (timeLeft <= 0){
                    // Time's up
                    if (!lostTheGame) {
                        // If the game was not already lost, play the sound and set lostTheGame to true
                        lostTheGame = true;
                        Game.updateSound();
                    }
                    ((Timer)e.getSource()).stop();
                    Game.getLevel().getLevelWorld().stop();
                }
            }
        });

    }

    @Override
    /**
     * Method to paint the foreground.
     *
     * @param  g The graphics object.
     */

    protected void paintForeground(Graphics2D g) {
         if (gameState.equals("play")) {
             timer.start();

             g.setColor(Color.RED);
             g.setFont(STATUS_FONT);

             if (!(currentLevel instanceof Level4)) {
                 if (!keys.isEmpty()) {
                     g.drawString("Coin count: " + keys.get(0).getCoin_count() + "/" + keys.get(0).getMax_coin_count(), 10, 25); // Max coin different on different levels. Consider that.
                 }
                 g.drawString("Timer:" + this.timeLeft, width - 125, 25);
             }
             else {
                 g.drawString("BOSS LEVEL: Beat Hypnotiser", 10, 25);
             }

             /*
             When level change occurs ball changes so, it is null for some time which causes error.
              */
             if (Game.getLevel().getBall() != null) {
                 int currentBallHealth = Game.getLevel().getBall().getBallHealth();
                 int maxBallHealth = Game.getLevel().getBall().getBallMaxHealth();

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
             }


            // Displaying rectangle shape. Value for Y given in each condition.
             int rectWidth = getWidth() / 2;
             int rectHeight;
             int rectX = (getWidth() - rectWidth) / 2;
             int rectY;

             if (helpClicked && !wonTheGame && !lostTheGame) {
                 rectHeight = getHeight() / 2;
                 rectY = (getHeight() - rectHeight) / 2;

                 if (!(currentLevel instanceof Level4)) {
                     // Draw a red rectangle in the middle of the screen
                     g.setColor(Color.YELLOW);
                     g.fillRect(rectX, rectY, rectWidth, rectHeight);

                     // Display the help text
                     g.setFont(helpFont);
                     g.setColor(Color.BLACK);

                     g.drawString("How to play:", rectX + 20, rectY + 120);
                     g.drawString("Use arrow keys to move BOUNCE / RED", rectX + 20, rectY + 140);
                     g.drawString("Press M to change ball mode .", rectX + 20, rectY + 160);
                     g.drawString("New features coming soon!!!", rectX + 20, rectY + 180);
                     g.drawString("Click the help again to exit this", rectX + 20, rectY + 220);
                 }

                if (currentLevel instanceof Level1) {
                    g.drawString("Help template", rectX + 90, rectY + 20);
                    g.drawString("Your goal in this level is to free ", rectX + 20, rectY + 40);
                    g.drawString("your old trusty friend THE ROCKY!!!", rectX + 20, rectY + 60);
                    g.drawString("Collect all keys to open the cage.", rectX + 20, rectY + 80);

                    g.drawString("How to play:", rectX + 20, rectY + 120);
                    g.drawString("Use arrow keys to move BOUNCE / RED", rectX + 20, rectY + 140);
                    g.drawString("Press M to change ball mode .", rectX + 20, rectY + 160);
                    g.drawString("New features coming soon!!!", rectX + 20, rectY + 180);
                    g.drawString("Click the help again to exit this", rectX + 20, rectY + 220);
                }
                else if (currentLevel instanceof Level2) {
                    g.drawString("Help template", rectX + 90, rectY + 20);
                    g.drawString("Free YOURSELF. ", rectX + 20, rectY + 40);
                    g.drawString("ESCAPE FROM HYPNOTISER", rectX + 20, rectY + 60);
                    g.drawString("Collect all keys to open the door.", rectX + 20, rectY + 80);
                }
                else if (currentLevel instanceof Level3) {
                    g.drawString("Help template", rectX + 90, rectY + 20);
                    g.drawString("Save VOLLEY!!!", rectX + 20, rectY + 40);
                    g.drawString("ROCKY can break ICE :)", rectX + 20, rectY + 60);
                    g.drawString("Collect all keys to open the cage.", rectX + 20, rectY + 80);
                }
                /*
                // Level 4 gives no help...
                else if (currentLevel instanceof Level4) {
                    g.drawString("Help template", rectX + 90, rectY + 20);
                    g.drawString("BEAT HYPNOTISER", rectX + 20, rectY + 40);
                    g.drawString("With Power Of Friendship :)", rectX + 20, rectY + 60);
                    g.drawString("Stop him from hurting anyone else", rectX + 20, rectY + 80);
                }
                 */
             }

             else if (wonTheGame) {
                 rectHeight = getHeight() / 4;
                 rectY = (getHeight() - 2 * rectHeight) / 2;

                 // Draw a red rectangle in the middle of the screen
                 g.setColor(Color.GREEN);
                 g.fillRect(rectX, rectY, rectWidth, rectHeight);

                 // Display congratulation screen for level 1.
                 g.setFont(STATUS_FONT);
                 g.setColor(Color.BLACK);

                 if (!(currentLevel instanceof Level4)) {
                     // Display two buttons. Restart or go to next level for any level if won.
                     MyGameButton restartButton = new MyGameButton(world, -8, -4f, 2, 2, "RESTART", "assets/images/texts/restart.png");
                     Game.getAllButtons().add(restartButton);

                     MyGameButton nextLevelButton = new MyGameButton(world, 8, -4f, 2, 2, "NEXT LEVEL", "assets/images/texts/goToNextLevel.png");
                     Game.getAllButtons().add(nextLevelButton);
                 }

                 if (currentLevel instanceof Level1) {
                     g.drawString("Congratulations!!!", rectX + 20, rectY + 30);
                     g.drawString("You saved your pal", rectX + 20, rectY + 70);
                     g.drawString("ROOCKKKYYY", rectX + 20, rectY + 110);
                 } else if (currentLevel instanceof Level2) {
                     g.drawString("Congratulations!!!", rectX + 20, rectY + 30);
                     g.drawString("Now save your pal", rectX + 20, rectY + 70);
                     g.drawString("VOLLEY", rectX + 20, rectY + 110);
                 } else if (currentLevel instanceof Level3) {
                     // Setting falling platform to vertical to avoid interference with buttons.
                     for (FallingPlatform platform : Level3.getFallingPlatforms()) {
                         platform.setVertical();
                     }

                     g.drawString("Congratulations!!!", rectX + 20, rectY + 30);
                     g.drawString("Beat Hypnotiser", rectX + 20, rectY + 70);
                     g.drawString("Save Everyone", rectX + 20, rectY + 110);
                 } else if (currentLevel instanceof Level4) {
                     g.drawString("Congratulations!!!", rectX + 20, rectY + 30);
                     g.drawString("YOU COMPLETED THE GAME", rectX + 20, rectY + 70);
                     g.drawString("NOW, TOUCH SOME GRASS!!!", rectX + 20, rectY + 110);
                 }
             }

            else if (lostTheGame) {
                rectHeight = getHeight() / 4;
                rectY = (getHeight() - 2*rectHeight) / 2;

                // Display two buttons. Restart or go to next level for any level if won.
                 if (!(currentLevel instanceof Level4)) {
                     MyGameButton restartButton = new MyGameButton(world, -8, -4f, 2, 2, "RESTART", "assets/images/texts/restart.png");
                     Game.getAllButtons().add(restartButton);
                 }

                 // Draw a red rectangle in the middle of the screen
                 g.setColor(Color.RED);
                 g.fillRect(rectX, rectY, rectWidth, rectHeight);

                 // Display losing screen.
                 g.setFont(STATUS_FONT);
                 g.setColor(Color.WHITE);

                if (currentLevel instanceof Level1) {

                    g.drawString("You Lost Level1", rectX + 20, rectY + 30);
                    g.drawString("Try Again!!!", rectX + 20, rectY + 70);
                } else if (currentLevel instanceof Level2) {
                    g.drawString("You Lost Level2", rectX + 20, rectY + 30);
                    g.drawString("Try Again!!!", rectX + 20, rectY + 70);
                } else if (currentLevel instanceof Level3) {
                    // Setting falling platform to vertical to avoid interference with buttons.
                    for (FallingPlatform platform : Level3.getFallingPlatforms()) {
                        platform.setVertical();
                    }

                    g.drawString("You Lost Level3", rectX + 20, rectY + 30);
                    g.drawString("Try Again!!!", rectX + 20, rectY + 70);
                } else {
                    g.drawString("You Lost Level4", rectX + 20, rectY + 30);
                    g.drawString("Try Again!!!", rectX + 20, rectY + 70);
                }

                 world.stop();
            }
         }
    }

    @Override
    /**
     * Method to paint the background.
     *
     * @param  g The graphics object.
     */
    protected void paintBackground(Graphics2D g) {
        if (currentLevel instanceof Level3) {
            g.drawImage(snowBackground, 0, 0, this);
            g.drawImage(scaledSnowImage, 0, 0, this);
        }
        else if (currentLevel instanceof Level4) {
            g.drawImage(background, 0, 0, this);
        } else {
            g.drawImage(background, 0, 0, this);
        }
    }

    @Override
    /**
     * Method to paint the component in the current view.
     *
     * @param  g The graphics object.
     */
    public void paintComponent(Graphics g) {
        currentLevel = Game.getLevel();

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
            g.drawImage(image, 200, 100,  width / 2, height / 4, this);
        }
        else if (gameState == "play") {
            Image image = null;
            try {
                image = ImageIO.read(new File("assets/images/cage.gif"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (currentLevel instanceof Level1) {
                if (image != null) {
                    // Drawing cage on top of the rockball.
                    g.drawImage(image, 588, 82, 65, 55, this);
                }
            }

            else if (currentLevel instanceof Level3) {
                if (image != null) {
                    // Drawing cage on top of the rockball.
                    g.drawImage(image, 733, 440, 65, 55, this);
                }
            }
        }
    }

    /**
     * Method to restart the timer.
     */
    public void restartTimer() {
        timeLeft = 100; // reseting the timer
        timer.start();
    }

    /**
     * Setter for the time left.
     *
     * @param  timeLeft The time left.
     */
    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    /**
     * Getter for the keys.
     *
     * @return The keys.
     */
    public static List<Collectable> getKeys() {
        return keys;
    }

    /**
     * Getter for the help clicked.
     *
     * @return The state of the help click.
     */
    public boolean getHelpClicked() {
        return helpClicked;
    }

    /**
     * Setter for the help clicked.
     *
     * @param  helpClicked The state of the help click.
     */
    public void setHelpClicked(boolean helpClicked) {
        this.helpClicked = helpClicked;
    }

    /**
     * Getter for the game won state.
     *
     * @return The state of the game won.
     */
    public boolean getWonTheGame() {
        return wonTheGame;
    }

    /**
     * Setter for the game won state.
     *
     * @param  wonTheGame The state of the game won.
     */
    public void setWonTheGame(boolean wonTheGame) {
        this.wonTheGame = wonTheGame;
    }

    /**
     * Getter for the game lost state.
     *
     * @return The state of the game lost.
     */
    public boolean isLostTheGame() {
        return lostTheGame;
    }

    /**
     * Setter for the game lost state.
     *
     * @param  lostTheGame The state of the game lost.
     */
    public void setLostTheGame(boolean lostTheGame) {
        this.lostTheGame = lostTheGame;
    }

    /**
     * Getter for the game state.
     *
     * @return The game state.
     */
    public static String getGameState() {
        return gameState;
    }

    /**
     * Setter for the collectable list.
     *
     * @param  keys The collectable list.
     */
    public void setCollectableList(List<Collectable> keys) {
        this.keys = keys;
    }

    /**
     * Setter for the game state.
     *
     * @param  gameState The game state.
     */
    public void setGameState(String gameState) {
        this.gameState = gameState;
    }

    /**
     * Setter for the view world.
     *
     * @param  world The view world.
     */
    public void setViewWorld(MyWorld world) {
        this.world = world;
    }

    /**
     * Getter for the view.
     *
     * @return The view.
     */
    public static MyUserView getView() {
        return view;
    }
}
