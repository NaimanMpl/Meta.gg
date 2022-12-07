package fr.r34.metagg.gui.panels;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.Strings;
import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.gui.CustomFileButton;
import fr.r34.metagg.gui.Dimension;
import fr.r34.metagg.gui.MainMenuGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class MainLeftPanel extends JPanel {

    private final JPanel header, filesContainer;
    private final JLabel appTitle, recentFiles;
    private static int LABEL_WIDTH = (int) (0.7* Dimension.WINDOW_WIDTH);
    private static int LABEL_HEIGHT = Dimension.WINDOW_HEIGHT;
    private final MainMenuGUI main;

    public MainLeftPanel(MainMenuGUI main) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        this.main = main;

        header = new JPanel();
        filesContainer = new JPanel();

        appTitle = new JLabel(Strings.APP_TITLE);
        recentFiles = new JLabel(Strings.RECENT_FILES_TITLE);

        appTitle.setFont(new Font(Dimension.FONT, Font.PLAIN, Dimension.TITLE_SIZE));
        appTitle.setForeground(Colors.WHITE);
        appTitle.setBorder(new EmptyBorder(
                Dimension.DEFAULT_MARGIN,
                Dimension.DEFAULT_MARGIN,
                0,0
        ));

        recentFiles.setFont(new Font(Dimension.FONT, Font.PLAIN, Dimension.TITLE_SIZE));
        recentFiles.setForeground(Colors.WHITE);
        recentFiles.setBorder(new EmptyBorder(
                Dimension.COMPONENT_MARGIN_TOP,
                Dimension.DEFAULT_MARGIN,
                0, 0
        ));

        this.setLayout(new BorderLayout());

        header.add(appTitle);
        header.add(recentFiles);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(Colors.BG_COLOR);

        filesContainer.setLayout(new GridLayout(3, 3, Dimension.DEFAULT_MARGIN, Dimension.DEFAULT_MARGIN));
        filesContainer.setBackground(Colors.BG_COLOR);
        filesContainer.setBorder(new EmptyBorder(
                Dimension.COMPONENT_MARGIN_TOP,
                Dimension.DEFAULT_MARGIN,
                Dimension.DEFAULT_MARGIN,
                Dimension.DEFAULT_MARGIN
        ));

        loadRecentFiles();

        this.add(header, BorderLayout.NORTH);
        this.add(filesContainer, BorderLayout.CENTER);

        this.setPreferredSize(new java.awt.Dimension(LABEL_WIDTH, LABEL_HEIGHT));
        this.setBackground(Colors.BG_COLOR);
    }

    private void loadRecentFiles() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        for (int i = 0; i < Dimension.MAX_RECENT_FILES_SIZE; i++) {
            String path = main.getPrefs().get(Strings.PREF_KEY + i, "NULL");
            if (path.equalsIgnoreCase("NULL")) {
                filesContainer.add(new CustomFileButton());
            } else {
                System.out.println("Recent file " + i + " path :" + path);
                MetaFile metaFile = new MetaFile(new File(path));
                CustomFileButton fileBtn = new CustomFileButton(metaFile);
                fileBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        main.updateRightPanel(metaFile);
                    }
                });

                filesContainer.add(fileBtn);
            }

        }
    }

}
