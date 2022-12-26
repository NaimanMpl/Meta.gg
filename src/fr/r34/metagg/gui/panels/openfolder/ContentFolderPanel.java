package fr.r34.metagg.gui.panels.openfolder;

import fr.r34.metagg.Constants;
import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.gui.MainMenuGUI;
import fr.r34.metagg.manager.Utils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ContentFolderPanel extends JPanel {

    private final JLabel myFolder;
    private final JPanel folderContentPanel;

    private ImageIcon myFolderIcon;
    private MainMenuGUI main;

    /**
     * JPanel modifié et personnalisé en selon un design prédéfini
     * Ce JPanel a pour fonction de structurer la partie inférieur
     * de la partie gauche du FolderPanel. Ce JPanel va contenir
     * une scrollbar des fichiers ODT présent dans le dossier ouvert
     * et un JLabel avec une icône définit selon un design et le nom
     * du dossier actuellement ouvert et choisit par l'utilisateur.
     *
     * @param folderContent     Liste des fichiers ODT présents dans le dossier ouvert par l'utilisateur.
     * @param folder            Dossier choisit et ouvert par l'utilisateur.
     * @param main              Instance de la Frame principale "MainMenuGUI"
     * @throws IOException
     */
    public ContentFolderPanel(ArrayList<File> folderContent, File folder, MainMenuGUI main) throws IOException {
        super();
        this.main = main;
        this.setBackground(Colors.BG_COLOR);
        myFolderIcon = new Utils().getImageFromResource(Constants.MY_FOLDER_ICON_PATH);
        myFolder = new JLabel(folder.getName(), myFolderIcon, JLabel.LEFT);
        myFolder.setForeground(Colors.WHITE);
        folderContentPanel = new FilePanel(folderContent, main);
        this.add(myFolder);
        this.add(folderContentPanel);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
}
