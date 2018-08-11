package exercise152;

/**
 * this class defines a thread object which will handle a single cell
 * in Conway's model.
 */
public class CellThread extends Thread{
    
    /*the wait time between consecutive generations*/
    public static final int SLEEP_TIME = 1000;
    
    private final int i;
    private final int j;
    private final ConwayController controller;
    
    /**
     * assigns the different fields.
     * 
     * @param i the horizontal position of the cell
     * @param j the vertical position of the cell
     * @param controller the controller part of the MVC model
     */
    public CellThread(int i, int j, ConwayController controller){
        super();
        this.i = i;
        this.j = j;
        this.controller = controller;
    }
    
    /**
     * the run method which defines the thread's operation. defines an
     * infinite loops which exits when exitClicked is set to false, this
     * is to make sure that the thread terminates if the system exit command
     * does not manage to close it for some reason. in each iteration, the
     * thread sleeps for a predefined period of time, to make sure the user
     * seas the updates made from last generation, it then calculates the cell's
     * next generation state based on its neighbors states and the updates its
     * own space based on that.
     */
    @Override
    public void run(){
        try {
            boolean state = false;
            boolean exitClicked = false;
            while(!exitClicked){
                Thread.sleep(SLEEP_TIME);
                state = controller.calculateCellState(this.i, this.j);
                exitClicked = controller.updateCellState(this.i, this.j, state);
            }
        }
        catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
    
}
