package fr.r34.metagg.gui.custombuttons;

import fr.r34.metagg.Strings;
import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.manager.DirectoryManager;
import fr.r34.metagg.manager.Utils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CustomFolderButton extends JPanel {

    private static JLabel folderNameLabel, numberOdtInFolderLabel, folderIcon;
    private ArrayList<File> folderContent = new ArrayList<>();
    private int numberOdtInFolder;
    private Utils utils;

    public CustomFolderButton(File folder) throws IOException {
        super();
        this.utils = new Utils();
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

        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.add(folderIcon);
        this.add(folderNameLabel);
        this.add(numberOdtInFolderLabel);
        this.setVisible(true);
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
