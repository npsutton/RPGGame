package FinalProjectTestRun;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

/**
 * Listener for the displayStatsButton
 */
public class statListener implements ActionListener, MouseListener
{
    private JLabel myLabel;
    private Hero myHero;
    
    public statListener(JLabel newLabel, Hero newHero)
    {
        super();
        
        myLabel = newLabel;
        myHero = newHero;
    }
    
    @Override
    public void mouseClicked(MouseEvent me)
    {
        
    }
    
    /**
     * If it's not the Hero's turn, causes the jLabel to change
     * and yell at the user to be patient.
     * @param me 
     */
    @Override
    public void mousePressed(MouseEvent me)
    {
        
    }
    
    /**
     * Changes the jLabel to revert back to the standard message
     * after yelling at the user.
     * @param me 
     */
    @Override
    public void mouseReleased(MouseEvent me)
    {
        
    }
    
    /**
     * Displays a description of what the button does to the JFrame
     * when the user hovers the mouse over it.
     * @param me 
     */
    @Override
    public void mouseEntered(MouseEvent me)
    {
        myLabel.setText("Displays Stats!");
    }
    
    /**
     * Reverts the text in the JLabel to the default text when the mouse
     * is not over the button.
     * @param me 
     */
    @Override
    public void mouseExited(MouseEvent me)
    {
        myLabel.setText("Choose your Move!");
    }
    
    /**
     * Displays the Hero's current stats in the JLabel when
     * the button is clicked, as long as it is the Hero's turn.
     * @param ae 
     */
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        myLabel.setText(myHero.getStats());
    }
}