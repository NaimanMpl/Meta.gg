package fr.r34.metagg.gui.panels.openfolder;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.gui.Dimension;
import fr.r34.metagg.gui.MainMenuGUI;
import fr.r34.metagg.gui.custombuttons.CustomFileInFolderButton;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FilePanel extends JPanel {
    private JPanel mainList;
    private JScrollPane scrollPane;
    private ArrayList<File> folderContent;
    private MainMenuGUI main;

    /**
     * JPanel personnalisé selon un design prédéfini.
     * Le JPanel "FilePanel" a pour but de créer une scrollbar composé
     * uniquement de JPanel "CustomFileInFolderButton". Il est donc constitué
     * d'un JPanel principale, d'une JScrollPane verticale et d'une liste
     * à laquelle on ajoutera les "CustomFileInFolderButton". Si la taille de la
     * liste des fichiers ODT présents dans le dossier est strictement inférieur à 4
     * alors on ajoute 4 CustomFileInFolderButton de type vide pour garder une
     * strcuture identique à si la taille de la liste était supérieur à 4.
     *
     * @param folderContent     Liste des fichiers ODT présent dans le dossier sélectionné
     * @param main              Instance de la Frame principale FolderMenuGUI
     * @throws IOException
     */
    public FilePanel(ArrayList<File> folderContent, MainMenuGUI main) throws IOException {
        this.main = main;
        this.folderContent = folderContent;
        setLayout(new BorderLayout());
        setPreferredSize(new java.awt.Dimension(500, 200));

        mainList = new JPanel(new GridBagLayout());
        mainList.setBackground(Colors.BG_COLOR);
        mainList.setBorder(null);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        mainList.add(new JPanel(), gbc);

        scrollPane = new JScrollPane(mainList);
        scrollPane.setBorder(null);
        add(scrollPane);

        if(folderContent.size() < 4) {
            initFilePanelNull(folderContent);
        }
        initFilePanel(folderContent, main);
    }

    /**
     * Méthode d'initialisation des FilePanel, cette méthode va
     * parcourir les fichiers ODT de la liste "folderContent",
     * créer des metaFiles de chaque fichier ODT, créer des nouveaux
     * CustomFileInFolderButton en fonction de ces metaFiles et ajouter
     * ces boutons les un après les autres dans la liste de JPanel
     *
     * @param folderContent     Liste des fichiers ODT présent dans le dossier sélectionné
     * @param main              Instance de la Frame principale FolderMenuGUI
     * @throws IOException
     */
    public void initFilePanel(ArrayList<File> folderContent, MainMenuGUI main) throws IOException {
        for(File odtFile : folderContent){
            CustomFileInFolderButton customFileInFolderButton = new CustomFileInFolderButton(odtFile, main);
            GridBagConstraints gbc2 = new GridBagConstraints();
            gbc2.gridwidth = GridBagConstraints.REMAINDER;
            gbc2.weightx = 1;
            gbc2.fill = GridBagConstraints.NONE;
            gbc2.insets = new Insets(Dimension.LITTLE_MARGIN, 5, 0, 5);
            mainList.add(customFileInFolderButton, gbc2, 0);
            validate();
            repaint();
        }
    }

    /**
     * Cette méthode va d'initialisation de FilePanel va ajouter
     * des CustomFileInFolder de type vide un nombre de fois déterminé.
     * Ce nombre de répétitions est calculé en fonction de la taille
     * de la liste en paramètre. On soustrait 4 (choix arbitraire fait
     * en fonction du design prédéfinis) à la taille de la liste pour
     * que finalement on ait 4 CustomFileInFolderButton (de type vide ou
     * lié à un metaFile).
     * On ajoute les JPanels de la même manière que la méthode ci-dessus.
     *
     * @param folderContent
     */
    public void initFilePanelNull(ArrayList<File> folderContent) {
        for (int i = 0; i < (4 - folderContent.size());i++){
            CustomFileInFolderButton customFileInFolderButton = new CustomFileInFolderButton();
            GridBagConstraints gbc2 = new GridBagConstraints();
            gbc2.gridwidth = GridBagConstraints.REMAINDER;
            gbc2.weightx = 1;
            gbc2.fill = GridBagConstraints.NONE;
            gbc2.insets = new Insets(Dimension.LITTLE_MARGIN, 5, 0, 5);
            mainList.add(customFileInFolderButton, gbc2, 0);
            validate();
            repaint();
        }
    }
}
