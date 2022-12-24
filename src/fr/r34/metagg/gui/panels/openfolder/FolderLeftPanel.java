package fr.r34.metagg.gui.panels.openfolder;

import fr.r34.metagg.Constants;
import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.gui.Dimension;
import fr.r34.metagg.gui.MainMenuGUI;
import fr.r34.metagg.manager.DirectoryManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class FolderLeftPanel extends JPanel {

    private final File folder;

    private final JPanel header, parentsFolderContainer, filesInFolderContainer;
    private final JLabel appTitle;

    private ArrayList<File> folderContent = new ArrayList<>();
    private static int LABEL_WIDTH = (int) (0.7* Dimension.WINDOW_WIDTH);
    private static int LABEL_HEIGHT = Dimension.WINDOW_HEIGHT;
    private final String initArborescencePathText = "";
    private final MainMenuGUI main;


    /**
     * JPanel modifié et personnalisé en fonction d'un design
     * prédéfini. Ce JPanel a pour but de rassembler les différents
     * sous panel de la partie gauche de l'interface. Ce panel est
     * composé de :
     * - un JPanel "header" qui contiendra le nom de l'application
     * - un JPanel pour la partie arborescence du dossier passé en paramètre.
     * - un JPanel pour la partie des fichiers ODT du dossier passé en paramètre.
     * La partie arborescence fait seulement appel à la classe ArborescencePanel.
     * La partie fichier du dossier fait uniquement appel à la classe ContentFolderPanel.
     *
     * @param folder    Dossier choisit et ouvert par l'utilisateur pour parcours ses différents sous-dossiers et fichiers ODT.
     * @param main      Instance de la Frame principale FolderMenuGUI.
     * @throws IOException
     */
    public FolderLeftPanel(File folder, MainMenuGUI main) throws IOException {
        this.main = main;
        this.folder = folder;
        DirectoryManager directoryManager = new DirectoryManager();
        folderContent = directoryManager.odtInDirectory(folder);

        header = new JPanel();
        appTitle = new JLabel(Constants.APP_TITLE);

        appTitle.setFont(new Font(Dimension.FONT, Font.PLAIN, Dimension.TITLE_SIZE));
        appTitle.setForeground(Colors.WHITE);
        appTitle.setBorder(new EmptyBorder(Dimension.DEFAULT_MARGIN, Dimension.DEFAULT_MARGIN, 0, 0));

        header.add(appTitle);
        //header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setLayout(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(Colors.BG_COLOR);

        parentsFolderContainer = new ArborescencePanel(folder, main);
        filesInFolderContainer = new ContentFolderPanel(folderContent, folder, main);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(header);
        this.add(parentsFolderContainer);
        this.add(filesInFolderContainer);

        this.setPreferredSize(new java.awt.Dimension(LABEL_WIDTH, LABEL_HEIGHT));
        this.setBackground(Colors.BG_COLOR);
    }
}