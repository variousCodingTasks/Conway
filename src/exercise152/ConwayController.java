package exercise152;

import java.util.Random;
import java.security.SecureRandom;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * implements the controller part of the MVC pattern of Conway's model.
 */
public class ConwayController {
    
    private final int n;
    private final ConwayModel model;
    private final ConwayView view;
    private final Random rand;
    
    private final Lock updateStateLock = new ReentrantLock();
    private final Condition canReadState = updateStateLock.newCondition();
    private final Condition canUpdateState = updateStateLock.newCondition();
    private int threadsCounter;
    
    /**
     * assigns and creates the different fields of this class.
     * 
     * @param n the side size of the cell matrix
     * @param model the model part of the pattern
     * @param view the view part of the pattern
     */
    public ConwayController(int n, ConwayModel model, ConwayView view) {
        this.n = n;
        this.model = model;
        this.view = view;
        this.rand = new SecureRandom();
    }
    
    /**
     * initializes a game by assigning a random state (with equal probabilities),
     * then updates the data structure in model. also makes sure that the
     * thread counter used by other methods is also set to 0;
     */
    public void initializeGame(){
        this.threadsCounter = 0;
        for(int i = 0; i < this.n; i++){
            for(int j = 0; j < this.n; j++){
                if(this.rand.nextInt(2) == 1)
                    this.model.setAlive(i, j, true);
                else
                    this.model.setAlive(i, j, false);
            }
        }
    }

    /**
     * calculates the cell's state based on its neighbors states. the method
     * makes sure no other thread updates the model by acquiring a lock on the
     * objects updateStateLock. each call increments the threads counter which
     * set to 0 at the beginning of this iteration, when the last thread calculates
     * its state (the n*n th thread), all the other threads are signaled and the
     * counter is reset, any other thread will await otherwise on the read condition,
     * in other words, all the threads will wait until the last of them has read
     * the surrounding cells current states and calculated its own next generation
     * state.
     * 
     * @param i row index
     * @param j column index
     * @return the of the cell for the next generation
     * @throws InterruptedException 
     */
    public boolean calculateCellState(int i, int j) throws InterruptedException{        
        updateStateLock.lock();
        boolean nextGenState = false;        
        try {
            boolean currentState = this.model.isAlive(i, j);
            int neighborsCount = model.countLivingNeighbors(i, j);
            if (!currentState &&  neighborsCount == 3) nextGenState = true;
            else if (currentState && (neighborsCount <= 1 || neighborsCount >= 4)) nextGenState = false;
            else nextGenState = currentState;
            this.threadsCounter++;        
            if (this.threadsCounter == n*n){
                canReadState.signalAll();
                this.threadsCounter = 0;
            }
            else canReadState.await();            
        }
        finally {
            updateStateLock.unlock();
            return nextGenState;
        }
    }
    
    /**
     * this method should be called by the threads once all of the have
     * finished calculating their states: it updates the thread's state
     * in the model and view components of the MVC model. this method
     * is synchronized and acquires a lock on the can updateStateLock, in
     * order to prevent threads from proceeding to the next iteration before
     * all the other threads have finished. the last thread will wake all 
     * the waiting threads on the lock condition and resets the threads counter.
     * the reset clickedReset flag is also checked: if it was, the flag is
     * cleared for the next iterations, the games state is initialized and
     * the reset button is enabled when all is done.
     * 
     * @param i row index
     * @param j column index
     * @param state the state of the cell in the next generation
     * @return the clickedExit flag from the view class, to notify
     * the thread in case it needs to terminate.
     * @throws InterruptedException 
     */
    public boolean updateCellState(int i, int j, boolean state) throws InterruptedException {
        updateStateLock.lock();
        try {
            model.setAlive(i, j, state);
            view.setSiteState(i, j, state);            
            this.threadsCounter++;            
            if (this.threadsCounter == n*n){
                if (this.view.isClickedReset()){
                    this.view.setClickedReset(false);
                    this.initializeGame();
                    this.view.enableResetButton();
                }
                canUpdateState.signalAll();
                this.threadsCounter = 0;
            }
            else
                canUpdateState.await();             
        }
        finally {
            updateStateLock.unlock();
        }
        return this.view.isClickedExit();
    }
    
}
