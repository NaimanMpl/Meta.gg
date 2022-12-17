package fr.r34.metagg.gui.panels.openfolder;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.gui.Dimension;
import fr.r34.metagg.gui.FolderMenuGUI;
import fr.r34.metagg.gui.custombuttons.CustomFileInFolderButton;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FilePanel extends JPanel {
    private JPanel mainList;
    private JScrollPane scrollPane;
    private ArrayList<File> folderContent = new ArrayList<File>();
    private FolderMenuGUI main;

    public FilePanel(ArrayList<File> folderContent, FolderMenuGUI main) throws IOException {
        this.main = main;
        this.folderContent = folderContent;
        setLayout(new BorderLayout());
        setPreferredSize(new java.awt.Dimension(500, 200));

        mainList = new JPanel(new GridBagLayout());
        mainList.setBackground(Colors.BG_COLOR);
        mainList.setBorder(null);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        mainList.add(new JPanel(), gbc);

        scrollPane = new JScrollPane(mainList);
        scrollPane.setBorder(null);
        add(scrollPane);

        if(folderContent.size() < 4) {
            initFilePanelNull(folderContent);
        }
        initFilePanel(folderContent, main);
    }
    public void initFilePanel(ArrayList<File> folderContent, FolderMenuGUI main) throws IOException {
        for(File odtFile : folderContent){
            MetaFile metaFile = new MetaFile(odtFile);
            CustomFileInFolderButton customFileInFolderButton = new CustomFileInFolderButton(metaFile, main);
            GridBagConstraints gbc2 = new GridBagConstraints();
            gbc2.gridwidth = GridBagConstraints.REMAINDER;
            gbc2.weightx = 1;
            gbc2.fill = GridBagConstraints.NONE;
            gbc2.insets = new Insets(Dimension.LITTLE_MARGIN, 5, 0, 5);
            mainList.add(customFileInFolderButton, gbc2, 0);
            validate();
            repaint();
        }
    }

    public void initFilePanelNull(ArrayList<File> folderContent) {
        for (int i = 0; i < (4 - folderContent.size());i++){
            CustomFileInFolderButton customFileInFolderButton = new CustomFileInFolderButton();
            GridBagConstraints gbc2 = new GridBagConstraints();
            gbc2.gridwidth = GridBagConstraints.REMAINDER;
            gbc2.weightx = 1;
            gbc2.fill = GridBagConstraints.NONE;
            gbc2.insets = new Insets(Dimension.LITTLE_MARGIN, 5, 0, 5);
            mainList.add(customFileInFolderButton, gbc2, 0);
            validate();
            repaint();
        }
    }
}
