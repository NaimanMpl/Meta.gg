package fr.r34.metagg.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class Arborescence {

    private File parentFolder;
    private ArrayList<File> folderContent = new ArrayList<>();

    /**
     * Cette classe va ajouter à une liste de dossier
     * tous les dossiers que contient directement
     * un dossier passé en paramètre. Pour ce faire
     * on vérifie si oui ou non le fichier traité dans
     * la boucle est un répertoire. Si c'est bien
     * un répertoire (dossier) alors on l'ajoute à notre liste.
     *
     * @param parentFolder Dossier dont on veut l'arborescence de sous-dossier
     * @return La liste des sous-dossiers du dossier choisit par l'utilisateur
     */
    public ArrayList<File> getArborescence(File parentFolder){
        for (File element : Objects.requireNonNull(parentFolder.listFiles())){
            if(element.isDirectory()){
                folderContent.add(element);
            }
        }
        return folderContent;
    }
}
