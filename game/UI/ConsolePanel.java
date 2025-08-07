package game.UI;

import javax.swing.*;
import java.awt.*;

public class ConsolePanel extends JPanel {
    private JTextArea consoleArea;

    public ConsolePanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Console Output"));

        consoleArea = new JTextArea(10, 30); // You can adjust size
        consoleArea.setEditable(false);
        consoleArea.setLineWrap(true);
        consoleArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(consoleArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void appendText(String text) {
        consoleArea.append(text + "\n");
        consoleArea.setCaretPosition(consoleArea.getDocument().getLength()); // Auto-scroll
    }
    
    public static GridBagConstraints getGBC() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.weightx = 0.2;
        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        return gbc;
	}
}
