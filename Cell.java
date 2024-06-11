public class Cell {
    private boolean hasButton;
    private  boolean isOpen;
    private boolean hasFire;
    private boolean hasBot;
    private boolean has_initial_fire;
    private int row;
    private int col;
    private Cell parent_of_the_Cell;

    public Cell(int r, int c) {
        this.row = r;
        this.col = c;
        hasButton = false;
        isOpen = false;
        hasFire = false;
        hasBot = false;
        has_initial_fire = false;
    }

    public boolean hasInitialFire() {
        return has_initial_fire;
    }

    public void setInitialFire(boolean has_initial_fire) {
        this.has_initial_fire = has_initial_fire;
    }

    public boolean hasButton() {
        return hasButton;
    }

    public void setButton(boolean hasButton) {
        this.hasButton = hasButton;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public boolean hasFire() {
        return hasFire;
    }

    public void setFire(boolean hasFire) {
        this.hasFire = hasFire;
    }

    public boolean hasBot() {
        return hasBot;
    }

    public void setBot(boolean hasBot) {
        this.hasBot = hasBot;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Cell getParent_of_the_Cell() {
        return parent_of_the_Cell;
    }
    
    public void setParent_of_the_cell(Cell parent_of_the_Cell) {
        this.parent_of_the_Cell = parent_of_the_Cell;
    }


    
}
