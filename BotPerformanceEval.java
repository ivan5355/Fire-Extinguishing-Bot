import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class BotPerformanceEval {
    private static final int GRID_SIZE = 25;
    private static final int SIMULATIONS_PER_Q = 25;
    private static final BigDecimal Q_STEP = BigDecimal.valueOf(0.1);

    //Run a single simulation for a given q value(flamability), Takes in a bot class, where a new instance is created each time
    public static boolean runSingleSimulation(double q, Class<? extends FireEscapeBot> botClass) {
    try {
        Grid grid = new Grid(GRID_SIZE, GRID_SIZE);
        Fire fire = new Fire(grid);
        FireEscapeBot bot;
        Constructor<? extends FireEscapeBot> constructor = botClass.getConstructor(Grid.class, Fire.class);
        bot = constructor.newInstance(grid, fire);
        
        bot.move_bot(q);
        
        return grid.getBotCell().equals(grid.getButtonCell()) && !grid.getBotCell().hasFire();
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
        return false;
    }
}

    //Runs simulations for a given flamability value
    public static double runSimulationsForQ(double q, Class<? extends FireEscapeBot> botClass) {
        int successCount = 0;
        for (int i = 0; i < SIMULATIONS_PER_Q; i++) {
            if (runSingleSimulation(q, botClass)) {
                successCount++;
            }
        }
        return (double) successCount / SIMULATIONS_PER_Q;
    }

    public static void main(String[] args) {
        List<BigDecimal> qValues = new ArrayList<>();
        List<Double> successRates = new ArrayList<>();
        Bot1 bot1 = new Bot1(new Grid(GRID_SIZE, GRID_SIZE), new Fire(new Grid(GRID_SIZE, GRID_SIZE)));

        //Runs simulation for given q values
        for (BigDecimal q = BigDecimal.ZERO; q.compareTo(new BigDecimal("1")) < 0; q = q.add(Q_STEP)) {
            qValues.add(q);
            double successRate = runSimulationsForQ(q.doubleValue(), bot1.getClass());
            successRates.add(successRate);
            System.out.printf("Q: %.2f, Success Rate: %.4f%n", q, successRate);
        }
        System.out.println("********");
        System.out.println("Q values: " + qValues);
        System.out.println("Success rates: " + successRates);

        // List<BigDecimal> qValues = new ArrayList<>();
        // List<Double> successRates = new ArrayList<>();
        // Bot2 bot2 = new Bot2(new Grid(GRID_SIZE, GRID_SIZE), new Fire(new Grid(GRID_SIZE, GRID_SIZE)));

        // //Runs simulation for given q values
        // for (BigDecimal q = BigDecimal.ZERO; q.compareTo(new BigDecimal("0.9")) < 0; q = q.add(Q_STEP)) {
        //     qValues.add(q);
        //     double successRate = runSimulationsForQ(q.doubleValue(), bot2.getClass());
        //     successRates.add(successRate);
        //     System.out.printf("Q: %.2f, Success Rate: %.4f%n", q, successRate);
        // }

        // System.out.println("Q values: " + qValues);
        // System.out.println("Success rates: " + successRates);

        // List<BigDecimal> qValues = new ArrayList<>();
        // List<Double> successRates = new ArrayList<>();
        // Bot3 bot3 = new Bot3(new Grid(GRID_SIZE, GRID_SIZE), new Fire(new Grid(GRID_SIZE, GRID_SIZE)));

        // //Runs simulation for given q values
        // for (BigDecimal q = BigDecimal.ZERO; q.compareTo(new BigDecimal("0.9")) < 0; q = q.add(Q_STEP)) {
        //     qValues.add(q);
        //     double successRate = runSimulationsForQ(q.doubleValue(), bot3.getClass());
        //     successRates.add(successRate);
        //     System.out.printf("Q: %.2f, Success Rate: %.4f%n", q, successRate);
        // }

        // System.out.println("Q values: " + qValues);
        // System.out.println("Success rates: " + successRates);


    }
}
