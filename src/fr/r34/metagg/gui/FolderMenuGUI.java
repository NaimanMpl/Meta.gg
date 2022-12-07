package fr.r34.metagg.gui;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.gui.panels.FolderLeftPanel;
import fr.r34.metagg.gui.panels.MainLeftPanel;
import fr.r34.metagg.gui.panels.MainRightPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FolderMenuGUI {

    private final JFrame frame;

    private final JPanel leftPanel, rightPanel;

    private final Container container;

    public FolderMenuGUI(){

        frame = new JFrame();
        leftPanel = new FolderLeftPanel(new File("./ParcoursODT/lezgong"));
        rightPanel = new MainRightPanel(new MetaFile(new File("./sujet.odt")));

        container = frame.getContentPane();

        container.setLayout(new BorderLayout());

        container.add(leftPanel, BorderLayout.WEST);
        container.add(rightPanel, BorderLayout.EAST);

        frame.pack();
        frame.setSize(Dimension.WINDOW_WIDTH, Dimension.WINDOW_HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) {
        new FolderMenuGUI();
    }
}
