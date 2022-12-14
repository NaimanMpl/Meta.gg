package fr.r34.metagg.gui.panels.openfolder;

import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.gui.Dimension;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FolderPanel extends JPanel {
    private JPanel mainList;
    private JScrollPane scrollPane;
    private ArrayList<File> folderContent = new ArrayList<File>();
    private int panelToAdd = 0, n = 0;
    public FolderPanel(ArrayList<File> folderContent) throws IOException {
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

        if(folderContent.size() < 6){
            panelToAdd = 6 - folderContent.size();
        }
        if((folderContent.size() % 2) != 0){
            File folderNull = null;
            folderContent.add(folderNull);
            System.out.println("Folder null ajouté");
        }

        for (int x = 1; x < (folderContent.size()); x += 2){
            File folder = folderContent.get(x-1);
            File folder2 = folderContent.get(x);
            FolderButtonPanel folderButtonPanel = new FolderButtonPanel(folder, folder2);
            GridBagConstraints gbc2 = new GridBagConstraints();
            gbc2.gridwidth = GridBagConstraints.HORIZONTAL;
            gbc2.weightx = 1;
            gbc2.fill = GridBagConstraints.NONE;
            gbc2.insets = new Insets(Dimension.LITTLE_MARGIN, 5, 0, 5);
            mainList.add(folderButtonPanel, gbc2, 0);
            validate();
            repaint();
        }
    }
}
