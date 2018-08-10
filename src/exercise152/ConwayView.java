package exercise152;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;

/**
 * this class implements the view part of the MVC pattern for Conway's model.
 */
public class ConwayView extends JFrame{
    
    private final static int X_POSITION = 100;
    private final static int Y_POSITION = 100;
    private final static int WIDTH = 800;
    private final static int HEIGHT = 800;
    private final static int GAP = 5;
    
    private final JPanel panel;
    private final JLabel[][] grid;
    private final ConwayViewBottomPanel conwayViewBottomPanel;
    
    private boolean clickedExit = false;
    private boolean clickedReset = false;

    /**
     * this constructor creates a panel and places it on the JFrame (this object)
     * it also sets its dimensions and position by calling "setBounds" on the
     * classes constants. it creates a 2d array to store the JLabels which will
     * represent the cells, each of which is added to the grid view (of size n) 
     * this constructor creates as well. the constructor also creates and adds a
     * ConwayViewBottomPanel to hold the buttons and their action listeners.
     * 
     * @param n is the side size of the square matrix which contains the cells
     */    
    public ConwayView(int n){
        super("Conway's Game of Life");
        this.setLayout(new BorderLayout(GAP, GAP));
        this.panel = new JPanel();
        this.panel.setLayout(new GridLayout(n, n));
        this.getContentPane().add(this.panel, BorderLayout.CENTER);
        this.grid = new JLabel[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                this.grid[i][j] = new JLabel();
                this.grid[i][j].setBorder(new LineBorder(Color.BLACK));
                this.panel.add(this.grid[i][j]);
            }
        }
        this.conwayViewBottomPanel = new ConwayViewBottomPanel(this);
        this.add(conwayViewBottomPanel, BorderLayout.SOUTH);
        this.setBounds(this.X_POSITION, this.Y_POSITION, this.WIDTH, this.HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * sets the color of cell at indexes (x,y): White represents
     * a living cell, Gray otherwise.
     * 
     * @param x the row index of the cell
     * @param y the column index of the cell
     * @param isAlive indicates if the state of the cell corresponds to "alive"
     */    
    public void setSiteState(int x, int y, boolean isAlive){
        if (isAlive)
            this.grid[x][y].setBackground(Color.GRAY);
        else
            this.grid[x][y].setBackground(Color.WHITE);
        this.grid[x][y].setOpaque(true);        
    }
    
    /**
     * enables the reset bottom on the bottom panel, in case it's disabled.
     */
    public void enableResetButton(){
        this.conwayViewBottomPanel.enableResetButton();
    }

    /**
     * getter for this.clickedExit.
     * 
     * @return true if the exit button was clicked, false otherwise.
     */
    public boolean isClickedExit() {
        return this.clickedExit;
    }

    /**
     * setter for this.clickedExit.
     * 
     * @param clickedExit true for clicked, false otherwise.
     */
    public void setClickedExit(boolean clickedExit) {
        this.clickedExit = clickedExit;
    }

    /**
     * getter for this.clickedReset.
     * 
     * @return true if reset button was clicked, false otherwise.
     */
    public boolean isClickedReset() {
        return this.clickedReset;
    }

    /**
     * setter for this.clickedReset.
     * 
     * @param clickedReset true for clicked, false otherwise.
     */
    public void setClickedReset(boolean clickedReset) {
        this.clickedReset = clickedReset;
    }
    
    

}