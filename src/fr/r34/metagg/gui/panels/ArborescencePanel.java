package fr.r34.metagg.gui.panels;

import fr.r34.metagg.Strings;
import fr.r34.metagg.gui.Arborescence;
import fr.r34.metagg.gui.Colors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class ArborescencePanel extends JPanel {

    private JLabel arborescencePath;
    private JPanel top;
    private ArrayList<File> folderContent = new ArrayList<>();
    public String arborescencePathText = "";

    public ArborescencePanel(File parentFolder, String arborescencePathText){
        super();
        this.setBackground(Colors.BG_COLOR);
        this.setBorder(new EmptyBorder(fr.r34.metagg.gui.Dimension.COMPONENT_MARGIN_TOP, 2* fr.r34.metagg.gui.Dimension.DEFAULT_MARGIN, fr.r34.metagg.gui.Dimension.DEFAULT_MARGIN, fr.r34.metagg.gui.Dimension.DEFAULT_MARGIN));
        Arborescence arborescence = new Arborescence();
        folderContent = arborescence.getArborescence(parentFolder);
        arborescencePath = new JLabel();
        arborescencePath.setText("<html><font color=#273343>" + arborescencePathText + " " + Strings.PARENT_FOLDER_TITLE + " " + "<font color=#ffffff>" + parentFolder.getName() + "</html>");
        arborescencePath.setFont(new Font(fr.r34.metagg.gui.Dimension.FONT, Font.PLAIN, fr.r34.metagg.gui.Dimension.TITLE_SIZE));
        ImageIcon myFolderIcon = new ImageIcon("./assets/img/my_folder_icon.png");
        arborescencePath.setIcon(myFolderIcon);
        arborescencePathText += ">" + parentFolder.getName();

        top = new JPanel();
        top.setLayout(new FlowLayout(FlowLayout.LEFT));

        this.add(arborescencePath);
        this.add(new FolderPanel(folderContent));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setVisible(true);
    }
}
