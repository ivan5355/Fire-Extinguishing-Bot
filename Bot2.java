import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Bot2 implements FireEscapeBot{
    private Grid grid;
    private Fire fire;


    public Bot2(Grid grid, Fire fire) {
        this.grid = grid;
        this.fire = fire;
    }

     //finding the path for the bot using BFS
     public List<Cell> finding_path_for_bot_two(Cell beginning, Cell ending)
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

    //remaking the path for the cells
    private LinkedList<Cell> create_path(Cell ending)
    {
        LinkedList<Cell> new_cell_path = new LinkedList<>();
        for (Cell i = ending; i != null; i = i.getParent_of_the_Cell()) 
        {
            new_cell_path.addFirst(i);
        }
        return new_cell_path;
    }

    //checking if it is safe to visit the cell
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

    //move the bot
    public void move_bot(double ship_flambility) {
        Cell bot = grid.getBotCell();
        Cell button = grid.getButtonCell();

        List<Cell> path = finding_path_for_bot_two(bot, button);
        for(Cell cell:path){
            System.out.println(cell.getRow() + " " + cell.getCol());
        }
        
        while(path.size() > 1) {

            Cell current = path.get(0);
            Cell next = path.get(1);
            current.setBot(false);
            next.setBot(true);
            grid.setBotCell(button);
            
            List<Cell> adjOpenCells = fire.get_all_adj_open_neigbors_of_fire_cells();
            fire.spread_fire(adjOpenCells, ship_flambility);
            
            if(next.hasBot() && next.hasButton()) {
                grid.printGrid();
                System.out.println("Current path:");
                path = finding_path_for_bot_two(next, button);
                for(Cell cell:path){
                    System.out.println(cell.getRow() + " " + cell.getCol());
                }
                fire.extinguish_fire();
                System.out.println("Fire has been extinguished. Task completed.");
                break;
            }
            if(!path.isEmpty()){
                grid.printGrid();
                path = finding_path_for_bot_two(next, button);
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