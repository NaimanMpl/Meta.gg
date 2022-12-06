package fr.r34.metagg.gui;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.gui.panels.MainLeftPanel;
import fr.r34.metagg.gui.panels.MainRightPanel;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.io.File;

public class MainMenuGUI {

    private final JFrame frame;
    private final JPanel leftPanel, rightPanel;
    private final Container container;

    public MainMenuGUI() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        frame = new JFrame();
        leftPanel = new MainLeftPanel(new MetaFile(new File("./sujet.odt")));
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        new MainMenuGUI();
    }

}
