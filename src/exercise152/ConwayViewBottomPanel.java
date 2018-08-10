package exercise152;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * implements the button bottom panel of the view class, separated from
 * the main view class ConwayView, for convenience and clarity purposes.
 */
public class ConwayViewBottomPanel extends JPanel{

    private final ConwayView view;
    private final JButton resetButton;
    private final JButton closeButton;
    
    /**
     * creates and initializes the panels different fields and buttons,
     * also attaches event handlers for them.
     * 
     * @param view the view to which the panel will be attached.
     */
    public ConwayViewBottomPanel(ConwayView view){
        this.view = view;        
        this.resetButton = new JButton("Restart");
        resetButton.addActionListener(new resetButtonHandler());
        this.add(resetButton);        
        this.closeButton = new JButton("Close");
        closeButton.addActionListener(new closeButtonHandler());
        this.add(closeButton);           
    }
    
    /**
     * enables the reset button, making it possible for the user to press it
     * again, in case it was disabled.
     */
    public void enableResetButton(){
        this.resetButton.setEnabled(true);
    }
    
    /**
     * an action listener for the close button: when clicked, sets the flag
     * to true and causes the application to exit.
     */
    private class closeButtonHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event){
            ConwayViewBottomPanel.this.view.setClickedExit(true);
            System.exit(0);
        }
    }    

    /**
     * an action listener for the reset button, first it disables the button,
     * to prevent the user from pressing it again, while the threads have not
     * finished yet their current iteration, which might cause errors/inaccurate
     * state calculations, and sets the relevant flag to true as well.
     */
    private class resetButtonHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event){
            ConwayViewBottomPanel.this.resetButton.setEnabled(false);
            ConwayViewBottomPanel.this.view.setClickedReset(true);
        }
    }    
        
}
