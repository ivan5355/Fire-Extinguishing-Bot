import java.util.ArrayList;
import java.util.List;

public class Fire {
    Grid grid;

    public Fire(Grid grid) {
        this.grid = grid;
    }

    public List<Cell> getFireCells(){
        ArrayList<Cell> fire_cells = new ArrayList<Cell>();
        for(int i = 0; i < grid.getRows(); i++)
        {
            for(int j = 0; j < grid.getCols(); j++)
            {
                if(grid.getCell(i,j).hasFire())
                {
                    fire_cells.add(grid.getCell(i,j));
                }
            }
        }
        return fire_cells;
    }


    public List<Cell> get_all_adj_open_neigbors_of_fire_cells() {
        List<Cell> fire_cells = getFireCells();
        List<Cell> open_neigbors = new ArrayList<Cell>();
        for(Cell cell: fire_cells){
            int r = cell.getRow();
            int c = cell.getCol();

            if(r-1 >= 0 && grid.getCell(r-1,c).isOpen() == true){
                open_neigbors.add(grid.getCell(r-1,c));
            }
            if(r+1 < grid.getRows() && grid.getCell(r+1,c).isOpen() == true){
                open_neigbors.add(grid.getCell(r+1,c));
            }
            if(c-1 >= 0 && grid.getCell(r,c-1).isOpen() == true){
                open_neigbors.add(grid.getCell(r,c-1));
            }
            if(c+1 < grid.getCols() && grid.getCell(r,c+1).isOpen() == true){
                open_neigbors.add(grid.getCell(r,c+1));
            }
        }
        return open_neigbors;
    }

    public int get_number_of_adj_fire_cells(Cell cell){
        int r = cell.getRow();
        int c = cell.getCol();
        int count = 0;
        if(r-1 >= 0 && grid.getCell(r-1,c).hasFire() == true){
            count++;
        }
        if(r+1 < grid.getRows() && grid.getCell(r+1,c).hasFire() == true){
            count++;
        }
        if(c-1 >= 0 && grid.getCell(r, c-1).hasFire() == true){
            count++;
        }
        if(c+1 < grid.getCols() && grid.getCell(r, c+1).hasFire() == true){
            count++;
        }
        return count;
    }
     
    public void spread_fire(List<Cell> open_neigbors, double ship_flambility) {
        for (Cell cell: open_neigbors){
            int r = cell.getRow();
            int c = cell.getCol();
            double probaility_of_open_neigbor_cell_catching_fire = Math.pow(1 - (1 - ship_flambility), get_number_of_adj_fire_cells(cell));
            double random = Math.random();
            if(random < probaility_of_open_neigbor_cell_catching_fire){
                grid.getCell(r,c).setFire(true);
            }
        }
    }

}
