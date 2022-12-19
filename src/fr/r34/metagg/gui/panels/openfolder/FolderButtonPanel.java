package fr.r34.metagg.gui.panels.openfolder;

import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.gui.FolderMenuGUI;
import fr.r34.metagg.gui.custombuttons.CustomFolderButton;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FolderButtonPanel extends JPanel {

    private CustomFolderButton customFolderButton1, customFolderButton2;

    private FolderMenuGUI main;

    /**
     * Cette classe va créer deux JPanels CustomFolderButton en
     * fonction des dossiers passés en paramètre (si le dossier est
     * nul alors utilise un constructeur sans paramètre) puis les ajouter
     * à un JPanel FolderButtonPanel qui aura pour but de structurer ces
     * deux CustomFolderButton en duo sous forme de grille de deux lignes
     * et une colonne.
     *
     * @param folder    Dossier que l'on veut lier au bouton
     * @param folder2   Dossier que l'on veut lier au bouton
     * @param main      Instance de la Frame principale FolderMenuGUI
     * @throws IOException
     */
    public FolderButtonPanel(File folder, File folder2, FolderMenuGUI main) throws IOException {
        super();
        this.main = main;
        this.setBackground(Colors.BG_COLOR);
        this.setOpaque(true);
        if(folder == null){
            customFolderButton1 = new CustomFolderButton();
        } else {
            customFolderButton1 = new CustomFolderButton(folder, main);
        }
        if(folder2 == null){
            customFolderButton2 = new CustomFolderButton();
        } else {
            customFolderButton2 = new CustomFolderButton(folder2, main);
        }

        this.setLayout(new GridLayout(2, 1, 0, 10));
        this.add(customFolderButton1);
        this.add(customFolderButton2);
        this.setVisible(true);
    }
}
