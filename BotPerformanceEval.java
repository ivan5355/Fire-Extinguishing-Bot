import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class BotPerformanceEval {
    private static final int GRID_SIZE = 20;
    private static final int SIMULATIONS_PER_Q = 10;
    private static final double Q_STEP = 0.1;
    
    //Run a single simulation for a given q value(flamability)
    public static boolean runSingleSimulation(double q) {
        Grid grid = new Grid(GRID_SIZE, GRID_SIZE);
        Fire fire = new Fire(grid);
        Bot1 bot = new Bot1(grid, fire);
        
        //Runs the bot until it reaches the button or does not catch up on fire(flamability)
        bot.move_bot(q);
        if(grid.getBotCell().equals(grid.getButtonCell()) && !grid.getBotCell().hasFire()){
            return true;
        } else {
            return false;
        }
    }

    //Runs simulations for a given flamability value
    public static double runSimulationsForQ(double q) {

        int successCount = 0;
        for (int i = 0; i < SIMULATIONS_PER_Q; i++) {
            if (runSingleSimulation(q)) {
                successCount++;
            }
        }
        return (double) successCount / SIMULATIONS_PER_Q;
    }

    
    public static void main(String[] args) {
        List<Double> qValues = new ArrayList<>();
        List<Double> successRates = new ArrayList<>();

        for (double q = 0; q <= 1.0; q += Q_STEP) {
            qValues.add(q);
            double successRate = runSimulationsForQ(q);
            successRates.add(successRate);
            System.out.printf("Q: %.2f, Success Rate: %.4f%n", q, successRate);
        }

        // Here we would typically call a graphing function
        // For now, we'll just print the results
        System.out.println("Q values: " + qValues);
        System.out.println("Success rates: " + successRates);
    }
}