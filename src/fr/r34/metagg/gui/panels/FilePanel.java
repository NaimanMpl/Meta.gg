package fr.r34.metagg.gui.panels;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.gui.Dimension;
import fr.r34.metagg.gui.customButtons.CustomFileInFolderButton;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class FilePanel extends JPanel {
    private JPanel mainList;
    private JScrollPane scrollPane;
    private ArrayList<File> folderContent = new ArrayList<File>();

    public FilePanel(ArrayList<File> folderContent) {
        setLayout(new BorderLayout());

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
            for (int i = 0; i < (4 - folderContent.size());i++){
                CustomFileInFolderButton customFileInFolderButton = new CustomFileInFolderButton();
                GridBagConstraints gbc2 = new GridBagConstraints();
                gbc2.gridwidth = GridBagConstraints.REMAINDER;
                gbc2.weightx = 1;
                gbc2.fill = GridBagConstraints.HORIZONTAL;
                gbc2.insets = new Insets(Dimension.LITTLE_MARGIN, 5, 0, 5);
                mainList.add(customFileInFolderButton, gbc2, 0);
                validate();
                repaint();
            }
        }
        for(File odtFile : folderContent){
            MetaFile metaFile = new MetaFile(odtFile);
            CustomFileInFolderButton customFileInFolderButton = new CustomFileInFolderButton(metaFile);
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
