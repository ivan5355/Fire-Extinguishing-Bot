import java.util.ArrayList;
import java.util.List;

public class Grid {
    private Cell[][] grid;
    private int rows;
    private int cols;
    private List<Cell> blockedCells_with_one_open_neighbour = new ArrayList<Cell>();
    private List<Cell> openCells_with_one_open_neighbour = new ArrayList<Cell>();

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.blockedCells_with_one_open_neighbour = new ArrayList<Cell>();
        this.openCells_with_one_open_neighbour = new ArrayList<Cell>();
        grid = new Cell[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new Cell(r, c);
            }
        }

        //opens inital cell
        int row = (int) (Math.random() * rows);
        int col = (int) (Math.random() * cols);
        grid[row][col].setOpen(true);
    }

    public void  get_all_cells_with_exactly_one_open_neighbour() {
       blockedCells_with_one_open_neighbour.clear();
        
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c< cols; c++) {
               if(grid[r][c].isOpen() == false){
                     int count = 0;
                     if(r-1 >= 0 && grid[r-1][c].isOpen() == true){
                          count++;
                     }
                     if(r+1 < rows && grid[r+1][c].isOpen() == true){
                          count++;
                     }
                     if(c-1 >= 0 && grid[r][c-1].isOpen() == true){
                          count++;
                     }
                     if(c+1 < cols && grid[r][c+1].isOpen() == true){
                          count++;
                     }
                     if(count == 1){
                          blockedCells_with_one_open_neighbour.add(grid[r][c]);
                     }
                }
            }

        }
    }

    public void randomly_open_one_of_adjcells(List<Cell> cells) {
       
        if (cells.isEmpty()) {
            return;
        }
        int index = (int) (Math.random() * cells.size());
        blockedCells_with_one_open_neighbour.get(index).setOpen(true);
    }

    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    public void setCell(int row, int col, Cell cell) {
        grid[row][col] = cell;
    }

    public List<Cell> get_blockedCells_with_one_open_neighbour() {
        if(blockedCells_with_one_open_neighbour.isEmpty()){
           System.out.println("No more blocked cells with one open neighbour");
        }
        return blockedCells_with_one_open_neighbour;
    }



    public void get_opencells_with_one_open_neighbour() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c< cols; c++) {
               if(grid[r][c].isOpen() == true){
                     int count = 0;
                     if(r-1 >= 0 && grid[r-1][c].isOpen() == true){
                          count++;
                     }
                     if(r+1 < rows && grid[r+1][c].isOpen() == true){
                          count++;
                     }
                     if(c-1 >= 0 && grid[r][c-1].isOpen() == true){
                          count++;
                     }
                     if(c+1 < cols && grid[r][c+1].isOpen() == true){
                          count++;
                     }
                     if(count == 1){
                            openCells_with_one_open_neighbour.add(grid[r][c]);
                     }
                }
            }
        }
    }
   

    public List<Cell> get_all_blocked_neigbors_of_a_cell(Cell cell) {
        List<Cell> blocked_neigbors = new ArrayList<Cell>();
        int r = cell.getRow();
        int c = cell.getCol();

        if(r-1 >= 0 && grid[r-1][c].isOpen() == false){
            blocked_neigbors.add(grid[r-1][c]);
        }
        if(r+1 < rows && grid[r+1][c].isOpen() == false){
            blocked_neigbors.add(grid[r+1][c]);
        }
        if(c-1 >= 0 && grid[r][c-1].isOpen() == false){
            blocked_neigbors.add(grid[r][c-1]);
        }
        if(c+1 < cols && grid[r][c+1].isOpen() == false){
            blocked_neigbors.add(grid[r][c+1]);
        }

        return blocked_neigbors;
    }

    public void randomly_select_half_open_cells_with_one_open_neighbour_and_open_blocked_neigbor(List<Cell> cells) {
        for(int i = 0; i < cells.size()/2; i++){
             int index = (int) (Math.random() * cells.size());
             System.out.println(cells.get(index).getRow() + " " + cells.get(index).getCol());
             cells.add(openCells_with_one_open_neighbour.get(index));
        }
        
        System.out.println(cells.size());
        System.out.println("**********");

        for(Cell cell: cells){
            List<Cell> blocked_neigbors = get_all_blocked_neigbors_of_a_cell(cell);
            if(blocked_neigbors.size() > 0){
                int index = (int) (Math.random() * blocked_neigbors.size());
                blocked_neigbors.get(index).setOpen(true);
    
            }
        }
        
     }

    
   public void printGrid() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c].isOpen()) {
                    System.out.print("O");
                } else {
                    System.out.print("X");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
       Grid grid = new Grid(10, 10);
    
       grid.get_all_cells_with_exactly_one_open_neighbour();

    //    for(Cell cell: grid.blockedCells_with_one_open_neighbour){
    //         System.out.println(cell.getRow() + " " + cell.getCol());
    //     }

        while(!grid.blockedCells_with_one_open_neighbour.isEmpty()){
            grid.get_all_cells_with_exactly_one_open_neighbour();
            grid.randomly_open_one_of_adjcells(grid.get_blockedCells_with_one_open_neighbour());
        }
        
        grid.printGrid();

        grid.get_opencells_with_one_open_neighbour();

       for(Cell cell: grid.openCells_with_one_open_neighbour){
            System.out.println(cell.getRow() + " " + cell.getCol());
         }

        //unable to test it
       // grid.randomly_select_half_open_cells_with_one_open_neighbour_and_open_blocked_neigbor(grid.openCells_with_one_open_neighbour);
 
        // grid.printGrid();
    }

}