package fr.r34.metagg.gui.panels;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.gui.Dimension;
import fr.r34.metagg.gui.customButtons.CustomFileInFolderButton;
import fr.r34.metagg.gui.customButtons.CustomFolderButton;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class FolderPanel extends JPanel {
    private JPanel mainList;
    private JScrollPane scrollPane;
    private ArrayList<File> folderContent = new ArrayList<File>();
    public FolderPanel(ArrayList<File> folderContent) {
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
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);

        for(File folder : folderContent){
            CustomFolderButton customFolderButton;
            if(folder == null){
                customFolderButton = new CustomFolderButton();
            } else{
                customFolderButton = new CustomFolderButton(folder);
            }
            GridBagConstraints gbc2 = new GridBagConstraints();
            gbc2.gridwidth = GridBagConstraints.REMAINDER;
            gbc2.weighty = 1;
            gbc2.fill = GridBagConstraints.VERTICAL;
            gbc2.insets = new Insets(Dimension.LITTLE_MARGIN, 5, 0, 5);
            mainList.add(customFolderButton, gbc2, 0);
            validate();
            repaint();
        }

    }
}
