import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;


public class Bot4 implements FireEscapeBot{

    private Grid grid;
    private Fire fire;


    public Bot4(Grid grid, Fire fire) 
    {
        this.grid = grid;
        this.fire = fire;
    }

     public List<Cell> findPathAStar(Cell beginning, Cell ending) {
    
        //keep tracks of the distance to current cell plus distance the adjdacent cell plus pythogorean distance to the button cell(heuristic)
        Map<Cell, Double> total_score = new HashMap<>();

        //priority queue to keep track of the cells with the lowest total score. Heuristic is used to determine which nodes have priority
        PriorityQueue<Cell> lineup = new PriorityQueue<>(Comparator.comparingDouble(c -> 
        total_score.getOrDefault(c, Double.POSITIVE_INFINITY)));

        Map<Cell, Double> start_to_curr_score = new HashMap<>();
        Set<Cell> seen_these_cells = new HashSet<>();

        lineup.add(beginning);
        start_to_curr_score.put(beginning, 0.0);
        total_score.put(beginning, heuristic(beginning, ending));
        beginning.setParent_of_the_cell(null);

        while(!lineup.isEmpty())
        {
            Cell the_present_cell = lineup.poll();
            seen_these_cells.add(the_present_cell);

            if(the_present_cell.equals(ending))
            {
                return create_path(ending);
            }
            
            //look thru the adjacent cells in the grid
            int[] indices_for_the_row = {-1, 1, 0, 0}; 
            int[] indices_for_the_colum = {0, 0, -1, 1}; 
            
            for(int i = 0 ; i < 4; i++)
            {
                int r = the_present_cell.getRow() + indices_for_the_row[i];
                int c = the_present_cell.getCol() + indices_for_the_colum[i];

                if(is_it_safe_to_visit_the_cell(r,c, seen_these_cells))
                {
                    Cell look_at_upcoming_cell = grid.getCell(r,c);  
                    double temp_distance = start_to_curr_score.get(the_present_cell) + 1;
                    if(temp_distance < start_to_curr_score.getOrDefault(look_at_upcoming_cell, Double.POSITIVE_INFINITY))
                    {
                        look_at_upcoming_cell.setParent_of_the_cell(the_present_cell);
                        start_to_curr_score.put(look_at_upcoming_cell, temp_distance);
                        double t_score = temp_distance + heuristic(look_at_upcoming_cell, ending);
                        total_score.put(look_at_upcoming_cell, t_score);
                        lineup.add(look_at_upcoming_cell);
                    }
                }
            }
        }

        return new ArrayList<>(); 
    }

    private double heuristic(Cell a, Cell b) {
        return Math.abs(a.getRow() - b.getRow()) + Math.abs(a.getCol() - b.getCol());
    }

    private LinkedList<Cell> create_path(Cell ending)
    {
        LinkedList<Cell> new_cell_path = new LinkedList<>();
        for (Cell i = ending; i != null; i = i.getParent_of_the_Cell()) 
        {
            new_cell_path.addFirst(i);
        }
        return new_cell_path;
    }

    private boolean is_it_safe_to_visit_the_cell(int r, int c, Set<Cell> seen_these_cells)
    {
        return r >= 0 &&
               r < grid.getCols()&& 
               c >= 0 && 
               c < grid.getRows() && 
               !seen_these_cells.contains(grid.getCell(r,c)) &&
               !grid.getCell(r,c).hasInitialFire() &&
               !grid.getCell(r,c).hasFire() &&
                grid.getCell(r,c).isOpen();
    }

    @Override
    public void move_bot(double ship_flambility) {
        Cell bot = grid.getBotCell();
        Cell button = grid.getButtonCell();

        List<Cell> path = findPathAStar(bot, button);
        for(Cell cell:path){
            System.out.println(cell.getRow() + " " + cell.getCol());
        }
        
        while(path.size() > 1) {

            Cell current = path.get(0);
            Cell next = path.get(1);
            current.setBot(false);
            next.setBot(true);
            grid.setBotCell(next);
            bot = next;
            
            List<Cell> adjOpenCells = fire.get_all_adj_open_neigbors_of_fire_cells();
            fire.spread_fire(adjOpenCells, ship_flambility);
            
            if(bot.equals(button)) {
                grid.printGrid();
                fire.extinguish_fire();
                System.out.println("Bot Cell:"+bot.getRow() + " " + bot.getCol());
                System.out.println("Button Cell:"+button.getRow() + " " + button.getCol());
                System.out.println("Fire has been extinguished. Task completed.");
                break;
            }
            if(!path.isEmpty()){
                grid.printGrid();
                path = findPathAStar(next, button);
                System.out.println("Current path:");
                for(Cell cell:path){
                    System.out.println(cell.getRow() + " " + cell.getCol());
                }
            }
            if(bot.hasFire()) {
                grid.printGrid();
                System.out.println("Current path:");
                for(Cell cell:path){
                    System.out.println(cell.getRow() + " " + cell.getCol());
                }
                System.out.println("Bot caught on fire. Task failed.");
                break;
            }
            if(path.isEmpty()) {
                grid.printGrid();
                System.out.println("Current path:");
                for(Cell cell:path){
                    System.out.println(cell.getRow() + " " + cell.getCol());
                }
                System.out.println("Task failed. No  path to button");
            }
        }
        
    }
    
    
}