package game.UI;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import data.card.Card;

class CardLabel extends JLabel {
    private boolean isLifted = false;
    private final CardSelectionListener listener;
    int indexOfCard;
    
    private static final int LIFT_AMOUNT = 50;

    public CardLabel(ImageIcon icon, CardSelectionListener listener) {
        super(icon);
        this.listener = listener;
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	 Container parent = getParent();
            	 if (!(parent instanceof JLayeredPane)) {
                     return;  // safety check
                 }
            	 
            	 // Check visibility and size to prevent premature interaction
                 if (!isShowing() || getWidth() == 0 || getHeight() == 0) return;
                 
                 int x = getX();
                 int y = getY();
                 
                 if (isLifted) {
                	 setLocation(x, y + LIFT_AMOUNT);
                     listener.onCardDeselected(CardLabel.this);
                     
                 } else {
                	 boolean allowed = listener.onCardSelected(CardLabel.this);
                	 
                	 if (!allowed) return;
                	 
                	 setLocation(x, y - LIFT_AMOUNT);
                 }

                 parent.repaint();

                 isLifted = !isLifted;
            }
        });
    }
    
    public void liftCard() {
    	if(isLifted) {
    		return;
    	}
    	else {
    		int x = getX();
            int y = getY();
            
            setLocation(x, y - LIFT_AMOUNT);
            isLifted = true;
    	}
    }
    
    public boolean isLifted() {
    	return isLifted;
    }
}
