package fr.r34.metagg.gui.panels.openfile;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.gui.Colors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

public class LinksPanel extends JPanel {

    public LinksPanel(MetaFile metaFile) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        for (String link : metaFile.getHyperTextWebList()) {

            JPanel linkPanel = new JPanel(new GridLayout(1, 2, 30, 30));
            JLabel linkLabel = new JLabel(link);

            linkPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
            linkPanel.setBackground(Colors.BLUE_1);
            linkLabel.setForeground(Color.WHITE);

            linkPanel.add(linkLabel);

            mainPanel.add(linkPanel);
        }
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setPreferredSize(new Dimension((int) (0.2* fr.r34.metagg.gui.Dimension.WINDOW_WIDTH), 200));
        scrollPane.setBorder(null);
        this.add(scrollPane);
        this.setBackground(Colors.BLUE_1);
    }
}
