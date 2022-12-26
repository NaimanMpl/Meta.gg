package fr.r34.metagg.gui.panels.openfolder;

import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.gui.Dimension;
import fr.r34.metagg.gui.MainMenuGUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FolderPanel extends JPanel {
    private JPanel mainList;
    private JScrollPane scrollPane;
    private ArrayList<File> folderContent = new ArrayList<File>();
    private int panelToAdd = 0, n = 0;

    private MainMenuGUI main;
    /**
     * JPanel personnalisé selon un design prédéfini.
     * Le JPanel "FolderPanel" a pour but de créer une scrollbar composé
     * uniquement de JPanel "CustomFolderButton". Il est donc constitué
     * d'un JPanel principale, d'une JScrollPane horizontale et d'une liste
     * à laquelle on ajoutera les "CustomFolderButton". Si la taille de la
     * liste des fichiers ODT présents dans le dossier est strictement inférieur à 6
     * alors on ajoute le nombre de CustomFolderButton de type vide pour arriver
     * finalement à un panel contenant au moins 6 CustomFolderButton.
     * Si la taille de la liste en paramètre n'est pas paire alors on ajoute un dossier nul
     * à cette liste pour garder un nombre de CustomFolderButton paire à l'affichage.
     *
     * @param folderContent Liste des fichiers ODT présent dans le dossier sélectionné
     * @param main Instance de la Frame principale MainMenuGUI
     * @throws IOException
     */
    public FolderPanel(ArrayList<File> folderContent, MainMenuGUI main) throws IOException {
        this.main = main;
        setLayout(new BorderLayout());

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
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);

        if((folderContent.size() % 2) != 0){
            File folderNull = null;
            folderContent.add(folderNull);
        }

        if(folderContent.size() < 6){
            panelToAdd = 6 - folderContent.size();
            initFolderPanel(folderContent, main);
            initFolderPanel2(panelToAdd, main);
        } else {
            initFolderPanel(folderContent, main);
        }
    }

    /**
     * Méthode d'initialisation des FolderPanel, cette méthode va
     * parcourir les dossiers de la liste "folderContent", créer des nouveaux
     * CustomFolderButton en fonction de ces dossiers et ajouter
     * ces boutons les un après les autres par deux dans la liste de JPanel
     *
     * @param folderContent     Liste des fichiers ODT présent dans le dossier sélectionné
     * @param main              Instance de la Frame principale FolderMenuGUI
     * @throws IOException
     */
    public void initFolderPanel(ArrayList<File> folderContent, MainMenuGUI main) throws IOException {
        this.main = main;
        for (int x = 1; x < (folderContent.size()); x += 2){
            File folder = folderContent.get(x-1);
            File folder2 = folderContent.get(x);
            FolderButtonPanel folderButtonPanel = new FolderButtonPanel(folder, folder2, main);
            GridBagConstraints gbc2 = new GridBagConstraints();
            gbc2.gridwidth = GridBagConstraints.HORIZONTAL;
            gbc2.weightx = 1;
            gbc2.fill = GridBagConstraints.NONE;
            gbc2.insets = new Insets(Dimension.LITTLE_MARGIN, 5, 0, 5);
            mainList.add(folderButtonPanel, gbc2, 0);
            validate();
            repaint();
        }
    }

    /**
     * Cette méthode va d'initialisation de FolderPanel va ajouter
     * des CustomFolderButton de type vide un nombre de fois déterminé.
     * Ce nombre de répétitions est calculé en fonction de la taille
     * de la liste en paramètre. On répète le nombre de fois passé en paramètre
     * l'ajout par deux des CustomFolderButton de type vide de la même manière que
     * la méthode ci-dessus.
     *
     *  @param panelToAdd   Nombre entier de CustomFolderButton de type vide à rajouter
     *  @throws IOException
     */

    public void initFolderPanel2(int panelToAdd, MainMenuGUI main) throws IOException {
        this.main = main;
        for (int x = 1; x < panelToAdd; x += 2){
            FolderButtonPanel folderButtonPanel = new FolderButtonPanel(null, null, main);
            GridBagConstraints gbc2 = new GridBagConstraints();
            gbc2.gridwidth = GridBagConstraints.HORIZONTAL;
            gbc2.weightx = 1;
            gbc2.fill = GridBagConstraints.NONE;
            gbc2.insets = new Insets(Dimension.LITTLE_MARGIN, 5, 0, 5);
            mainList.add(folderButtonPanel, gbc2, 0);
            validate();
            repaint();
        }
    }
}
