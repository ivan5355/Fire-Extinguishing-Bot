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
       
        grid = new Cell[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new Cell(r, c);
            }
        }

        setupGrid();
    }

    //sets up tgrid
    private void setupGrid() {

        //opens the initial cell
        int row = (int) (Math.random() * rows);
        int col = (int) (Math.random() * cols);
        grid[row][col].setOpen(true);

        List<Cell> all_blocked_cells_with_one_open_neigbor = get_all_blocked_cells_with_exactly_one_open_neighbour();
        while(!all_blocked_cells_with_one_open_neigbor.isEmpty()){
            all_blocked_cells_with_one_open_neigbor = get_all_blocked_cells_with_exactly_one_open_neighbour();
            randomly_open_one_of_adjcells(all_blocked_cells_with_one_open_neigbor);
        }

        List<Cell> openCells_with_one_open_neighbour = get_open_cells_with_one_open_neighbour();
        this.randomly_select_half_open_cells_with_one_open_neighbour_and_open_blocked_neigbor(openCells_with_one_open_neighbour);
        List<Cell> openCells = this.get_all_open_cells();
        this.generate_unique_random_fire_button_and_bot_cells(openCells);
        printGrid();
        
    }


    public List<Cell> get_all_blocked_cells_with_exactly_one_open_neighbour() {
       List<Cell> blockedCells_with_one_open_neighbour = new ArrayList<Cell>();
        
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
        return blockedCells_with_one_open_neighbour;
    }

    public void randomly_open_one_of_adjcells(List<Cell> cells) {
       
        if (cells.isEmpty()) {
            return;
        }
        int index = (int) (Math.random() * cells.size());
        cells.get(index).setOpen(true);
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


        for(int i = 0; i < half; i++){
             int index = (int) (Math.random() * cells.size());
             half_of_open_cells_with_open_neigCells.add(cells.get(index));
             cells.remove(index);
        }
        
        for(Cell cell: half_of_open_cells_with_open_neigCells){
            List<Cell> blocked_neigbors = get_all_blocked_neigbors_of_a_cell(cell);
            if(blocked_neigbors.size() > 0){
                int index = (int) (Math.random() * blocked_neigbors.size());
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
        System.out.print("     ");
        for (int c = 0; c < cols; c++) {
            System.out.printf(format, c);
        }
        System.out.println();
    
        // Print top border
        System.out.print("   ┌");
        for (int c = 0; c < cols; c++) {
            System.out.print("─".repeat(cellWidth));
            if (c < cols - 1) System.out.print("┬");
        }
        System.out.println("┐");
    
        // Print grid with row indices
        for (int r = 0; r < rows; r++) {
            System.out.printf("%2d │", r);  // Print row index
    
            for (int c = 0; c < cols; c++) {
                String cellContent;
                if (grid[r][c].hasButton()) {
                    cellContent = "B";
                } else if (grid[r][c].hasFire()) {
                    cellContent = "*";
                } else if (grid[r][c].hasBot()) {
                    cellContent = "T";
                } else if (grid[r][c].isOpen()) {
                    cellContent = "O";
                } else {
                    cellContent = "X";
                }
                System.out.printf(format, cellContent);
                if (c < cols - 1) System.out.print("│");
            }
            System.out.println("│");
    
            // Print row separator, except for the last row
            if (r < rows - 1) {
                System.out.print("   ├");
                for (int c = 0; c < cols; c++) {
                    System.out.print("─".repeat(cellWidth));
                    if (c < cols - 1) System.out.print("┼");
                }
                System.out.println("┤");
            }
        }
    
        // Print bottom border
        System.out.print("   └");
        for (int c = 0; c < cols; c++) {
            System.out.print("─".repeat(cellWidth));
            if (c < cols - 1) System.out.print("┴");
        }
        System.out.println("┘");
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

    public void setBotCell(Cell cell) {
         bot_Cell = cell;
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

        Grid grid = new Grid(25, 25);
        Fire fire = new Fire(grid);

        // //Bot 1 - This bot plans the shortest path to the button, avoiding the initial fire cell, and then executes thatplan. The spread of the fire is ignored by the bot
        // Bot1 bot1 = new Bot1(grid, fire);
        // bot1.move_bot(0.5);
        // System.out.print(grid.getBotCell().getRow() + " " + grid.getBotCell().getCol());
        // System.out.println();
        // System.out.println(grid.getButtonCell().getRow() + " " + grid.getButtonCell().getCol());
         
        // //Bot 2 - At every time step, the bot re-plans the shortest path to the button, avoiding the current fire cells,and then executes the next step in that plan.
        // grid = new Grid(25, 25);
        // fire = new Fire(grid);
        // Bot2 bot2 = new Bot2(grid, fire);
        // bot2.move_bot(0.5);
        // System.out.print(grid.getBotCell().getRow() + " " + grid.getBotCell().getCol());
        // System.out.println();
        // System.out.println(grid.getButtonCell().getRow() + " " + grid.getButtonCell().getCol());

       // Bot 3 - This bot plans the shortest path to the button, avoiding the current fire cells, and then executes the next step in that plan. The bot also considers the spread of the fire when planning its path.
        grid = new Grid(25, 25);
        fire = new Fire(grid);
        Bot3 bot3 = new Bot3(grid, fire);
        bot3.move_bot(0.8);
        System.out.print(grid.getBotCell().getRow() + " " + grid.getBotCell().getCol());
        System.out.println();
        System.out.println(grid.getButtonCell().getRow() + " " + grid.getButtonCell().getCol());

        //Bot 4 - Uses A* with distances heruisitc to find the shortest path to the button, avoiding the current fire cells, and then executes the next step in that plan. The bot also considers the spread of the fire when planning its path.
        // grid = new Grid(25, 25);
        // fire = new Fire(grid);
        // Bot4 bot4 = new Bot4(grid, fire);
        // bot4.move_bot(0.5);
        // System.out.print(grid.getBotCell().getRow() + " " + grid.getBotCell().getCol());
        // System.out.println();
        // System.out.println(grid.getButtonCell().getRow() + " " + grid.getButtonCell().getCol());

    } 
}

   

