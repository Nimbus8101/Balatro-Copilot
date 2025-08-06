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

import data.card.Card;

class CardLabel extends JLabel {
    private boolean isLifted = false;
    private final int liftAmount = 75;
    private Insets originalInsets;
    private final CardSelectionListener listener;
    int indexOfCard;

    public CardLabel(ImageIcon icon, Insets originalInsets, CardSelectionListener listener) {
        super(icon);
        this.originalInsets = originalInsets;
        this.listener = listener;
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	 Container parent = getParent();
                 GridBagLayout layout = (GridBagLayout) parent.getLayout();
                 GridBagConstraints gbc = layout.getConstraints(CardLabel.this);

                 if (isLifted) {
                     gbc.insets = originalInsets;
                     listener.onCardDeselected(CardLabel.this);
                     
                 } else {
                	 boolean allowed = listener.onCardSelected(CardLabel.this);
                	 
                	 if (!allowed) return;
                	 
                     gbc.insets = new Insets(
                             originalInsets.top - liftAmount,
                             originalInsets.left,
                             originalInsets.bottom + liftAmount,
                             originalInsets.right
                     );
                 }

                 layout.setConstraints(CardLabel.this, gbc);
                 parent.revalidate();
                 parent.repaint();

                 isLifted = !isLifted;
            }
        });
    }
}
