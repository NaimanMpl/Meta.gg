package fr.r34.metagg.gui.panels;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.Strings;
import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.manager.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class LinksPanel extends JPanel {

    public LinksPanel(MetaFile metaFile) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        for (String link : metaFile.getHyperTextWebList()) {

            JPanel linkPanel = new JPanel(new GridLayout(1, 2, 30, 30));
            JLabel linkLabel = new JLabel(link);

            linkPanel.setBackground(Colors.BLUE_1);
            linkPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

            linkLabel.setBackground(Colors.BLUE_1);
            linkLabel.setForeground(Color.WHITE);

            linkPanel.add(linkLabel);

            mainPanel.add(linkPanel);
        }
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setPreferredSize(new Dimension((int) (0.25* fr.r34.metagg.gui.Dimension.WINDOW_WIDTH), 100));
        scrollPane.setBorder(null);
        this.add(scrollPane);
        this.setBackground(Colors.BLUE_1);
    }
}
