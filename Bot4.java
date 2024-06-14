import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Bot4 {
    private Grid grid;

    public Bot4(Grid grid) {
        this.grid = grid;
    }

    public List<Cell> finding_path_to_button(Cell beginning, Cell ending)
    {
        // HashMap<Cell, Cell> remaking_the_path_for_the_cells = new HashMap<>();
        boolean[][] seen_these_cells = new boolean[grid.getRows()][grid.getCols()];
        Queue<Cell> lineup_of_the_cells = new LinkedList<>();

        seen_these_cells[beginning.getRow()][beginning.getCol()] = true; //changed
        beginning.setParent_of_the_cell(null);
        lineup_of_the_cells.add(beginning);
        // remaking_the_path_for_the_cells.put(beginning, null);

        while(!lineup_of_the_cells.isEmpty())
        {
            Cell the_present_cell = lineup_of_the_cells.remove();

            if(the_present_cell.equals(ending))
            {
                 return remaking_the_path_for_the_cells(ending);
             }
            
            //look thru the adjacent cells in the grid
            int[] indices_for_the_row = {-1, 1, 0, 0}; // changed
            int[] indices_for_the_colum = {0, 0, -1, 1}; // changed
            
            for(int i = 0 ; i < 4; i++)
            {
                int r = the_present_cell.getRow() + indices_for_the_row[i];
                int c = the_present_cell.getCol() + indices_for_the_colum[i];
                if(is_it_safe_to_visit_the_cell_d(r,c,seen_these_cells))
                {
                    seen_these_cells[r][c] = true;
                    
                    Cell look_at_upcoming_cell = grid.getCell(r,c);  
                   
                    look_at_upcoming_cell.setParent_of_the_cell(the_present_cell);     
                   
                    lineup_of_the_cells.add(look_at_upcoming_cell);         

                }
            }
        }
        return new LinkedList<>();  
    }

    private boolean is_it_safe_to_visit_the_cell_d(int r, int c, boolean[][]seen_these_cells)
    {
        return r >= 0 &&
               r < grid.getCols()&& 
               c >= 0 && 
               c < grid.getRows() && 
               !seen_these_cells[r][c] &&
               !grid.getCell(r,c).hasFire()
               && grid.getCell(r,c).isOpen();
    }

    public List<Cell> find_path_to_nearest_fire_cell(Cell beginning){
        boolean[][] seen_these_cells = new boolean[grid.getRows()][grid.getCols()];
        Queue<Cell> lineup_of_the_cells = new LinkedList<>();

        seen_these_cells[beginning.getRow()][beginning.getCol()] = true; //changed
        beginning.setParent_of_the_cell(null);
        lineup_of_the_cells.add(beginning);

        while(!lineup_of_the_cells.isEmpty())
        {
            Cell the_present_cell = lineup_of_the_cells.remove();

            if(the_present_cell.hasFire())
            {
                return remaking_the_path_for_the_cells(the_present_cell);
            }
           
            //look thru the adjacent cells in the grid
            int[] indices_for_the_row = {-1, 1, 0, 0}; // changed
            int[] indices_for_the_colum = {0, 0, -1, 1}; // changed
            
            for(int i = 0 ; i < 4; i++)
            {
                int r = the_present_cell.getRow() + indices_for_the_row[i];
                int c = the_present_cell.getCol() + indices_for_the_colum[i];

                if(is_it_safe_to_visit_the_cell_f(r,c,seen_these_cells))
                {
                    seen_these_cells[r][c] = true;
                    
                    Cell look_at_upcoming_cell = grid.getCell(r,c);  
                   
                    look_at_upcoming_cell.setParent_of_the_cell(the_present_cell);     
                   
                    lineup_of_the_cells.add(look_at_upcoming_cell);        
                }
            }
        }
        return new LinkedList<>();  
    }

    private boolean is_it_safe_to_visit_the_cell_f(int r, int c, boolean[][]seen_these_cells)
    {
       if(r >= 0 && r < grid.getCols() && c >= 0 && c < grid.getRows() && !seen_these_cells[r][c] && grid.getCell(r,c).isOpen())
       {
           return true;
       }
       return false;
    }

     private LinkedList<Cell> remaking_the_path_for_the_cells(Cell ending)
    {
        LinkedList<Cell> new_cell_path = new LinkedList<>();
        for (Cell i = ending; i != null; i = i.getParent_of_the_Cell()) 
        {
            new_cell_path.addFirst(i);
        }
        return new_cell_path;
    }

    private double get_cost(Cell beginning, Cell ending) {
        List<Cell> path_to_fire_cell = find_path_to_nearest_fire_cell(beginning);
        List<Cell> path_to_button = finding_path_to_button(beginning, ending);

        System.out.println("Distacne to fire: " + path_to_fire_cell.size());
        System.out.println("Distacne to button: " + path_to_button.size());
        for(Cell cell:path_to_button){
            System.out.println(cell.getRow() + " " + cell.getCol());
        }
        
        if (path_to_fire_cell.isEmpty()) {
            return Double.MAX_VALUE;
        }
    
        int distance_to_fire = path_to_fire_cell.size();
        int distance_to_button = path_to_button.size();
    
        double total_distance = Math.abs(distance_to_fire) + Math.abs(distance_to_button);
    
        // Use absolute values to ensure non-negative weights
        double weight_of_distance_to_button = Math.abs(distance_to_button) / total_distance;
        double weight_of_distance_to_fire = Math.abs(distance_to_fire) / total_distance;

        System.out.println("Weight of distance to button: " + weight_of_distance_to_button);
        System.out.println("Weight of distance to fire: " + weight_of_distance_to_fire);
        
    
        // Calculate positive contributions of distances
        double positive_distance_to_button = weight_of_distance_to_button * Math.abs(distance_to_button);
        double positive_distance_to_fire = weight_of_distance_to_fire * Math.abs(distance_to_fire);
    
        // Sum up positive contributions to get the cost
        double cost = positive_distance_to_button + positive_distance_to_fire;
    
        return cost;
    }
    

    public List<Cell> get_path_for_bot_4(double ship_flambility) {
        Cell botCell = grid.getBotCell();
        List<Cell> path = new LinkedList<>();
        Fire fire = new Fire(grid); // Assuming Fire is a class managing fire spread
        grid.printGrid(); // Assuming this prints the grid state
        
        
        List<Cell> adjacent_open_cells = grid.get_all_adj_open_neigbors_of_bot_cells(botCell);
        double min_cost = Double.MAX_VALUE;
        Cell min_cost_cell = null;
    
       
            for (Cell cell : adjacent_open_cells) {
                System.out.println("Adjacent open cells:");
                System.out.println(cell.getRow() + " " + cell.getCol());
                System.out.println("Cost: " + get_cost(cell, grid.getButtonCell()));
                double cost = get_cost(cell, grid.getButtonCell());
                if (cost < min_cost) {
                    min_cost = cost;
                    min_cost_cell = cell;
                }
            }
            path.add(min_cost_cell);
            botCell.setBot(false);
            min_cost_cell.setBot(true);
            grid.printGrid(); 
            
        
        
        return path;
    }
    
    
  
}

