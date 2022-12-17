package fr.r34.metagg.gui.custombuttons;

import fr.r34.metagg.Strings;
import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.gui.FolderMenuGUI;
import fr.r34.metagg.manager.DirectoryManager;
import fr.r34.metagg.manager.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CustomFolderButton extends JPanel {

    private static JLabel folderNameLabel, numberOdtInFolderLabel, folderIcon;
    private static JPanel spacePanel;
    private ArrayList<File> folderContent = new ArrayList<>();
    private int numberOdtInFolder;
    private Utils utils;
    private FolderMenuGUI main;

    public CustomFolderButton(File folder, FolderMenuGUI main) throws IOException {
        super();
        this.utils = new Utils();
        this.main = main;
        DirectoryManager directoryManager = new DirectoryManager();
        folderContent = directoryManager.directoryContent(folder, folderContent);
        numberOdtInFolder = folderContent.size();
        this.setBackground(Colors.BLUE_1);
        this.setOpaque(true);
        this.setPreferredSize(new Dimension(200, 110));
        folderNameLabel = new JLabel();
        folderNameLabel.setText(folder.getName());
        folderNameLabel.setForeground(Colors.WHITE);
        folderNameLabel.setFont(new Font(fr.r34.metagg.gui.Dimension.FONT, Font.BOLD, fr.r34.metagg.gui.Dimension.PARAGRAPH_SIZE));
        numberOdtInFolderLabel = new JLabel();
        numberOdtInFolderLabel.setText(numberOdtInFolder + " Fichiers");
        numberOdtInFolderLabel.setForeground(Colors.BLUE_0);
        numberOdtInFolderLabel.setFont(new Font(fr.r34.metagg.gui.Dimension.FONT, Font.PLAIN, fr.r34.metagg.gui.Dimension.PARAGRAPH_SIZE));
        folderIcon = new JLabel(utils.getImageFromResource(Strings.FOLDER_ICON_PATH));
        spacePanel = new JPanel();
        spacePanel.setBackground(Colors.BLUE_1);
        spacePanel.setOpaque(true);
        spacePanel.setPreferredSize(new Dimension(200, 0));
        spacePanel.setVisible(true);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.setBorder(new EmptyBorder(5, 10, 20, 0));
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.add(folderIcon);
        this.add(spacePanel);
        this.add(folderNameLabel);
        this.add(numberOdtInFolderLabel);
        this.setVisible(true);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    main.getListFile().add(folder);
                    main.getListFileName().add(folder.getName());
                    main.updateFolderLeftPanel(folder);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public CustomFolderButton(){
        super();
        this.setBackground(Colors.BLUE_1);
        this.setOpaque(true);
        this.setPreferredSize(new Dimension(200, 110));
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setVisible(true);
    }
}
