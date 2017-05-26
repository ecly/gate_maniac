import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Random;
import javax.swing.JFrame;

/**
 *  Class for spawning Gates and handling all the Gates
 *  aswell as the choosing of random X coordinates for
 *  the openings. Furthermore includes the function for
 *  checking collision with the gates and the function
 *  that is constantly run to increase current gates Y.
 */
public class GateSpawner extends JFrame
{
    private static final long serialVersionUID = 1L;

    private static Game game = Game.getGame();
    private static Levels level;
    private static CopyOnWriteArrayList<GateSpawner> gates = new CopyOnWriteArrayList<GateSpawner>();

    static int amountOfGates = gates.size();
    static int lastGate;
    static int totalGates = 0;

    //variables for gate placement
    private double gateX;
    private double gateY;
    private static int randomX = Draw.getEdgeRight() - 70;

    static double gateAccMultiplier = 0.05;

    static Random r = new Random();

    public GateSpawner()
    {
        gateX = 0;
        gateY = 0;
    }

    public double getGateX() {
        return gateX;
    }

    //return variable for the "right side" of the gate
    public double getGateX2() {
    	return gateX + 50;
    }

    public double getGateY() {
        return gateY;
    }

    public static int getAmountOfGates() {
        return amountOfGates;
    }

    public static int getTotalGates() {
        return totalGates;
    }

    //add gate if none or last gate has Y>=220
    public static void addRandomGate()
    {
        if (totalGates == 0 || gates.get(lastGate).gateY >= 220)
        {
            GateSpawner gs = new GateSpawner();
            gs.gateX = r.nextInt(randomX); //blazeit
            gates.add(gs);
            totalGates++;
        }
    }

    public static CopyOnWriteArrayList<GateSpawner> getGates() {
        return gates;
    }

    //remove gates at bottom of screen
    public static void gateRemoval()
    {
        if (gates.get(0).gateY >= 495) {
            gates.remove(0);
        }
    }

    //variable refresh
    public static void gateUpdate()
    {
        amountOfGates = gates.size();
        lastGate = gates.size() - 1;
    }

    public static void gateLevelIncrease()
    {
        gateAccMultiplier = gateAccMultiplier + level.getLevelGateAccMultiplier();
    }

    public static void gateMovement()
    {
        for(GateSpawner gate: gates){
            gate.gateY = gate.gateY + gateAccMultiplier * game.getTimePassed();
        }
    }

    //variable reset used for gameOver and rageQuit
    public static void resetGateAcc()
    {
        gateAccMultiplier = 0.05;
    }

    public static void resetGates()
    {
        gates.clear();
        totalGates = 0;
    }

    //unit collission check
    public static void gateCollision()
    {
        for(GateSpawner gc: gates){
            if ((game.getCircleX() <= gc.gateX || game.getCircleX() >= gc.gateX + 50)
                && game.getCircleY() <= gc.gateY && game.getCircleY() >= gc.gateY - 20)
            {
                game.gameOver();
            }
        }
    }
}
