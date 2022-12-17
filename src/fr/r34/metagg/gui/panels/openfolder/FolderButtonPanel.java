package fr.r34.metagg.gui.panels.openfolder;

import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.gui.FolderMenuGUI;
import fr.r34.metagg.gui.custombuttons.CustomFolderButton;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FolderButtonPanel extends JPanel {

    private CustomFolderButton customFolderButton1, customFolderButton2;

    private FolderMenuGUI main;

    public FolderButtonPanel(File folder, File folder2, FolderMenuGUI main) throws IOException {
        super();
        this.main = main;
        this.setBackground(Colors.BG_COLOR);
        this.setOpaque(true);
        if(folder == null){
            customFolderButton1 = new CustomFolderButton();
        } else {
            customFolderButton1 = new CustomFolderButton(folder, main);
        }
        if(folder2 == null){
            customFolderButton2 = new CustomFolderButton();
        } else {
            customFolderButton2 = new CustomFolderButton(folder2, main);
        }

        this.setLayout(new GridLayout(2, 1, 0, 10));
        this.add(customFolderButton1);
        this.add(customFolderButton2);
        this.setVisible(true);
    }
}
