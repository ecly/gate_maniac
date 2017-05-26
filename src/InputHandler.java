import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
*   Class reponsible of the KeyListener itself
*   aswell as all KeyBinds and referred actions
*   of both the Menu and the Game itself.
 */
public class InputHandler implements KeyListener
{
    private Game game = Game.getGame();
    private Levels level;
    private GameMenu menu;
    private Draw draw;
    private InputHandler input = draw.getInput();

    private static boolean[] keys = new boolean[256];
    private static double newCircleX;
    private static double newCircleY;

    static long lastTimePressed = System.currentTimeMillis(); //used in menu

    /*
     * Assigns the newly created InputHandler to a Component
     * @param c Component to get input from
     */
    public InputHandler(Component c)
    {
    	c.addKeyListener(this);
    }

    /*
     * Checks whether a specific key is down
     * @param keyCode The key to check
     * @return Whether the key is pressed or not
     */
    public boolean isKeyDown(int keyCode)
    {
        if (keyCode > 0 && keyCode < 256) {
            return keys[keyCode];
        }

        return false;
    }

    /*
     * Called when a key is pressed while the component is focused
     * @param e KeyEvent sent by the component
     */
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() > 0 && e.getKeyCode() < 256) {
			keys[e.getKeyCode()] = true;
        }
    }

    /*
     * Called when a key is released while the component is focused
     * @param e KeyEvent sent by the component
     */
    public void keyReleased(KeyEvent e)
    {
        if (e.getKeyCode() > 0 && e.getKeyCode() < 256) {
                keys[e.getKeyCode()] = false;
            }
    }

    /*
    *  KeyBindings
    */
    public void inputCheckerGame()
    {
        //input management for game including boosts
        if (draw.input.isKeyDown(KeyEvent.VK_RIGHT))
        {
            newCircleX = game.getCircleX();
            if (draw.input.isKeyDown(KeyEvent.VK_SHIFT))
            {
                newCircleX += level.getMovementMultiplier() * level.getBoostMultiplier() * game.getTimePassed();
                game.moveCircleX(newCircleX);
            } else {
                newCircleX += level.getMovementMultiplier() * game.getTimePassed();
                game.moveCircleX(newCircleX);
            }

            if (game.getCircleX() >= draw.getEdgeRight()){
                game.moveCircleX(draw.getEdgeRight());
            }
        }

        if (draw.input.isKeyDown(KeyEvent.VK_LEFT))
        {
            newCircleX = game.getCircleX();
            if (draw.input.isKeyDown(KeyEvent.VK_SHIFT))
            {
                newCircleX -= level.getMovementMultiplier() * level.getBoostMultiplier() * game.getTimePassed();
                game.moveCircleX(newCircleX);
            } else {
                newCircleX -= level.getMovementMultiplier() * game.getTimePassed();
                game.moveCircleX(newCircleX);
            }

            if (game.getCircleX() <= draw.getEdgeLeft()){
                game.moveCircleX(draw.getEdgeLeft());
            }
        }

        if (draw.input.isKeyDown(KeyEvent.VK_UP))
        {
            newCircleY = game.getCircleY();
            if (draw.input.isKeyDown(KeyEvent.VK_SHIFT))
            {
                newCircleY -= level.getMovementMultiplier() * level.getBoostMultiplier() * game.getTimePassed();
                game.moveCircleY(newCircleY);
            } else {
                newCircleY -= level.getMovementMultiplier() * game.getTimePassed();
                game.moveCircleY(newCircleY);
            }

            if (game.getCircleY() <= draw.getEdgeTop()){
                game.moveCircleY(draw.getEdgeTop());
            }
        }

        if (draw.input.isKeyDown(KeyEvent.VK_DOWN))
        {
            newCircleY = game.getCircleY();
            if (draw.input.isKeyDown(KeyEvent.VK_SHIFT))
            {
                newCircleY += level.getMovementMultiplier() * level.getBoostMultiplier() * game.getTimePassed();
                game.moveCircleY(newCircleY);
            } else {
                newCircleY += level.getMovementMultiplier() * game.getTimePassed();
                game.moveCircleY(newCircleY);
            }

            if (game.getCircleY() >= draw.getEdgeBottom()){
                game.moveCircleY(draw.getEdgeBottom());
            }
        }

        //escape to exit game
        if (draw.input.isKeyDown(KeyEvent.VK_ESCAPE)) {
            game.rageQuit();
        }
    }

    public void inputCheckerMenu()
    {
        //Scrolling up and down in the menu
        if (draw.input.isKeyDown(KeyEvent.VK_DOWN))
        {
            //updating lastTimePressed
            lastTimePressed = System.currentTimeMillis();
            menu.nextMenuItem();
        }

        if (draw.input.isKeyDown(KeyEvent.VK_UP))
        {
            //updating lastTimePressed
            lastTimePressed = System.currentTimeMillis();
            menu.prevMenuItem();
        }

        //Scrolling through specific options in menu
        if (draw.input.isKeyDown(KeyEvent.VK_LEFT))
        {
            //updating lastTimePressed
            lastTimePressed = System.currentTimeMillis();
            menu.prevMenuSett();
        }

        if (draw.input.isKeyDown(KeyEvent.VK_RIGHT))
        {
            //updating lastTimePressed
            lastTimePressed = System.currentTimeMillis();
            menu.nextMenuSett();
        }

        //PLAY//
        if (draw.input.isKeyDown(KeyEvent.VK_ENTER) && menu.getActiveMenuItem() == 0)
        {
            game.setFrameLimiter(menu.getFps());
            draw.setBackgroundColor(menu.getBgColor());
            game.runGame();
        }

        // exit game from menu
        if (draw.input.isKeyDown(KeyEvent.VK_ESCAPE)) {
            game.menuQuit();
        }
    }

    public static long getLastTimePressed() {
        return lastTimePressed;
    }

    public static void resetKeys()
    {
        /*for(KeyEvent k: e) {
            keyReleased(k);
        }*/

        lastTimePressed = System.currentTimeMillis();
    }

    /*
     * Not used, for finding typed key.
     */
    public void keyTyped(KeyEvent e){} //needed for overriding
}