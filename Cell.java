public class Cell {
    private boolean hasButton;
    private boolean isOpen;
    private boolean hasFire;
    private boolean hasBot;

    public Cell() {
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

    
}
