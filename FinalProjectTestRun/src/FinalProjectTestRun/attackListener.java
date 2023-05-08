package FinalProjectTestRun;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

/**
 * Listener for the attackButton
 */
public class attackListener implements ActionListener, MouseListener
{
    private JLabel myLabel;
    private Hero myHero;
    private Monster myMonster;
    private FinalProjectTester myWindow;
    
    public attackListener(JLabel newLabel, Hero newHero, Monster newMonster, FinalProjectTester newWindow)
    {
        super();
        
        myLabel = newLabel;
        myHero = newHero;
        myMonster = newMonster;
        myWindow = newWindow;
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
        if(myHero.isMyTurn == false)
        {
            myLabel.setText("WAIT!!");
        }
    }
    
    /**
     * Changes the jLabel to revert back to the standard message
     * after yelling at the user.
     * @param me 
     */
    @Override
    public void mouseReleased(MouseEvent me)
    {
        if(myHero.isMyTurn == false)
        {
            myLabel.setText("Choose your Move!");
        }
    }
    
    /**
     * Displays a description of what the button does to the JFrame
     * when the user hovers the mouse over it.
     * @param me 
     */
    @Override
    public void mouseEntered(MouseEvent me)
    {
        myLabel.setText("Attacks the target");
    }
    
    /**
     * Reverts the text in the JLabel to the default text when the mouse
     * is not over the button.
     * @param me 
     */
    @Override
    public void mouseExited(MouseEvent me)
    {
        myLabel.setText("Choose your move!");
    }
    
    /**
     * Causes the Hero to attack the Monster when
     * the button is clicked, as long as it is the Hero's turn.
     * @param ae 
     */
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        if(myHero.isMyTurn == true)
        {
            myHero.attack(myMonster);
            
            if(myMonster.status.equals("Dead"))
            {
                myWindow.setTitle("The evil has been slain by the Hero!!");
            }
            
            myMonster.takeTurn(myHero);
            
            if(myHero.status.equals("Dead"))
            {
                myWindow.setTitle("The Hero has been defeated! Evil shall rise!");
            }
        }
    }
}
