package fr.r34.metagg.gui;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.Strings;
import fr.r34.metagg.gui.panels.openfile.MainLeftPanel;
import fr.r34.metagg.gui.panels.openfile.MainRightPanel;
import fr.r34.metagg.manager.CacheManager;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainMenuGUI {

    private final JFrame frame;
    private JPanel leftPanel;
    private static JPanel rightPanel;
    private final JMenuBar menuBar;
    private final JMenu menu;
    private final JMenuItem openFile, saveFile;
    private static Container container;
    private final JFileChooser fileChoose;
    private final MainMenuGUI main;
    private final File cacheFile;
    private CacheManager cacheManager;
    private final ArrayList<MetaFile> metaFilesOpened;
    private MetaFile currentFile;

    public MainMenuGUI() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, ParserConfigurationException, SAXException {

        main = this;
        this.currentFile = new MetaFile();
        this.metaFilesOpened = new ArrayList<>();
        this.cacheFile = new File("./cache.xml");
        this.cacheManager = new CacheManager(cacheFile);
        cacheManager.initCache();

        frame = new JFrame();
        leftPanel = new MainLeftPanel(this);
        rightPanel = new MainRightPanel(this, currentFile);
        menuBar = new JMenuBar();
        menu = new JMenu(Strings.MENU_TITLE);
        openFile = new JMenuItem(Strings.OPEN);
        saveFile = new JMenuItem(Strings.SAVE_MODIFICATIONS);

        fileChoose = new JFileChooser();
        fileChoose.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChoose.addChoosableFileFilter(new FileNameExtensionFilter("Fichier ODT (*.odt)", "odt"));
        fileChoose.setAcceptAllFileFilterUsed(false);

        openFile.addActionListener(new OpenFileAction());
        saveFile.addActionListener(new SaveFileAction());

        menu.add(openFile);
        menu.add(saveFile);

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
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int response = JOptionPane.showConfirmDialog(
                        frame,
                        "Êtes vous sur de vouloir quitter ?",
                        "Système",
                        JOptionPane.YES_NO_OPTION
                );
                if (response == JOptionPane.OK_OPTION) {
                    saveAllFiles();
                    System.exit(0);
                }
            }
        });
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    class OpenFileAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int response = fileChoose.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = fileChoose.getSelectedFile();
                if (file.isDirectory()) {
                    try {
                        new FolderMenuGUI(main, file);
                    } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
                             IllegalAccessException | IOException ex) {
                        ex.printStackTrace();
                    }
                    return;
                }
                MetaFile metaFile = new MetaFile(file);
                cacheManager.addFileToCache(metaFile);
                metaFilesOpened.add(metaFile);
                try {
                    updateLeftPanel();
                    updateRightPanel(metaFile);
                } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException |
                         ClassNotFoundException | IOException | ParserConfigurationException | SAXException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    class SaveFileAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (MetaFile metaFile : metaFilesOpened) metaFile.save();
        }
    }

    private void saveAllFiles() {
        for (MetaFile metaFile : metaFilesOpened) {
            if (!metaFile.getDestDir().exists()) continue;
            metaFile.save();
            metaFile.deleteTempFolder();
        }
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, ParserConfigurationException, SAXException {
        new MainMenuGUI();
    }

    public void updateLeftPanel() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, ParserConfigurationException, SAXException {
        container.remove(leftPanel);
        leftPanel = new MainLeftPanel(this);
        container.add(leftPanel, BorderLayout.WEST);
        container.revalidate();
        container.repaint();
    }

    public void updateRightPanel(MetaFile metaFile) throws UnsupportedLookAndFeelException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        container.remove(rightPanel);
        rightPanel = new MainRightPanel(this, metaFile);
        container.add(rightPanel, BorderLayout.EAST);
        container.revalidate();
        container.repaint();
    }

    public ArrayList<MetaFile> getMetaFilesOpened() {
        return metaFilesOpened;
    }
}
