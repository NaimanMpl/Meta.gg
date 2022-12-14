package fr.r34.metagg.gui.panels;

import fr.r34.metagg.Strings;
import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.gui.Dimension;
import fr.r34.metagg.manager.DirectoryManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;


public class FolderLeftPanel extends JPanel {

    private final File folder;

    private final JPanel header, parentsFolderContainer, filesInFolderContainer;
    private final JLabel appTitle;

    private ArrayList<File> folderContent = new ArrayList<>();
    private static int LABEL_WIDTH = (int) (0.7* Dimension.WINDOW_WIDTH);
    private static int LABEL_HEIGHT = Dimension.WINDOW_HEIGHT;

    private final String initArborescencePathText = "";


    public FolderLeftPanel(File folder) {
        this.folder = folder;
        DirectoryManager directoryManager = new DirectoryManager();
        folderContent = directoryManager.directoryContent(folder, folderContent);

        header = new JPanel();
        appTitle = new JLabel(Strings.APP_TITLE);

        appTitle.setFont(new Font(Dimension.FONT, Font.PLAIN, Dimension.TITLE_SIZE));
        appTitle.setForeground(Colors.WHITE);
        appTitle.setBorder(new EmptyBorder(Dimension.DEFAULT_MARGIN, Dimension.DEFAULT_MARGIN, 0, 0));

        header.add(appTitle);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(Colors.BG_COLOR);

        parentsFolderContainer = new ArborescencePanel(folder, initArborescencePathText);
        filesInFolderContainer = new ContentFolderPanel(folderContent, folder);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(header);
        this.add(parentsFolderContainer);
        this.add(filesInFolderContainer);

        this.setPreferredSize(new java.awt.Dimension(LABEL_WIDTH, LABEL_HEIGHT));
        this.setBackground(Colors.BG_COLOR);
    }
}