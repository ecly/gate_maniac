/**
*   Class including all multipliers relevant
*   for levels and includes all the values for
*   levels in the game.
*/
public class Levels
{
    private Game game = Game.getGame();

    //game multipliers and points
    private static double pointsMultiplier = 0.5;
    private static double movementMultiplier = 0.15;
    private static double boostMultiplier = 2;
    private static double levelMovementMultiplier = 0.02;
    private static double levelGateAccMultiplier = 0.02;
    private static int levelCheck = 0; //used for making sure levelups only activate once

    public void levelCheck()
    {
        if (game.getPoints()/10 >= 100 && levelCheck == 0)
        {
            levelCheck++;
            GateSpawner.gateLevelIncrease();
            movementMultiplier = movementMultiplier + levelMovementMultiplier;
        }

        //Level 2
        if (game.getPoints()/10 >= 200 && levelCheck == 1)
        {
            levelCheck++;
            GateSpawner.gateLevelIncrease();
            movementMultiplier = movementMultiplier + levelMovementMultiplier;
        }

        //Level 3
        if (game.getPoints()/10 >= 300 && levelCheck == 2)
        {
            levelCheck++;
            GateSpawner.gateLevelIncrease();
            movementMultiplier = movementMultiplier + levelMovementMultiplier;
        }

        //Level 4
        if (game.getPoints()/10 >= 400 && levelCheck == 3)
        {
            levelCheck++;
            GateSpawner.gateLevelIncrease();
            movementMultiplier = movementMultiplier + levelMovementMultiplier;
        }
    }

    //get methods for multipliers
    public static double getMovementMultiplier() {
        return movementMultiplier;
    }

    public static double getPointsMultiplier() {
        return pointsMultiplier;
    }

    public static double getBoostMultiplier() {
        return boostMultiplier;
    }

    public static double getLevelGateAccMultiplier() {
        return levelGateAccMultiplier;
    }

    public static void resetLevels()
    {
        pointsMultiplier = 0.5;
        movementMultiplier = 0.15;
        boostMultiplier = 2;
        levelMovementMultiplier = 0.02;
        levelCheck = 0;
    }
}