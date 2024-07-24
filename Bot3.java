import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.ArrayList;

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
        
        boolean[][] seen_these_cells = new boolean[grid.getRows()][grid.getCols()];
        Queue<Cell> lineup_of_the_cells = new LinkedList<>();

        seen_these_cells[beginning.getRow()][beginning.getCol()] = true; 
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
                    seen_these_cells[r][c] = true;
                    
                    Cell look_at_upcoming_cell = grid.getCell(r,c);  
                   
                    look_at_upcoming_cell.setParent_of_the_cell(the_present_cell);     
                   
                    lineup_of_the_cells.add(look_at_upcoming_cell);         

                }
            }
        }
        return new LinkedList<>();  
    }
    
    private boolean is_it_safe_to_visit_the_cell(int r, int c, boolean[][]seen_these_cells)
    {
        return r >= 0 &&
               r < grid.getCols()&& 
               c >= 0 && 
               c < grid.getRows() && 
               !seen_these_cells[r][c] && 
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

    public void move_bot(double ship_flambility) 
    {
        Cell bot = grid.getBotCell();
        Cell button = grid.getButtonCell();
        
        List<Cell> path = finding_path_for_bot_3(bot, button);
        for(Cell cell:path){
            System.out.println(cell.getRow() + " " + cell.getCol());
        }
        
        for (Cell step : path) 
        {
            if (step.hasFire()) 
            {
                System.out.println("Bot met fire at " + step.getRow() + "," + step.getCol());
                
                break;
            } 
            else if(step.equals(button)){
                System.out.println("Bot reached the button at " + step.getRow() + "," + step.getCol());
            }
            else{
                System.out.println("Bot movin to " + step.getRow() + "," + step.getCol());
                
                grid.getCell(step.getRow(), step.getCol()).setBot(true);

                fire.spread_fire(fire.get_all_adj_open_neigbors_of_fire_cells(), ship_flambility);
                path = finding_path_for_bot_3(step, button);
                for(Cell cell:path){
                    System.out.println(cell.getRow() + " " + cell.getCol());
                }
                if(path.isEmpty())
                {
                    grid.printGrid();
                    System.out.println("No way for bot to reach the button");
                    break;
                }
                grid.printGrid();
            }
        }
    }
}
