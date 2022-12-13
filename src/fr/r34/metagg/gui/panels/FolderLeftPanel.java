package fr.r34.metagg.gui.panels;

import fr.r34.metagg.Strings;
import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.gui.Dimension;
import fr.r34.metagg.gui.customButtons.CustomFolderButton;
import fr.r34.metagg.manager.DirectoryManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

public class FolderLeftPanel extends JPanel {

    private final File folder;

    private final JPanel header, parentsFolderContainer, filesInFolderContainer;
    private final JLabel appTitle, myFolder;

    private ArrayList<File> folderContent = new ArrayList<>();
    private static int LABEL_WIDTH = (int) (0.7* Dimension.WINDOW_WIDTH);
    private static int LABEL_HEIGHT = Dimension.WINDOW_HEIGHT;

    private final String initArborescencePathText = "";


    public FolderLeftPanel(File folder) {
        this.folder = folder;
        DirectoryManager directoryManager = new DirectoryManager();
        folderContent = directoryManager.directoryContent(folder, folderContent);

        header = new JPanel();
        filesInFolderContainer = new JPanel();

        appTitle = new JLabel(Strings.APP_TITLE);

        appTitle.setFont(new Font(Dimension.FONT, Font.PLAIN, Dimension.TITLE_SIZE));
        appTitle.setForeground(Colors.WHITE);
        appTitle.setBorder(new EmptyBorder(Dimension.DEFAULT_MARGIN, Dimension.DEFAULT_MARGIN, 0, 0));

        header.add(appTitle);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(Colors.BG_COLOR);

        parentsFolderContainer = new ArborescencePanel(folder, initArborescencePathText);

        ImageIcon myFolderIcon = new ImageIcon("./assets/img/my_folder_icon.png");
        myFolder = new JLabel(folder.getName(), myFolderIcon, JLabel.LEFT);
        myFolder.setForeground(Colors.WHITE);

        GridBagConstraints gbcnt = new GridBagConstraints();
        filesInFolderContainer.setLayout(new GridBagLayout());
        filesInFolderContainer.setBackground(Colors.BG_COLOR);
        gbcnt.fill = GridBagConstraints.VERTICAL;
        gbcnt.gridx = 0;
        gbcnt.gridy = 0;
        filesInFolderContainer.add(myFolder, gbcnt);
        gbcnt.gridx = 0;
        gbcnt.gridy = 1;
        filesInFolderContainer.add(new FilePanel(folderContent), gbcnt);
        //filesInFolderContainer.setBorder(new EmptyBorder(Dimension.COMPONENT_MARGIN_TOP, 2*Dimension.DEFAULT_MARGIN, Dimension.DEFAULT_MARGIN, Dimension.DEFAULT_MARGIN));

        this.setLayout(new BorderLayout());

        this.add(header, BorderLayout.NORTH);
        this.add(parentsFolderContainer, BorderLayout.CENTER);
        this.add(filesInFolderContainer, BorderLayout.SOUTH);

        this.setPreferredSize(new java.awt.Dimension(LABEL_WIDTH, LABEL_HEIGHT));
        this.setBackground(Colors.BG_COLOR);
    }
}