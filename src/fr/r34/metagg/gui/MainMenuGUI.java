package fr.r34.metagg.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;

public class MainMenuGUI {

    private JFrame frame;
    private JPanel header, gridPanel;
    private JLabel appTitle;
    private Container container;
    private JLabel recentFilesLabel;

    public MainMenuGUI(String title) {

        frame = new JFrame();

        appTitle = new JLabel(title);
        appTitle.setFont(new Font(Dimension.FONT, Font.PLAIN, Dimension.TITLE_SIZE));
        appTitle.setForeground(Colors.WHITE);
        recentFilesLabel = new JLabel("Fichiers récents");
        recentFilesLabel.setFont(new Font(Dimension.FONT, Font.PLAIN, Dimension.TITLE_SIZE));
        recentFilesLabel.setForeground(Colors.WHITE);

        container = frame.getContentPane();
        container.setForeground(Colors.BG_COLOR);

        header = new JPanel();
        header.add(appTitle);
        header.add(recentFilesLabel);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(Colors.BG_COLOR);

        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(3, 2));
        for (int i = 1; i < 7; i++) {
            gridPanel.add(new Rectangle(20, 20));
        }
        gridPanel.setBackground(Colors.BG_COLOR);

        container.add(header);
        container.add(gridPanel);

        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(Colors.BG_COLOR);
        frame.pack();
        frame.setBackground(Colors.BG_COLOR);
        frame.setSize(Dimension.WINDOW_MIN_WIDTH, Dimension.WINDOW_MIN_HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new MainMenuGUI("Meta.gg");
    }

}
