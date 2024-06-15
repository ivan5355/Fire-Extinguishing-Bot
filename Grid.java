import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Grid {
    private Cell[][] grid;

    private int rows;
    private int cols;
    private Cell initial_fire_cell;
    private Cell button_Cell;
    private Cell bot_Cell;
    private List<Cell> blockedCells_with_one_open_neighbour;

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

    public void setCell(int row, int col, Cell cell) {
        grid[row][col] = cell;
    }

    public List<Cell> get_blockedCells_with_one_open_neighbour() {
        if(blockedCells_with_one_open_neighbour.isEmpty()){
          // System.out.println("No more blocked cells with one open neighbour");
        }
        return blockedCells_with_one_open_neighbour;
    }

    public List<Cell> get_open_cells_with_one_open_neighbour() {
        List<Cell> openCells_with_one_open_neighbour = new ArrayList<Cell>();
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
        return openCells_with_one_open_neighbour;
    }
   
    public Cell getCell(int row, int col) {
        return grid[row][col];
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
        List<Cell> half_of_open_cells_with_open_neigCells = new ArrayList<Cell>();

        int half = cells.size()/2;

       // System.out.println("Half of open cells with open neigbors");
        for(int i = 0; i < half; i++){
             int index = (int) (Math.random() * cells.size());
           //  System.out.println(cells.get(index).getRow() + " " + cells.get(index).getCol());
             half_of_open_cells_with_open_neigCells.add(cells.get(index));
             cells.remove(index);
        }
        
      //  System.out.println(cells.size());
     //   System.out.println("**********");

       // System.out.println("Blocked neigbors opened of half of open cells with open neigbors");
        for(Cell cell: half_of_open_cells_with_open_neigCells){
            List<Cell> blocked_neigbors = get_all_blocked_neigbors_of_a_cell(cell);
            if(blocked_neigbors.size() > 0){
                int index = (int) (Math.random() * blocked_neigbors.size());
              //  System.out.println(blocked_neigbors.get(index).getRow() + " " + blocked_neigbors.get(index).getCol());
                blocked_neigbors.get(index).setOpen(true);
            }
        }
    }

    public List<Cell> get_all_open_cells() {
        List<Cell> openCells = new ArrayList<Cell>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c< cols; c++) {
               if(grid[r][c].isOpen() == true){
                    openCells.add(grid[r][c]);
                }
            }
        }
        return openCells;
    }


    public void printGrid() {
    // Determine the width for each cell based on the maximum number of digits in row/col indices
    int cellWidth = String.valueOf(Math.max(rows, cols)).length() + 1;
    String format = "%" + cellWidth + "s";

    // Print column indices header
    System.out.print("  ");
    for (int c = 0; c < cols; c++) {
        System.out.printf(format, c);
    }
    System.out.println();

    // Print grid with row indices
    for (int r = 0; r < rows; r++) {
        System.out.printf(format, r);  // Print row index

        for (int c = 0; c < cols; c++) {
            if (grid[r][c].hasButton()) {
                System.out.printf(format, "B");
            } else if (grid[r][c].hasFire()) {
                System.out.printf(format, "*");
            } else if (grid[r][c].hasBot()) {
                System.out.printf(format, "T");
            } else if (grid[r][c].isOpen()) {
                System.out.printf(format, "O");
            } else {
                System.out.printf(format, "X");
            }
        }
        System.out.println();
    }
}

    
    public Cell getInitialFireCell() {
        return initial_fire_cell;
    }

    public Cell getButtonCell() {
        return button_Cell;
    }


    public Cell getBotCell() {
        return bot_Cell;
    }

    public int getRows()
    {
        return rows;
    }

    public int getCols()
    {
        return cols;
    }

    public void generate_unique_random_fire_button_and_bot_cells(List<Cell> openCells) {
        int index = (int) (Math.random() * openCells.size());
        openCells.get(index).setButton(true);
        button_Cell = openCells.get(index);
        openCells.remove(index);

        int index2 = (int) (Math.random() * openCells.size());
        openCells.get(index2).setFire(true);
        openCells.get(index2).setInitialFire(true);
        initial_fire_cell = openCells.get(index2);
        openCells.remove(index2);

        int index3 = (int) (Math.random() * openCells.size());
        openCells.get(index3).setBot(true);
        bot_Cell = openCells.get(index3);
        openCells.remove(index3);
    }

    public List<Cell> get_all_adj_open_neigbors_of_bot_cells(Cell cell) {
        List<Cell> open_neigbors = new ArrayList<Cell>();
        int r = cell.getRow();
        int c = cell.getCol();

        if(r-1 >= 0 && grid[r-1][c].isOpen() == true){
            open_neigbors.add(grid[r-1][c]);
        }
        if(r+1 < rows && grid[r+1][c].isOpen() == true){
            open_neigbors.add(grid[r+1][c]);
        }
        if(c-1 >= 0 && grid[r][c-1].isOpen() == true){
            open_neigbors.add(grid[r][c-1]);
        }
        if(c+1 < cols && grid[r][c+1].isOpen() == true){
            open_neigbors.add(grid[r][c+1]);
        }

        return open_neigbors;
        
    }
   
    public Cell[][] getGrid() {
        return grid;
    }
    public static void main(String[] args) {

        //Bot1
        System.out.println("Bot1 Test");
        Grid test_grid = new Grid(15, 15);
        test_grid.get_all_cells_with_exactly_one_open_neighbour();
        while(!test_grid.blockedCells_with_one_open_neighbour.isEmpty()){
            test_grid.get_all_cells_with_exactly_one_open_neighbour();
            test_grid.randomly_open_one_of_adjcells(test_grid.get_blockedCells_with_one_open_neighbour());
        }
        List<Cell> openCells_with_one_open_neighbour = test_grid.get_open_cells_with_one_open_neighbour();
        test_grid.randomly_select_half_open_cells_with_one_open_neighbour_and_open_blocked_neigbor(openCells_with_one_open_neighbour);
        List<Cell> openCells = test_grid.get_all_open_cells();

        test_grid.generate_unique_random_fire_button_and_bot_cells(openCells); 
        Fire fire = new Fire(test_grid);
        Bot1 bot1 = new Bot1(test_grid, fire);
        List<Cell> path = bot1.finding_path_for_bot_one(test_grid.getBotCell(), test_grid.getButtonCell());
        test_grid.printGrid();
        System.out.println("Path for Bot");
        for(Cell cell: path){
            System.out.println(cell.getRow() + " " + cell.getCol());
        }
    
        double[] q_values = new double[10];
        for(int i = 0; i<q_values.length; i++){
            double q = Math.random();
            q_values[i] = q;
        }  
         
        for(int i = 0; i<q_values.length; i++){
            int success = 0;
            for(int j = 0; j<20; j++){
                Grid grid = new Grid(15, 15);
                grid.get_all_cells_with_exactly_one_open_neighbour();
                while(!grid.blockedCells_with_one_open_neighbour.isEmpty()){
                    grid.get_all_cells_with_exactly_one_open_neighbour();
                    grid.randomly_open_one_of_adjcells(grid.get_blockedCells_with_one_open_neighbour());
                }
                openCells_with_one_open_neighbour = grid.get_open_cells_with_one_open_neighbour();
                grid.randomly_select_half_open_cells_with_one_open_neighbour_and_open_blocked_neigbor(openCells_with_one_open_neighbour);
                openCells = grid.get_all_open_cells();

                grid.generate_unique_random_fire_button_and_bot_cells(openCells); 
                fire = new Fire(grid);
                bot1 = new Bot1(grid, fire);
                path = bot1.finding_path_for_bot_one(grid.getBotCell(), grid.getButtonCell());
                if(!path.isEmpty()){
                success++;
                }
            }
            System.out.println("Success rate for q value " + q_values[i] + " is " + success/20);


         //Bot2 
          System.out.println("Bot2 Test");
          Grid grid2 = new Grid(15, 15);
          grid2.get_all_cells_with_exactly_one_open_neighbour();
          while(!grid2.blockedCells_with_one_open_neighbour.isEmpty()){
                grid2.get_all_cells_with_exactly_one_open_neighbour();
                grid2.randomly_open_one_of_adjcells(grid2.get_blockedCells_with_one_open_neighbour());
           }
           List<Cell> openCells_with_one_open_neighbour2 = grid2.get_open_cells_with_one_open_neighbour();
           grid2.randomly_select_half_open_cells_with_one_open_neighbour_and_open_blocked_neigbor(openCells_with_one_open_neighbour2);
           List<Cell> openCells2 = grid2.get_all_open_cells();

          grid2.generate_unique_random_fire_button_and_bot_cells(openCells2); 
          Fire fire2 = new Fire(grid2);
          Bot2 bot2 = new Bot2(grid2, fire2);
          System.out.println("Bot2 path");
          bot2.moveBot(grid2.getBotCell(), grid2.getButtonCell(), 0.7);

        //Bot3 Test
        System.out.println("Bot3 Test");
        Grid grid3 = new Grid(15, 15);
        grid3.get_all_cells_with_exactly_one_open_neighbour();
        while (!grid3.blockedCells_with_one_open_neighbour.isEmpty()) {
            grid3.get_all_cells_with_exactly_one_open_neighbour();
            grid3.randomly_open_one_of_adjcells(grid3.get_blockedCells_with_one_open_neighbour());
        }
        List<Cell> openCells_with_one_open_neighbour3 = grid3.get_open_cells_with_one_open_neighbour();
        grid3.randomly_select_half_open_cells_with_one_open_neighbour_and_open_blocked_neigbor(openCells_with_one_open_neighbour3);
        List<Cell> openCells3 = grid3.get_all_open_cells();

        grid3.generate_unique_random_fire_button_and_bot_cells(openCells3);
       
        Fire fire3 = new Fire(grid3);
        Bot3 bot3 = new Bot3(grid3, fire3);
        bot3.movin_the_bot(grid3.getBotCell(), grid3.getButtonCell(), 0.7);


        }
    }

    public Cell[][] copyGridState() {
        Cell[][] copiedGrid = new Cell[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                copiedGrid[r][c] = new Cell(r, c);
                copiedGrid[r][c].setOpen(grid[r][c].isOpen());
                copiedGrid[r][c].setFire(grid[r][c].hasFire());
                copiedGrid[r][c].setButton(grid[r][c].hasButton());
                copiedGrid[r][c].setBot(grid[r][c].hasBot());
            }
        }
        return copiedGrid;
    }

    public void loadGridState(Cell[][] savedGrid) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c].setOpen(savedGrid[r][c].isOpen());
                grid[r][c].setFire(savedGrid[r][c].hasFire());
                grid[r][c].setButton(savedGrid[r][c].hasButton());
                grid[r][c].setBot(savedGrid[r][c].hasBot());
            }
        }
    }


}