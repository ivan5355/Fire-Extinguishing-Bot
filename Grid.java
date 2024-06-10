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

        System.out.println("Half of open cells with open neigbors");
        for(int i = 0; i < half; i++){
             int index = (int) (Math.random() * cells.size());
             System.out.println(cells.get(index).getRow() + " " + cells.get(index).getCol());
             half_of_open_cells_with_open_neigCells.add(cells.get(index));
             cells.remove(index);
        }
        
        System.out.println(cells.size());
        System.out.println("**********");

        System.out.println("Blocked neigbors opened of half of open cells with open neigbors");
        for(Cell cell: half_of_open_cells_with_open_neigCells){
            List<Cell> blocked_neigbors = get_all_blocked_neigbors_of_a_cell(cell);
            if(blocked_neigbors.size() > 0){
                int index = (int) (Math.random() * blocked_neigbors.size());
                System.out.println(blocked_neigbors.get(index).getRow() + " " + blocked_neigbors.get(index).getCol());
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
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if(grid[r][c].hasButton()){
                    System.out.print("B");
                } else if(grid[r][c].hasFire()){
                    System.out.print("F");
                } else if(grid[r][c].hasBot()){
                    System.out.print("T");
                }
                else if (grid[r][c].isOpen()) {
                    System.out.print("O");
                }
                else{
                    System.out.print("X");
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

       List<Cell> openCells_with_one_open_neighbour = grid.get_open_cells_with_one_open_neighbour();

       System.out.println("Open cells with one open neighbour");
       for(Cell cell: openCells_with_one_open_neighbour){
            System.out.println(cell.getRow() + " " + cell.getCol());
         }

         System.out.println(openCells_with_one_open_neighbour.size());

         System.out.println("**********");

        
        grid.randomly_select_half_open_cells_with_one_open_neighbour_and_open_blocked_neigbor(openCells_with_one_open_neighbour);
 
        List<Cell> openCells = grid.get_all_open_cells();
        grid.generate_unique_random_fire_button_and_bot_cells(openCells); 

    

        grid.printGrid();
        System.out.println("Fire Cell: "+grid.getInitialFireCell().getRow() + " " + grid.getInitialFireCell().getCol());  
        System.out.println("Buttton Cell: "+grid.getButtonCell().getRow() + " " + grid.getButtonCell().getCol());
        System.out.println("Bot Cell: "+grid.getBotCell().getRow() + " " + grid.getBotCell().getCol());
        
        System.out.println("Bot1 path");
        List<Cell> path = grid.finding_path_for_bot_one(grid.getBotCell(), grid.getButtonCell());
        for(Cell cell: path){
            System.out.println(cell.getRow() + " " + cell.getCol());
        }
    
    }

// • Bot 1 - This bot plans the shortest path to the button, avoiding the initial fire cell,
// and then executes that
// plan. The spread of the fire is ignored by the bot

    //im using bfs for bot 1 (i think i could also use djikstra alg tho)
    public List<Cell> finding_path_for_bot_one(Cell beginning, Cell ending)
    {
        // HashMap<Cell, Cell> remaking_the_path_for_the_cells = new HashMap<>();
        boolean[][] seen_these_cells = new boolean[rows][cols];
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
                    
                    Cell look_at_upcoming_cell = grid[r][c];  
                   
                    look_at_upcoming_cell.setParent_of_the_cell(the_present_cell);     
                   
                    lineup_of_the_cells.add(look_at_upcoming_cell);         

                }
            }
        }
        return new LinkedList<>();  
    }

    private List<Cell> remaking_the_path_for_the_cells(Cell ending)
    {
        LinkedList<Cell> new_cell_path = new LinkedList<>();
        for (Cell i = ending; i != null; i = i.getParent_of_the_Cell()) 
        {
            new_cell_path.addFirst(i);
        }
        return new_cell_path;
        
    }

    private boolean is_it_safe_to_visit_the_cel(int r, int c, boolean[][]seen_these_cells)
    {
        return r >= 0 &&
               r < rows && 
               c >= 0 && 
               c < cols && 
               !seen_these_cells[r][c] && 
               !grid[r][c].hasInitialFire() &&
               grid[r][c].isOpen();
    }

}