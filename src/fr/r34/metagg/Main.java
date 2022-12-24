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
                File file = new File(args[1]);
                MetaFile metaFile = new MetaFile(file);
                metaFile.displayMetaData();
                metaFile.deleteTempFolder();
            } else if (args[0].equalsIgnoreCase("-d")) {
                File folder = new File(args[1]);
                ArrayList<File> odtInFolder = new ArrayList<>();
                odtInFolder = directoryM.directoryContent(folder, odtInFolder);
                for (File file : odtInFolder) {
                    MetaFile metaFile = new MetaFile(file);
                    String name = metaFile.getFile().getName();
                    String title = metaFile.getTitle();
                    String creationDate = metaFile.getCreationDate();
                    float size = metaFile.getSize();
                    System.out.println(metaFile.getFile().getParent());
                    System.out.println("◼ "+ name + "\t" + title + " " + creationDate + " " + size + " Ko");
                    metaFile.deleteTempFolder();
                }
            } else {
                displayHelp();
            }
        } else if (args.length >= 4) {
            if (args[0].equalsIgnoreCase("-f")) {
                File file = new File(args[1]);
                MetaFile metaFile = new MetaFile(file);
                String attribute = args[2].replace("--", "");
                StringBuilder content = new StringBuilder();
                for (int i = 3; i < args.length; i++) { content.append(args[i] + " "); }

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
                }
                metaFile.save();
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
        System.out.println("\t-f <argument> --keyword <keyword> : Permet d'ajouter un mot clé aux métadonnées d'un fichier.");
        System.out.println("\t-d <argument> : Affiche les métadonnées des fichiers présents dans le répertoire.");
        System.out.println("\t-h ou -help : Affiche l'aide");
    }
}
