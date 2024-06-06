public class Cell {
    private boolean hasButton;
    private boolean isOpen;
    private boolean hasFire;
    private boolean hasBot;
    private int row;
    private int col;

    public Cell(int r, int c) {
        this.row = r;
        this.col = c;
        hasButton = false;
        isOpen = false;
        hasFire = false;
        hasBot = false;
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


    
}
