package fr.r34.metagg.gui.custombuttons;

import fr.r34.metagg.Constants;
import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.gui.MainMenuGUI;
import fr.r34.metagg.manager.DirectoryManager;
import fr.r34.metagg.manager.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CustomFolderButton extends JPanel {

    private static JLabel folderNameLabel, numberOdtInFolderLabel, folderIcon;
    private static JPanel spacePanel;
    private ArrayList<File> folderContent = new ArrayList<>();
    private int numberOdtInFolder;
    private Utils utils;
    private MainMenuGUI main;

    /**
     * JPanel modifié et personnalisé selon un design prédifinis et pour
     * rendre l'interface plus claire et plus propre. Ce JPanel a été
     * modifié dans le but d'être un bouton cliquable correspondant
     * à un dossier dit "fille" du dossier ouvert par l'utilisateur.
     * Ce bouton affiche les informations suivantes :
     * - Nom du dossier
     * - Nombre de fichier ODT que contient ce dossier
     * Ce bouton a pour fonction de d'ajouter son dossier correspondant
     * à la liste "listFile" de dossier qui modélise l'arborescence. Une fois ce
     * JPanel cliqué, le dossier lié est ajouté à la liste, son nom
     * est ajouté à la liste des noms de dossier de l'arborescence "listFileName"
     * (qui sera utilisé pour afficher à l'utilisateur l'arborescence)
     * puis va lancer la fonction de mise à jour du FolderLeftPanel
     * pour correspondre à la nouvelle interface en fonction du dossier lié.
     *
     * @param folder    Dossier que l'on veut lier à notre bouton personnalisé.
     * @param main      Instance de la Frame principale FolderMenuGUI à laquelle ce bouton est rattaché.
     * @throws IOException
     */
    public CustomFolderButton(File folder, MainMenuGUI main) throws IOException {
        super();
        this.utils = new Utils();
        this.main = main;
        DirectoryManager directoryManager = new DirectoryManager();
        folderContent = directoryManager.directoryContent(folder, folderContent);
        numberOdtInFolder = folderContent.size();
        this.setBackground(Colors.BLUE_1);
        this.setOpaque(true);
        this.setPreferredSize(new Dimension(200, 110));
        folderNameLabel = new JLabel();
        folderNameLabel.setText(folder.getName());
        folderNameLabel.setForeground(Colors.WHITE);
        folderNameLabel.setFont(new Font(fr.r34.metagg.gui.Dimension.FONT, Font.BOLD, fr.r34.metagg.gui.Dimension.PARAGRAPH_SIZE));
        numberOdtInFolderLabel = new JLabel();
        numberOdtInFolderLabel.setText(numberOdtInFolder + " Fichiers");
        numberOdtInFolderLabel.setForeground(Colors.BLUE_0);
        numberOdtInFolderLabel.setFont(new Font(fr.r34.metagg.gui.Dimension.FONT, Font.PLAIN, fr.r34.metagg.gui.Dimension.PARAGRAPH_SIZE));
        folderIcon = new JLabel(utils.getImageFromResource(Constants.FOLDER_ICON_PATH));
        spacePanel = new JPanel();
        spacePanel.setBackground(Colors.BLUE_1);
        spacePanel.setOpaque(true);
        spacePanel.setPreferredSize(new Dimension(200, 0));
        spacePanel.setVisible(true);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.setBorder(new EmptyBorder(5, 10, 20, 0));
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.add(folderIcon);
        this.add(spacePanel);
        this.add(folderNameLabel);
        this.add(numberOdtInFolderLabel);
        this.setVisible(true);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    main.getListFile().add(folder);
                    main.getListFileName().add(folder.getName());
                    main.updateFolderLeftPanel(folder);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    /**
     * JPanel modifié selon le même design que le constructeur ci-dessus.
     * La fonction de ce JPanel n'est pas d'être un bouton cliquable mais
     * un bouton vide non cliquable, non lié à un dossier et sans
     * information dans le but combler le vide dans le JPanel parent.
     * Ce bouton sera généré si le dossier "fille" présent dans le
     * dossier parent est nul.
     */
    public CustomFolderButton(){
        super();
        this.setBackground(Colors.BLUE_1);
        this.setOpaque(true);
        this.setPreferredSize(new Dimension(200, 110));
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setVisible(true);
    }
}
