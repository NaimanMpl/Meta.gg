package fr.r34.metagg.gui.panels;

import fr.r34.metagg.Strings;
import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.gui.CustomRectangle;
import fr.r34.metagg.gui.Dimension;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class MainLeftPanel extends JPanel {

    private final JPanel header, filesContainer;
    private final JLabel appTitle, recentFiles;
    private CustomRectangle rectangle;
    private static int LABEL_WIDTH = (int) (0.7* Dimension.WINDOW_WIDTH);
    private static int LABEL_HEIGHT = Dimension.WINDOW_HEIGHT;

    public MainLeftPanel() {

        header = new JPanel();
        filesContainer = new JPanel();

        appTitle = new JLabel(Strings.APP_TITLE);
        recentFiles = new JLabel(Strings.RECENT_FILES_TITLE);

        rectangle = new CustomRectangle(
                LABEL_WIDTH - 80,
                4,
                Dimension.DEFAULT_MARGIN,
                Dimension.COMPONENT_MARGIN_TOP
        );

        appTitle.setFont(new Font(Dimension.FONT, Font.PLAIN, Dimension.TITLE_SIZE));
        appTitle.setForeground(Colors.WHITE);
        appTitle.setBorder(new EmptyBorder(
                Dimension.DEFAULT_MARGIN,
                Dimension.DEFAULT_MARGIN,
                0,0
        ));

        recentFiles.setFont(new Font(Dimension.FONT, Font.PLAIN, Dimension.TITLE_SIZE));
        recentFiles.setForeground(Colors.WHITE);
        recentFiles.setBorder(new EmptyBorder(
                Dimension.COMPONENT_MARGIN_TOP,
                Dimension.DEFAULT_MARGIN,
                0, 0
        ));

        this.setLayout(new BorderLayout());

        header.add(appTitle);
        header.add(rectangle);
        header.add(recentFiles);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(Colors.BG_COLOR);

        filesContainer.setLayout(new GridLayout(3, 3, Dimension.DEFAULT_MARGIN, Dimension.DEFAULT_MARGIN));
        filesContainer.setBackground(Colors.BG_COLOR);
        filesContainer.setBorder(new EmptyBorder(
                Dimension.COMPONENT_MARGIN_TOP,
                2*Dimension.DEFAULT_MARGIN,
                Dimension.DEFAULT_MARGIN,
                Dimension.DEFAULT_MARGIN
        ));

        for (int i = 0; i < 9; i++) {
            JButton button = new JButton("Click me!");
            button.setBackground(Colors.BLUE_1);
            button.setOpaque(true);
            button.setBorderPainted(false);
            filesContainer.add(button);
        }

        this.add(header, BorderLayout.NORTH);
        this.add(filesContainer, BorderLayout.CENTER);

        this.setPreferredSize(new java.awt.Dimension(LABEL_WIDTH, LABEL_HEIGHT));
        this.setBackground(Colors.BG_COLOR);
    }

}
