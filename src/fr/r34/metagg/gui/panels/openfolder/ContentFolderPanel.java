package fr.r34.metagg.gui.panels.openfolder;

import fr.r34.metagg.Strings;
import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.manager.Utils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ContentFolderPanel extends JPanel {

    private final JLabel myFolder;
    private final JPanel folderContentPanel;

    ImageIcon myFolderIcon = new ImageIcon();

    public ContentFolderPanel(ArrayList<File> folderContent, File folder) throws IOException {
        super();
        this.setBackground(Colors.BG_COLOR);
        myFolderIcon = new Utils().getImageFromResource(Strings.MY_FOLDER_ICON_PATH);
        myFolder = new JLabel(folder.getName(), myFolderIcon, JLabel.LEFT);
        myFolder.setForeground(Colors.WHITE);
        folderContentPanel = new FilePanel(folderContent);
        this.add(myFolder);
        this.add(folderContentPanel);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
}
