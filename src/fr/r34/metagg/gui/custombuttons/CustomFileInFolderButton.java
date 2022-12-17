package fr.r34.metagg.gui.custombuttons;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.Strings;
import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.gui.FolderMenuGUI;
import fr.r34.metagg.manager.Utils;
import jdk.jshell.execution.Util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class CustomFileInFolderButton extends JPanel {

    private static JLabel metafileNameLabel, metafileSizeLabel, metafileDateLabel, fileIcon;
    private double round;
    private Utils utils;
    private FolderMenuGUI main;

    public CustomFileInFolderButton(MetaFile metaFile, FolderMenuGUI main) throws IOException {
        super();
        this.main = main;
        this.setBackground(Colors.BLUE_1);
        this.setOpaque(true);
        this.utils = new Utils();
        this.setPreferredSize(new Dimension(600, 60));
        metafileNameLabel = new JLabel();
        metafileNameLabel.setText(metaFile.getFile().getName());
        metafileNameLabel.setForeground(Colors.WHITE);
        metafileNameLabel.setFont(new Font(fr.r34.metagg.gui.Dimension.FONT, Font.PLAIN, fr.r34.metagg.gui.Dimension.SUBTITLE_SIZE));
        round = (double) Math.round(metaFile.getSize() * 10) / 10;
        metafileSizeLabel = new JLabel();
        metafileSizeLabel.setText(round + "Ko");
        metafileSizeLabel.setForeground(Colors.BLUE_0);
        metafileSizeLabel.setFont(new Font(fr.r34.metagg.gui.Dimension.FONT, Font.PLAIN, fr.r34.metagg.gui.Dimension.PARAGRAPH_SIZE));
        metafileDateLabel = new JLabel();
        metafileDateLabel.setText("" + metaFile.getCreationDate());
        metafileDateLabel.setForeground(Colors.BLUE_0);
        metafileDateLabel.setFont(new Font(fr.r34.metagg.gui.Dimension.FONT, Font.PLAIN, fr.r34.metagg.gui.Dimension.PARAGRAPH_SIZE));
        fileIcon = new JLabel(utils.getImageFromResource(Strings.FILE_BUTTON_ICON_FOLDER_PANEL_PATH));

        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.setLayout(new GridLayout(1, 3, 30, 0));
        this.add(fileIcon);
        this.add(metafileNameLabel);
        this.add(metafileSizeLabel);
        this.add(metafileDateLabel);
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        this.setVisible(true);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    main.updateFolderRightPanel(metaFile);
                } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException |
                         ClassNotFoundException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public CustomFileInFolderButton(){
        super();
        this.setBackground(Colors.BLUE_1);
        this.setOpaque(true);
        this.setPreferredSize(new Dimension(600, 60));
        this.setVisible(true);
    }
}
