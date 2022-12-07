package fr.r34.metagg.gui;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.Strings;
import fr.r34.metagg.gui.panels.MainLeftPanel;
import fr.r34.metagg.gui.panels.MainRightPanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.prefs.Preferences;

public class MainMenuGUI {

    private final JFrame frame;
    private JPanel leftPanel;
    private static JPanel rightPanel;
    private final JMenuBar menuBar;
    private final JMenu menu;
    private final JMenuItem openFile;
    private static Container container;
    private final JFileChooser fileChoose;
    private final Preferences prefs;
    private final ArrayList<MetaFile> recentMetaFiles;
    private final MainMenuGUI main;
    public MainMenuGUI() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        main = this;
        prefs = Preferences.userNodeForPackage(MainMenuGUI.class);
        recentMetaFiles = new ArrayList<>();

        frame = new JFrame();
        leftPanel = new MainLeftPanel(this);
        rightPanel = new MainRightPanel(new MetaFile(new File("./sujet.odt")));
        menuBar = new JMenuBar();
        menu = new JMenu(Strings.MENU_TITLE);
        openFile = new JMenuItem(Strings.OPEN);

        fileChoose = new JFileChooser();
        fileChoose.addChoosableFileFilter(new FileNameExtensionFilter("Fichier ODT (*.odt)", "odt"));
        fileChoose.setAcceptAllFileFilterUsed(false);

        openFile.addActionListener(new OpenFileAction());

        menu.add(openFile);

        menuBar.add(menu);

        container = frame.getContentPane();

        container.setLayout(new BorderLayout());

        container.add(leftPanel, BorderLayout.WEST);
        container.add(rightPanel, BorderLayout.EAST);

        frame.pack();
        frame.setJMenuBar(menuBar);
        frame.setSize(Dimension.WINDOW_WIDTH, Dimension.WINDOW_HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    class OpenFileAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int response = fileChoose.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = fileChoose.getSelectedFile();
                MetaFile metaFile = new MetaFile(file);

                updateRightPanel(metaFile);
                recentMetaFiles.add(metaFile);

                // Vérifie si la limite de fichiers récents n'a pas été atteinte.
                if (recentMetaFiles.size() > Dimension.MAX_RECENT_FILES_SIZE) {
                    recentMetaFiles.remove(0);
                    for (int i = 1; i < recentMetaFiles.size() - 1; i++) {
                        prefs.put(Strings.PREF_KEY + (i-1), prefs.get(Strings.PREF_KEY + i, "NULL"));
                    }
                    prefs.put(Strings.PREF_KEY + "0", file.getAbsolutePath());
                } else {
                    prefs.put(Strings.PREF_KEY + (recentMetaFiles.size() - 1), file.getAbsolutePath());
                }

                /*
                try {
                    updateRecentFilesContainer();
                } catch (UnsupportedLookAndFeelException | ClassNotFoundException | RuntimeException | InstantiationException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }*/

            }
        }
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        new MainMenuGUI();
    }

    public Preferences getPrefs() {
        return prefs;
    }

    public ArrayList<MetaFile> getRecentMetaFiles() {
        return recentMetaFiles;
    }

    public void updateRecentFilesContainer() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        container.remove(leftPanel);
        leftPanel = new MainLeftPanel(main);
        container.add(leftPanel);
        container.repaint();
        container.revalidate();
    }

    public void updateRightPanel(MetaFile metaFile) {
        container.remove(rightPanel);
        rightPanel = new MainRightPanel(metaFile);
        container.add(rightPanel);
        container.repaint();
        container.revalidate();
    }
}
