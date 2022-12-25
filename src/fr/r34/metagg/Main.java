package fr.r34.metagg;

import fr.r34.metagg.manager.DirectoryManager;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws ParseException, IOException {

        DirectoryManager directoryM = new DirectoryManager();

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("-f")) {
                // Chargement du fichier
                File file = new File(args[1]);
                MetaFile metaFile = new MetaFile(file);
                // Affichage des métadonnées
                metaFile.displayMetaData();
                // Supression du dossier temporairement extrait pour y lire les métadonnées suite à l'appel du constructeur de MetaFile
                metaFile.deleteTempFolder();
            } else if (args[0].equalsIgnoreCase("-d")) {
                File folder = new File(args[1]);
                // Chargement des fichiers OpenDocument présents dans le dossier (et l'ensemble de ses sous dossiers)
                ArrayList<File> odtInFolder = new ArrayList<>();
                odtInFolder = directoryM.directoryContent(folder, odtInFolder);
                // Parcours des fichiers OpenDocument
                for (File file : odtInFolder) {
                    MetaFile metaFile = new MetaFile(file);
                    // Lecture des métadonnées à afficher
                    String name = metaFile.getFile().getName();
                    String title = metaFile.getTitle();
                    String creationDate = metaFile.getCreationDate();
                    float size = metaFile.getSize();
                    // Affichages des métadonnées
                    System.out.println(metaFile.getFile().getParent());
                    System.out.println("◼ "+ name + "\t" + title + " " + creationDate + " " + size + " Ko");
                    // Supression du dossier temporairement extrait pour y lire les métadonnées suite à l'appel du constructeur de MetaFile
                    metaFile.deleteTempFolder();
                }
            } else {
                displayHelp();
            }
        } else if (args.length >= 4) {
            if (args[0].equalsIgnoreCase("-f")) {
                File file = new File(args[1]);
                MetaFile metaFile = new MetaFile(file);
                // Attribut que l'utilisateur souhaite modifier
                String attribute = args[2].replace("--", "");
                // Contenu de sa modification
                StringBuilder content = new StringBuilder();
                // Chargement du contenu renseigné en paamètre
                for (int i = 3; i < args.length; i++) { content.append(args[i] + " "); }

                // Modification de la métadonnée en fonction de l'attribut renseigné en paramètre
                switch (attribute) {
                    case "title":
                        metaFile.setTitle(content.toString());
                        break;
                    case "subject":
                        metaFile.setSubject(content.toString());
                        break;
                    case "keyword":
                        metaFile.getKeywords().add(content.toString());
                        break;
                    case "author":
                        metaFile.setAuthor(content.toString());
                        break;
                    default:
                        displayHelp();
                        break;
                }
                // Sauvegarde de la modification dans le fichier XML
                metaFile.save();
                // Supression du dossier temporairement extrait pour y lire les métadonnées suite à l'appel du constructeur de MetaFile
                metaFile.deleteTempFolder();
            } else {
                displayHelp();
            }
        } else if (args.length == 1 && Arrays.asList("-h", "-help").contains(args[0])) {
            displayHelp();
        } else {
            System.out.println("Commande inconnue. Veuillez réessayer");
            displayHelp();
        }
    }
    private static void displayHelp() {
        System.out.println("Usage:");
        System.out.println("\t-f <argument> : Affiche les métadonnées du fichier renseigné en argument.");
        System.out.println("\t-f <argument> --title <titre>: Permet de modifier le titre d'un fichier.");
        System.out.println("\t-f <argument> --subject <subject> : Permet de modifier le sujet d'un fichier.");
        System.out.println("\t-f <argument> --author <author> : Permet de modifier l'auteur d'un fichier.");
        System.out.println("\t-f <argument> --keyword <keyword> : Permet d'ajouter un mot clé aux métadonnées d'un fichier.");
        System.out.println("\t-d <argument> : Affiche les métadonnées des fichiers présents dans le répertoire.");
        System.out.println("\t-h ou -help : Affiche l'aide");
    }
}
