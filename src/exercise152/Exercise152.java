package exercise152;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * demonstrates a multi threaded version of Conway's game of life,
 * based on MVC design pattern.
 */
public class Exercise152 {
    
    public static final int DEFAULT_SIZE = 10;
    public static final int LOWER_BOUND = 3;
    public static final int UPPER_BOUND = 100;

    /**
	 * prompts the user to enter his selection of the matrix side size.
     * creates and initializes the different parts of the MVC pattern
     * for Conway's game of life. then calls startThreads, which creates
     * and starts the threads for each of the cells.
     */		
    public static void main(String[] args) {
        int n = getUserInput();        
        ConwayView view = new ConwayView(n);
        ConwayModel model = new ConwayModel(n);
        ConwayController controller = new ConwayController(n , model, view);
        view.setVisible(true);
        controller.initializeGame();
		startThreads(controller, n);
    }

    /**
     * creates an ArrayList of threads, then adds adds n*n cell controlling
     * threads to it for each of the cells, then starts the threads which
     * update the model's state.
     * 
     * @param controller the controller part of the model
     */	
	public static void startThreads(ConwayController controller, Integer n){
		ArrayList<Thread> threads = new ArrayList<>();
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++){
                threads.add(new CellThread(i, j, controller));
            }
        for (Thread thread : threads)
            thread.start();		
	}

    /**
     * prompts the user to enter the dimension of the matrix. if user
     * presses cancel, then DEFAULT_SIZE is selected, the method does not
     * allow too high or too low values for the dimension so UPPER_BOUND
     * and LOWER_BOUND are selected in case the user entered a low or high
     * value than allowed. the notification will be displayed until the user
     * manages to enter a valid integer value.
     * 
     * @return the selected integer
     */
    public static int getUserInput(){
        String input;
        Integer selection;
        while(true){
            try {
                input = JOptionPane.showInputDialog(null, "Please enter the desired side size or press cancel for default size,\n"
                                        + "you should enter a valid integer between 3 and 100.");
                if (input == null) {
                    selection = DEFAULT_SIZE;
                }
                else {
                    selection = Integer.parseInt(input);
                    if (selection > UPPER_BOUND) selection = UPPER_BOUND;
                    else if (selection < LOWER_BOUND) selection = LOWER_BOUND;
                }
                break;
             }
            catch (NumberFormatException e){
                continue;
            }
        }
        return selection;
        
    }
    
}
