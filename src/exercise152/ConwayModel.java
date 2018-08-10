package exercise152;

/**
 * this class implements the model part of the MVC pattern for Conway's model.
 */
public class ConwayModel {
    
    private final int n;
    private final boolean[][] lifeMatrix;

    /**
     * initializes the model's underlying 2d square matrices.
     * 
     * @param n the side size of the matrix
     */    
    public ConwayModel(int n) {
        this.n = n;
        this.lifeMatrix = new boolean[n][n];
    }

    /**
     * returns the state of t he cell: true for alive, false otherwise.
     * 
     * @param x row index
     * @param y column index
     * @return the state of the cell
     */    
    public boolean isAlive(int x, int y){
        return this.lifeMatrix[x][y];
    }    
    
    /**
     * sets the state of the cell in the life matrix.
     * 
     * @param x row index
     * @param y column index
     * @param isAlive the desired state of the cell
     */    
    public void setAlive(int x, int y, boolean isAlive){
        this.lifeMatrix[x][y] = isAlive;
    }

    /**
     * counts the "true" values of the direct neighbors of the cell in 
     * the "lifeMatrix", these values range from 3 up to 8, and the method
     * can handle cells located on the "borders" by calculating the correct
     * index range for the given cell.
     * 
     * @param x row index
     * @param y column index
     * @return the count of living neighbors of the given cell.
     */    
    public int countLivingNeighbors(int x, int y){
        int output = 0;
        int startIndexH = (x - 1 < 0) ? x : x - 1;
        int startIndexV = (y - 1 < 0) ? y : y - 1;
        int endIndexH = (x + 1 >= n) ? x : x + 1;
        int endIndexV = (y + 1 >= n) ? y : y + 1;
        for(int i = startIndexH; i <= endIndexH; i++)
            for(int j = startIndexV; j <= endIndexV; j++)
                if (this.lifeMatrix[i][j])
                    output++;
        if (this.lifeMatrix[x][y]) output--;
        return output;
    }
    
}