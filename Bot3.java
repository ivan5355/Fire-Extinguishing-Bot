import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

public class Bot3 implements FireEscapeBot
{
    private Grid  grid;
    private Fire fire;


    public Bot3(Grid grid, Fire fire) 
    {
        this.grid = grid;
        this.fire = fire;
    }

    //uses BFS to find the shortest path to the button
    public List<Cell> finding_path_for_bot_3(Cell beginning, Cell ending)
    {
        
        Set<Cell> seen_these_cells = new HashSet<>();
        Queue<Cell> lineup_of_the_cells = new LinkedList<>();

        seen_these_cells.add(beginning);
        beginning.setParent_of_the_cell(null);
        lineup_of_the_cells.add(beginning);

        while(!lineup_of_the_cells.isEmpty())
        {
            Cell the_present_cell = lineup_of_the_cells.remove();

            if(the_present_cell.equals(ending))
            {
                 return create_path(ending);
            }
            
            //look thru the adjacent cells in the grid
            int[] indices_for_the_row = {-1, 1, 0, 0}; // changed
            int[] indices_for_the_colum = {0, 0, -1, 1}; // changed
            
            for(int i = 0 ; i < 4; i++)
            {
                int r = the_present_cell.getRow() + indices_for_the_row[i];
                int c = the_present_cell.getCol() + indices_for_the_colum[i];
                if(is_it_safe_to_visit_the_cell(r,c,seen_these_cells))
                {
                    seen_these_cells.add(grid.getCell(r,c));
                    
                    Cell look_at_upcoming_cell = grid.getCell(r,c);  
                   
                    look_at_upcoming_cell.setParent_of_the_cell(the_present_cell);     
                   
                    lineup_of_the_cells.add(look_at_upcoming_cell);         

                }
            }
        }
        return new LinkedList<>();  
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
               !fire.get_all_adj_open_neigbors_of_fire_cells().contains(grid.getCell(r,c)) &&
               grid.getCell(r,c).isOpen();
    }
 

    private List<Cell> create_path(Cell end) 
    {
        LinkedList<Cell> path_of_cells = new LinkedList<>();
        
        for (Cell i = end; i != null; i = i.getParent_of_the_Cell()) 
        {
            
            path_of_cells.addFirst(i);
        }
        return path_of_cells;
    }

    public void move_bot(double ship_flambility) {
        Cell bot = grid.getBotCell();
        Cell button = grid.getButtonCell();

        List<Cell> path = finding_path_for_bot_3(bot, button);
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

                System.out.println(bot.getRow() + " " + bot.getCol());
                System.out.println(button.getRow() + " " + button.getCol());
                
                fire.extinguish_fire();
                System.out.println("Fire has been extinguished. Task completed.");
                break;
            }
            if(!path.isEmpty()){
                grid.printGrid();
                path = finding_path_for_bot_3(next, button);
                System.out.println("Current path:");
                for(Cell cell:path){
                    System.out.println(cell.getRow() + " " + cell.getCol());
                }
            }
            if(bot.hasFire()) {
                grid.printGrid();
                System.out.println("Current path:");
                
                System.out.println("Bot caught on fire. Task failed.");
                break;
            }
            if(path.isEmpty()) {
                grid.printGrid();
                System.out.println("Task failed. No  path to button");
            }
        }
    }
}
