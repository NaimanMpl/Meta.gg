package fr.r34.metagg;

import fr.r34.metagg.manager.DirectoryManager;
import fr.r34.metagg.manager.FileManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        FileManager fileM = new FileManager();
        DirectoryManager directoryM = new DirectoryManager();

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("-f")) {
                File file = new File(args[1]);
                MetaFile metaFile = new MetaFile(file);
                metaFile.displayMetaData();

                File fileToZip = new File("./" + metaFile.getDestDir().getName());
                File zipFile = new File(metaFile.getDestDir().getName() + ".zip");
                try {
                    fileM.zip(fileToZip.toPath(), zipFile.toPath());
                    fileM.changeExtension(zipFile, ".odt");
                    fileM.delete(metaFile.getDestDir());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (args[0].equalsIgnoreCase("-d")) {
                File folder = new File(args[1]);
                ArrayList<File> odtInFolder = new ArrayList<>();
                odtInFolder = directoryM.directoryContent(folder, odtInFolder);
                for(File file : odtInFolder){
                    MetaFile metaFile = new MetaFile(file);
                    String name = metaFile.getFile().getName();
                    String title = metaFile.getTitle();
                    Date creationDate = metaFile.getCreationDate();
                    float size = metaFile.getSize();
                    System.out.println(metaFile.getFile().getParent());
                    System.out.println("◼"+ name + "\t" + title + " " + creationDate + " " + size + " Ko");
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

                File fileToZip = new File("./" + metaFile.getDestDir().getName());
                File zipFile = new File(metaFile.getDestDir().getName() + ".zip");
                try {
                    fileM.zip(fileToZip.toPath(), zipFile.toPath());
                    fileM.changeExtension(zipFile, ".odt");
                    fileM.delete(metaFile.getDestDir());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
