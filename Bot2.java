import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Bot2 {
    private Grid grid;
    private Fire fire;
   

    public Bot2(Grid grid, Fire fire) {
        this.grid = grid;
        this.fire = fire;
    }

    

    public List<Cell> finding_path_for_bot_two(Cell beginning, Cell ending)
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
                if(is_it_safe_to_visit_the_cel(r,c,seen_these_cells))
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

    private LinkedList<Cell> remaking_the_path_for_the_cells(Cell ending)
    {
        
        LinkedList<Cell> new_cell_path = new LinkedList<>();
        for (Cell i = ending; i != null; i = i.getParent_of_the_Cell()) 
        {
            new_cell_path.addFirst(i);
            //i.cellPath(true);
        }
        return new_cell_path;
        
    }

    private boolean is_it_safe_to_visit_the_cel(int r, int c, boolean[][]seen_these_cells)
    {
        return r >= 0 &&
               r < grid.getCols()&& 
               c >= 0 && 
               c < grid.getRows() && 
               !seen_these_cells[r][c] && 
               !grid.getCell(r,c).hasInitialFire() &&
               !grid.getCell(r,c).hasFire() &&
                grid.getCell(r,c).isOpen();
    }

    public void moveBot(Cell bot, Cell button, double ship_flambility) {
        List<Cell> path = finding_path_for_bot_two(bot, button);
        for(Cell f_path:path){
            System.out.println(f_path.getRow() + " " + f_path.getCol());
        }

        Bot4 bot4 = new Bot4(grid);

        while(path.size() > 1) {
     
            Cell current = path.get(0);
           
            Cell next = path.get(1);
            current.setBot(false);
            next.setBot(true);
            
            List<Cell> adjOpenCells = fire.get_all_adj_open_neigbors_of_fire_cells();
            List<Cell> path_to_nearest_fire = bot4.find_path_to_nearest_fire_cell(current);
           // System.out.println("Path to nearest fire:");
            for(Cell cell:path_to_nearest_fire){
                System.out.println(cell.getRow() + " " + cell.getCol());
            }
            int fire_path_size = path_to_nearest_fire.size();
          //  System.out.println("Fire path size " + fire_path_size);
        
            fire.spread_fire(adjOpenCells, ship_flambility);
            if(next.hasBot() && next.hasButton()) {
                grid.printGrid();
           //     System.out.println("Current path:");

                path = finding_path_for_bot_two(next, button);
                System.out.println(path.size());
                for(Cell cell:path){
                    System.out.println(cell.getRow() + " " + cell.getCol());
                }

                int path_size = path.size();
                System.out.println("path size " + path_size);

                fire.extinguish_fire();
                grid.printGrid();
           //     System.out.println("Fire has been extinguished. Task completed.");
                break;
            }
            if(!path.isEmpty()){
                grid.printGrid();
                path = finding_path_for_bot_two(next, button);
                System.out.println("Current path:");
                for(Cell cell:path){
                    System.out.println(cell.getRow() + " " + cell.getCol());
                }
                System.out.println("Path size: " + path.size());
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