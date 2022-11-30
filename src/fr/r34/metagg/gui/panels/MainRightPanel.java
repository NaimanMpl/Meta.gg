package fr.r34.metagg.gui.panels;

import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.gui.Dimension;

import javax.swing.JPanel;
import java.awt.BorderLayout;

public class MainRightPanel extends JPanel {

    public MainRightPanel() {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new java.awt.Dimension(
                (int) (0.3* Dimension.WINDOW_WIDTH),
                Dimension.WINDOW_HEIGHT
        ));
        this.setBackground(Colors.BLUE_1);
    }

}
