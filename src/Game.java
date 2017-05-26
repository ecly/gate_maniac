import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
*   Main Class that runs the game. Drawings on the screen are included in this class
*   and it is also reliable for updating the game and handling everything related
*   to the GameMenu. The 'Circle' that you control in the game is also included
*   in this class, and it lastly also holds our KeyBinds and their actions.
*/

public class Game extends JFrame
{
    private static final long serialVersionUID = 1L;

    //instantiate other classes
    private static Game game = new Game();
    private static Draw draw = new Draw();
    private static InputHandler input = draw.getInput();
    private static GameMenu menu = new GameMenu();
    private static Levels level = new Levels();

    private static boolean isRunning = false;
    private static boolean menuIsRunning = true;

    //starting coordinates for circle
    private static double circleX = 230;
    private static double circleY = 480;

    private int frameLimiter = 100; //default framelimiter to avoid dividing with zero
    private long timePassed = 0;

    static double points = 0;

    static long fps; // fps for showing on screen

    public static void main(String[] args)
    {
        game.run();
        System.exit(0);
    }

    public static Game getGame() {
        return game;
    }

    private void run()
    {
        long newTime = System.currentTimeMillis();
        long oldTime = System.currentTimeMillis();

        while(isRunning || menuIsRunning)
        {
            timePassed = newTime - oldTime;
            if (timePassed < ((long)1000 / frameLimiter)) {
                timePassed = ((long)1000 / frameLimiter);
            }

            update(timePassed); //method that calls update methods
            draw.draw(); // the draw method that draws everything

            if (timePassed > 0) {
                fps = (long)1000 / timePassed;
            }

            oldTime = newTime;
            newTime = System.currentTimeMillis();
        }
        draw.setVisible(false);
    }

    //return method for other classes to use
    public long getTimePassed() {
        return timePassed;
    }

    private void update(long timePassed)
    {
        if (menuIsRunning)
        {
            //check for lastTimePressed
            if ((System.currentTimeMillis() - InputHandler.getLastTimePressed()) >= menu.getMenuKeyDelay()){
                input.inputCheckerMenu(); //Scrolling up and down in the menu
            }
        }

        if (isRunning)
        {
            //input management for game including boosts
            input.inputCheckerGame();
            level.levelCheck();

            //adding and removing and movinggates
            GateSpawner.addRandomGate();
            GateSpawner.gateRemoval();
            GateSpawner.gateMovement();
            GateSpawner.gateUpdate();
            GateSpawner.gateCollision();

            //point management
            points = points + Levels.getPointsMultiplier();
        }
    }

    // set methods from menu
    public void setFrameLimiter(int newFrameLimiter) {
        frameLimiter = newFrameLimiter;
    }

    //methods for handling CircleX&Y
    public static double getCircleX() {
        return circleX;
    }

    public static double getCircleY() {
        return circleY;
    }

    //methods for moving the circle(player)
    public static void moveCircleX(double newCircleX) {
        circleX = newCircleX;
    }

    public static void moveCircleY(double newCircleY) {
        circleY = newCircleY;
    }

    public static double getPoints() {
        return points;
    }

    public static long getFps() {
        return fps;
    }

    public static boolean getIsRunning() {
        return isRunning;
    }

    public static boolean getMenuIsRunning() {
        return menuIsRunning;
    }

    //runs the game
    public static void runGame()
    {
        isRunning = true;
        menuIsRunning = false;
    }

    //runs the menu
    public static void runMenu()
    {
        isRunning = false;
        menuIsRunning = true;
    }

    //total quit of game
    public static void menuQuit()
    {
        isRunning = false;
        menuIsRunning = false;
    }

    /*  resets variables for Game and calls
    *   other classes' reset methods */
    public void reset()
    {
        circleX = 230;
        circleY = 480;

        points = 0;
        GateSpawner.resetGateAcc();
        GateSpawner.resetGates();
        menu.resetMenu();
        input.resetKeys();
        level.resetLevels();
    }

    //if game lost through unit collission
    public void gameOver()
    {
        runMenu(); //return to menu by default
        String pointsOnScreen = "" + (int)points/10;
        JOptionPane.showMessageDialog(null, "You accumulated " + pointsOnScreen + " points before dying miserably.");
        reset();
    }

    //if game quit by pressing escape
    public void rageQuit()
    {
        runMenu(); //return to menu by default
        String pointsOnScreen = "" + (int)points/10;
        JOptionPane.showMessageDialog(null, "You accumulated " + pointsOnScreen + " points before furiously quitting!");
        reset();
    }
}