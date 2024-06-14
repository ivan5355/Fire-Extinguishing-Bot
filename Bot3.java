import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.ArrayList;

public class Bot3 
{
    private Grid  grid;
    private Fire fire;

    public Bot3(Grid grid, Fire fire) 
    {
        this.grid = grid;
        this.fire = fire;
    }

    public List<Cell> pat_for_bot_3(Cell beginning, Cell ending) 
    {
        boolean[][] seen_these_cells = new boolean[grid.getRows()][grid.getCols()];

        Queue<Cell> lineup_of_the_cells = new LinkedList<>();
        List<Cell> pat_of_cels = new LinkedList<>();

        seen_these_cells[beginning.getRow()][beginning.getCol()] = true;
       
        beginning.setParent_of_the_cell(null);
        lineup_of_the_cells.add(beginning);

        while (!lineup_of_the_cells.isEmpty()) 
        {
            Cell current = lineup_of_the_cells.poll();

                if (current.equals(ending)) 
                {
                    return changepatofcels(current);
                }

            List<Cell> neighbors = grid.get_all_adj_open_neigbors_of_bot_cells(current);
            for (Cell neighbor : neighbors) 
            {
                if (!seen_these_cells[neighbor.getRow()][neighbor.getCol()] && safey(neighbor)) 
                {
                    seen_these_cells[neighbor.getRow()][neighbor.getCol()] = true;
                    
                    neighbor.setParent_of_the_cell(current);      
                    lineup_of_the_cells.add(neighbor);
                }
            }
        }

        return pat_of_cels; 
    }

    private boolean safey(Cell cell) 
    {
        
        return !fire.get_all_adj_open_neigbors_of_fire_cells().contains(cell) && cell.isOpen();
    }

    private List<Cell> changepatofcels(Cell end) 
    {
        LinkedList<Cell> path_ofcells = new LinkedList<>();
        
        for (Cell i = end; i != null; i = i.getParent_of_the_Cell()) 
        {
            
            path_ofcells.addFirst(i);
        }
        return path_ofcells;
    }

    public void movin_the_bot(Cell botCell, Cell buttonCell, double ship_flambility) 
    {
        List<Cell> path = pat_for_bot_3(botCell, buttonCell);
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
            else if(step.equals(buttonCell)){
                System.out.println("Bot reached the button at " + step.getRow() + "," + step.getCol());
            }
            else{
                System.out.println("Bot movin to " + step.getRow() + "," + step.getCol());
                
                grid.getCell(step.getRow(), step.getCol()).setBot(true);

                fire.spread_fire(fire.get_all_adj_open_neigbors_of_fire_cells(), ship_flambility);
                path = pat_for_bot_3(step, buttonCell);
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
