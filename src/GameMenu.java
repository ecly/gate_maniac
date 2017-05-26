import java.awt.Color;

/**
*   Class responsible for handling everything related
*   to the Game's Menu. Includes all the options in the
*   menu, aswell as the functions used for going up and
*   down in the menu. Extra options for menu are to be
*   added here and afterwards implemented in the Inputs.
*/

public class GameMenu
{
	private static int[] menuSettFps = new int[]{60, 80, 100, 120};
    private static String[] menuSettBgString = new String[]{"BLACK", "BLUE", "GRAY"}; //String for menu
    private static Color[] menuSettBgColor = new Color[]{Color.BLACK, Color.BLUE, Color.GRAY};

    //variables for using menu
    static int activeMenuItem = 0;
    static int activeFpsSett = 0;
    static int activeBgColorSett = 0;
    static int menuKeyDelay = 200;

    //amount of menu items, to avoid hardcoding
    static int amountOfFps = menuSettFps.length - 1;
    static int amountOfBgColors = menuSettBgString.length - 1;

    public static int getMenuKeyDelay() {
        return menuKeyDelay;
    }

    public static int getActiveMenuItem() {
        return activeMenuItem;
    }

    public static void nextMenuItem()
    {
        if(activeMenuItem < 2) {
            activeMenuItem++;
        }
    }

    public static void prevMenuItem()
    {
        if(activeMenuItem > 0) {
            activeMenuItem--;
        }
    }

    public static void nextMenuSett()
    {
        if (activeMenuItem == 1 && activeFpsSett < amountOfFps) {
            activeFpsSett++;
        }

        if (activeMenuItem == 2 && activeBgColorSett < amountOfBgColors) {
            activeBgColorSett++;
        }
    }

    public static void prevMenuSett()
    {
        if (activeMenuItem == 1 && activeFpsSett > 0) {
            activeFpsSett--;
        }

        if (activeMenuItem == 2 && activeBgColorSett > 0) {
            activeBgColorSett--;
        }
    }

    public static int getFps() {
        return menuSettFps[activeFpsSett];
    }

    public static Color getBgColor() {
        return menuSettBgColor[activeBgColorSett];
    }

    public static String getBgColorString() {
        return menuSettBgString[activeBgColorSett];
    }

    //resets menu values
    public static void resetMenu()
    {
        activeMenuItem = 0;
        activeFpsSett = 0;
        activeBgColorSett = 0;
    }
}