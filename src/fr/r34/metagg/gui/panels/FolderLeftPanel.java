package fr.r34.metagg.gui.panels;

import fr.r34.metagg.Strings;
import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.gui.Dimension;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class FolderLeftPanel extends JPanel {

    private final File folder;

    private final JPanel header, parentsFolderContainer, middle, filesInFolder;

    private final JLabel appTitle, parentFolder;

    private static int LABEL_WIDTH = (int) (0.7* Dimension.WINDOW_WIDTH);
    private static int LABEL_HEIGHT = Dimension.WINDOW_HEIGHT;
    public FolderLeftPanel(File folder) {
        this.folder = folder;
        header = new JPanel();
        parentsFolderContainer = new JPanel();
        middle = new JPanel();
        filesInFolder = new JPanel();

        appTitle = new JLabel(Strings.APP_TITLE);
        parentFolder = new JLabel("<html><font color=#273343>" + folder.getParentFile().getName() + "<font color=#FFFFFF>" + Strings.PARENT_FOLDER_TITLE + folder.getName() + "</html>");

        appTitle.setFont(new Font(Dimension.FONT, Font.PLAIN, Dimension.TITLE_SIZE));
        appTitle.setForeground(Colors.WHITE);
        appTitle.setBorder(new EmptyBorder(Dimension.DEFAULT_MARGIN, Dimension.DEFAULT_MARGIN, 0, 0));

        parentFolder.setFont(new Font(Dimension.FONT, Font.PLAIN, Dimension.TITLE_SIZE));
        parentFolder.setBorder(new EmptyBorder(Dimension.COMPONENT_MARGIN_TOP, Dimension.DEFAULT_MARGIN, 0, 0));

        this.setLayout(new BorderLayout());

        header.add(appTitle);
        header.add(parentFolder);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(Colors.BG_COLOR);



        this.add(header, BorderLayout.NORTH);

        this.setPreferredSize(new java.awt.Dimension(LABEL_WIDTH, LABEL_HEIGHT));
        this.setBackground(Colors.BG_COLOR);
    }
}
