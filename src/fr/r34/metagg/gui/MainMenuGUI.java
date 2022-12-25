package fr.r34.metagg.gui;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.Constants;
import fr.r34.metagg.gui.panels.openfile.MainLeftPanel;
import fr.r34.metagg.gui.panels.openfile.MainRightPanel;
import fr.r34.metagg.gui.panels.openfolder.FolderLeftPanel;
import fr.r34.metagg.manager.CacheManager;
import fr.r34.metagg.manager.Utils;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
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
    private ArrayList<File> listFile = new ArrayList<>();
    private ArrayList<String> listFileName = new ArrayList<>();
    private Utils utils;
    private ImageIcon logoIcon;

    /**
     * Frame principale de l'application c'est elle qui affiche les fichiers récents (panneau de gauche)
     * ainsi que le fichier ouvert (panneau de droite)
     * @throws UnsupportedLookAndFeelException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public MainMenuGUI() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, ParserConfigurationException, SAXException {

        main = this;
        this.utils = new Utils();
        this.currentFile = new MetaFile();
        this.metaFilesOpened = new ArrayList<>();
        this.cacheFile = new File("./cache.xml");
        this.cacheManager = new CacheManager(cacheFile);
        cacheManager.initCache();

        frame = new JFrame();
        leftPanel = new MainLeftPanel(this);
        rightPanel = new MainRightPanel(this, currentFile);
        menuBar = new JMenuBar();
        menu = new JMenu(Constants.MENU_TITLE);
        openFile = new JMenuItem(Constants.OPEN);
        saveFile = new JMenuItem(Constants.SAVE_MODIFICATIONS);
        logoIcon = utils.getImageFromResource(Constants.LOGO_ICON);
        Taskbar taskbar = Taskbar.getTaskbar();
        try {
            // Modification de l'icone de l'application dans la barre des tâches
            taskbar.setIconImage(logoIcon.getImage());
        } catch (final UnsupportedOperationException e) {
            System.out.println("Le système d'exploitation ne supporte pas le changement de l'icone dans la barre des tâches.");
        } catch (final SecurityException e) {
            System.out.println("Impossible de changer l'icone dans la barre des tâches, il y'a un problème de sécurité.");
        }

        // Filtrage des fichiers que l'on peut ouvrir
        fileChoose = new JFileChooser();
        fileChoose.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChoose.addChoosableFileFilter(new FileNameExtensionFilter("Fichier ODT (*.odt)", "odt"));
        fileChoose.addChoosableFileFilter(new FileNameExtensionFilter("Fichier ODP (*.odp)", "odp"));
        fileChoose.addChoosableFileFilter(new FileNameExtensionFilter("Fichier ODS (*.ods)", "ods"));
        fileChoose.addChoosableFileFilter(new FileNameExtensionFilter("Fichier ODG (*.odg)", "odg"));
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
        frame.setIconImage(logoIcon.getImage());
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

    /**
     * Cette classe s'occupe de gérer l'ouverture d'un fichier.
     * Lorsque l'utilisateur a choisit un fichier a ouvrir, on ajoute ce fichier dans les fichiers
     * récemments ouverts et dans le cache puis on met à jour l'affichage du panneau de gauche ainsi
     * que celui de droite (car les informations ne sont plus les mêmes).
     * Par contre si on ouvre un dossier on fait alors appel à la class "FolderMenuGUI" qui s'occupe
     * d'afficher le contenu d'un dossier ainsi que ses fichiers ODT.
     */
    class OpenFileAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Sauvegarde de tous les fichiers ouverts au cours du lancement de l'application
            saveAllFiles();
            int response = fileChoose.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = fileChoose.getSelectedFile();
                if (file.isDirectory()) {
                    try {
                        listFile.add(file);
                        listFileName.add(file.getName());
                        // Mis à jour du panneau de gauche, pour y insérer le fichier récemment ouvert
                        updateFolderLeftPanel(file);
                        // Mis à jour du panneau de droite pour laisser place aux métadonnées du fichier ouvert
                        updateRightPanel(new MetaFile());
                    } catch (IOException | UnsupportedLookAndFeelException | ClassNotFoundException |
                             InstantiationException | IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                    return;
                }
                MetaFile metaFile = null;
                try {
                    metaFile = new MetaFile(file);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    // Mis à jour du panneau de droite pour laisser place aux métadonnées du fichier ouvert
                    updateRightPanel(metaFile);
                    // Mis à jour du panneau de gauche, pour y insérer le fichier récemment ouvert
                    updateLeftPanel();
                } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException |
                         ClassNotFoundException | IOException | ParserConfigurationException | SAXException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Sauvegarde les métadonnées de tous les fichiers récemments ouverts dans le fichier "meta.xml"
     * (Mais le fichier .odt reste inchangé!)
     */
    class SaveFileAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Parcours des fichiers ouverts
            for (MetaFile metaFile : metaFilesOpened) {
                File metaXML = new File(metaFile.getDestDir() + "/meta.xml");
                if (!metaXML.exists()) continue;
                // Sauvegarde des métadonnées dans le fichier XML
                metaFile.save();
            }
        }
    }

    /**
     * Sauvegarde les métadonnées de tous les fichiers récemments ouverts, extrait leurs dossiers temporairement
     * ouverts puis change l'extension du fichier compressé en .odt. On obtient alors un nouveau fichier odt
     * contenant les modifications effectuées.
     */
    public void saveAllFiles() {
        // Parcours des fichiers récemments ouverts
        for (MetaFile metaFile : metaFilesOpened) {
            // Localisation du fichier "meta.xml"
            File metaXML = new File(metaFile.getDestDir().getAbsolutePath() + "/meta.xml");
            if (metaXML.exists()) {
                // Sauvegarde des métadonnées du fichier
                metaFile.save();
            }
            // Supression du dossier temporairement extrait pour y lire les métadonnées suite à l'appel du constructeur de MetaFile
            metaFile.deleteTempFolder();
        }
        // Vide la liste des fichiers ouverts puisqu'ils ont tous été sauvegardés
        metaFilesOpened.clear();
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, ParserConfigurationException, SAXException {
        new MainMenuGUI();
    }

    /**
     * Met à jour le contenu du panneau de gauche (celui concernant les fichiers récemments ouvert)
     * On fait appel à cette méthode lorsque l'on ouvre un nouveau fichier afin qu'il puisse apparaître
     * dans les fichiers récemments ouverts
     * @throws UnsupportedLookAndFeelException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public void updateLeftPanel() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, ParserConfigurationException, SAXException {
        container.remove(leftPanel);
        leftPanel = new MainLeftPanel(this);
        container.add(leftPanel, BorderLayout.WEST);
        container.revalidate();
        container.repaint();
    }

    /**
     * Met à jour le panel de gauche (celui qui concerne les
     * éléments du dossier parent) en fonction d'un dossier
     * pour afficher désormais les éléments de ce dossier
     * quand on clique sur le CustomFolderButton correspondant.
     *
     * @param superFolder   Dossier choisit par l'utilisateur pour afficher ses éléments sur la partie gauche du panel.
     * @throws IOException
     */
    public void updateFolderLeftPanel(File superFolder) throws IOException {
        container.remove(leftPanel);
        leftPanel = new FolderLeftPanel(superFolder, this);
        container.add(leftPanel, BorderLayout.WEST);
        container.revalidate();
        container.repaint();
    }


    /**
     * Met à jour le contenu du panneau de droite (celui concernant les informations du fichier ouvert).
     * On fait appel à cette méthode lorsque l'on clique sur un fichier ouvert récemment ou lors de
     * l'ouverture d'un fichier.
     * @param metaFile Le fichier que l'on souhaite ouvrir (ou consulter)
     * @throws UnsupportedLookAndFeelException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void updateRightPanel(MetaFile metaFile) throws UnsupportedLookAndFeelException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        container.remove(rightPanel);
        rightPanel = new MainRightPanel(this, metaFile);
        container.add(rightPanel, BorderLayout.EAST);
        container.revalidate();
        container.repaint();
        // Fichier par défaut ouvert lorsque l'on ouvre pour la première fois l'application, ce dernier est fictif.
        if (metaFile.getFile().getName().equalsIgnoreCase("unknown")) return;
        // Au cours de l'éxécution un fichier peut avoir deux références différentes, il est important de sauvegarder le bon
        if (metaFilesOpened.contains(metaFile)) {
            for (int i = 0; i < metaFilesOpened.size(); i++) {
                if (metaFilesOpened.get(i).equals(metaFile)) {
                    metaFilesOpened.set(i, metaFile);
                }
            }
        } else {
            // Ajout à la liste des fichiers ouverts et au cache
            main.getMetaFilesOpened().add(metaFile);
            cacheManager.addFileToCache(metaFile);
        }
    }

    /**
     * Renvoie la liste de tout les fichiers ouverts lors de l'exécution du programme
     * @return La liste des fichiers ouverts
     */
    public ArrayList<MetaFile> getMetaFilesOpened() {
        return metaFilesOpened;
    }

    /**
     * Renvoi la liste des dossiers parcourus par l'utilisateur.
     * @return La liste des dossiers parcourus par l'utilisateur
     */
    public ArrayList<File> getListFile() {
        return listFile;
    }

    /**
     * Renvoi la liste des noms des dossiers parcourus par l'utilisateur.
     * @return  La liste des noms des dossiers parcourus
     */
    public ArrayList<String> getListFileName() {
        return listFileName;
    }
}
