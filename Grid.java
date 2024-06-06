import java.util.ArrayList;
import java.util.List;

public class Grid {
    private Cell[][] grid;
    private int rows;
    private int cols;
    private List<Cell> blockedCells_with_one_open_neighbour = new ArrayList<Cell>();
    

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.blockedCells_with_one_open_neighbour = new ArrayList<Cell>();
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

        while(!grid.blockedCells_with_one_open_neighbour.isEmpty()){
            grid.get_all_cells_with_exactly_one_open_neighbour();
            grid.randomly_open_one_of_adjcells(grid.get_blockedCells_with_one_open_neighbour());
        }
        
        grid.printGrid();

    }

}