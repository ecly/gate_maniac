import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;

/**
*   Class for drawing everything on the screen.
*   Also responsible for initialization of the.
*/

public class Draw extends JFrame
{
	private static final long serialVersionUID = 1L;

	private Game game = Game.getGame();
    static InputHandler input;

    static String gameName = "GATEMANIAC";
    static Color backgroundColor = Color.BLACK;

    //windowSize
    static int windowWidth = 500;
    static int windowHeight = 500;

    //windowedges
    static int edgeLeft = 5;
    static int edgeRight = windowWidth - 25;
    static int edgeTop = 10;
    static int edgeBottom = windowHeight - 25;

    //init variables for bg
    static BufferedImage backBuffer;
    static Insets insets;

    public Draw()
    {
        setTitle("Game");
        setSize(windowWidth, windowHeight);
        setResizable(false);
        setVisible(true);
        insets = getInsets();
        setSize(insets.left + windowWidth + insets.right,
                insets.top + windowHeight + insets.bottom);

        backBuffer = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        input = new InputHandler(this);
        addKeyListener(input);
    }

    public static InputHandler getInput() {
        return input;
    }

    public void draw()
    {
        //initial Graphics2D
        Graphics2D g2 = (Graphics2D)getGraphics();

        //backBuffer to avoid image flickering
        Graphics2D bbg = backBuffer.createGraphics();
        bbg.setStroke(new BasicStroke(1));
        bbg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        //game
        if(game.getIsRunning()){
            String pointsOnScreen = "Points:" + (int)game.getPoints()/10;
            String totalGatesOnScreen = "Gates:" + GateSpawner.getTotalGates();
            String fpsOnScreen = "FPS:" + (int)game.getFps();


            //draws the background
            bbg.setColor(backgroundColor);
            bbg.fillRect(0, 0, 500, 500);

            //draws the filled oval
            bbg.setColor(Color.WHITE);
            bbg.fillOval((int)game.getCircleX(), (int)game.getCircleY(), 20, 20);

            //outline for oval
            bbg.setColor(Color.YELLOW);
            bbg.setStroke(new BasicStroke(3));
            bbg.drawOval((int)game.getCircleX(), (int)game.getCircleY(), 20, 20);

            //color for strings on screen
            bbg.setColor(Color.WHITE);

            //draws the points on the screen
            bbg.drawString(pointsOnScreen, 2, 10);

            //draw total gates on the screen
            bbg.drawString(totalGatesOnScreen, 445, 10);

            //draw fps on screen
            bbg.drawString(fpsOnScreen, 2, 500);

            //loop for drawing gates
            bbg.setStroke(new BasicStroke(5));
            bbg.setColor(Color.RED);
            for(GateSpawner drawnGate: GateSpawner.getGates()){
                bbg.drawLine(0, (int)drawnGate.getGateY(),
                            (int)drawnGate.getGateX(), (int)drawnGate.getGateY());           //left

                bbg.drawLine(windowWidth, (int)drawnGate.getGateY(),
                            (int)drawnGate.getGateX2(), (int)drawnGate.getGateY());        //right
            }

          //level announcer
            if (game.getPoints()/10 >= 100 && Game.getPoints()/10 <= 120)
            {
                bbg.setColor(Color.WHITE);
                bbg.drawString("LEVEL 1", 220, 30);
            }
            else if (game.getPoints()/10 >= 200 && game.getPoints()/10 <= 220)
            {
                bbg.setColor(Color.WHITE);
                bbg.drawString("LEVEL 2", 220, 30);
            }
            else if (game.getPoints()/10 >= 300 && game.getPoints()/10 <= 320)
            {
                bbg.setColor(Color.WHITE);
                bbg.drawString("LEVEL 3", 220, 30);
            }
            else if (game.getPoints()/10 >= 400 && game.getPoints()/10 <= 420)
            {
                bbg.setColor(Color.WHITE);
                bbg.drawString("LEVEL 4", 220, 30);
            }
        }

        //menu
        if (game.getMenuIsRunning())
        {
            String activeBgColor = "Color: " + GameMenu.getBgColorString();
            String activeFps = "FPS: " + String.valueOf(GameMenu.getFps());

            //draws the background
            bbg.setColor(Color.BLACK);
            bbg.fillRect(0, 0, 500, 500);

            bbg.setColor(Color.WHITE);
            bbg.setFont(new Font("Default", Font.BOLD, 30));
            bbg.drawString(gameName, 135, 30);

            //Play
            bbg.setColor(Color.WHITE);
            bbg.setFont(new Font("Rockwell", Font.PLAIN, 15));
            if (GameMenu.getActiveMenuItem() == 0) {   // check for active and change color if true
                bbg.setColor(Color.RED);
            }
            bbg.drawString("PLAY", 200, 200);

            //FPS
            bbg.setColor(Color.WHITE);
            if (GameMenu.getActiveMenuItem() == 1) {    // check for active and change color if true
                bbg.setColor(Color.RED);
            }
            bbg.drawString(activeFps, 200, 250);


            //Background Color
            bbg.setColor(Color.WHITE);
            if (GameMenu.getActiveMenuItem() == 2) {    // check for active and change color if true
                bbg.setColor(Color.RED);
            }
            bbg.drawString(activeBgColor, 200, 300);
        }

        g2.drawImage(backBuffer, insets.left, insets.top, this);
    }

    public static void setBackgroundColor(Color newBackgroundColor)
    {
        backgroundColor = newBackgroundColor;
    }

    //methods for the screen size used for collision
    public static int getEdgeTop() {
        return edgeTop;
    }

    public static int getEdgeBottom() {
        return edgeBottom;
    }

    public static int getEdgeLeft() {
        return edgeLeft;
    }

    public static int getEdgeRight() {
        return edgeRight;
    }
}