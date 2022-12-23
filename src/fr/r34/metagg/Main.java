package fr.r34.metagg;

import fr.r34.metagg.manager.DirectoryManager;
import fr.r34.metagg.manager.FileManager;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws ParseException, IOException {
        FileManager fileM = new FileManager();
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
                    System.out.println("◼"+ name + "\t" + title + " " + creationDate + " " + size + " Ko");
                    metaFile.deleteTempFolder();
                }
            }
        } else if (args.length == 4) {
            if (args[0].equalsIgnoreCase("-f")) {
                File file = new File(args[1]);
                MetaFile metaFile = new MetaFile(file);
                String attribute = args[2].replace("--", "");
                String content = args[3];

                switch (attribute) {
                    case "title" -> {
                        metaFile.setTitle(content);
                    }
                    case "subject" -> {
                        metaFile.setSubject(content);
                    }
                    case "keyword" -> {
                        metaFile.getKeywords().add(content);
                    }
                }
                metaFile.save();
                metaFile.deleteTempFolder();
            }
        }
    }
}
