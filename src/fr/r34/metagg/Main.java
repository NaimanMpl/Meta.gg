package fr.r34.metagg;

import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        FileManager fileM = new FileManager();
        DirectoryManager directoryM = new DirectoryManager();

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("-f")) {
                File file = new File(args[1]);
                MetaFile metaFile = new MetaFile(file);
                metaFile.setTitle("Pipouloupipope");
                metaFile.setSubject("Mon super sujet sjdhf shjf sjh !");
                metaFile.setWordAmount(7277);
                metaFile.getKeywords().add("Mot clé test");
                metaFile.save();
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
                File folder = new File(args[1] + "/");
                ArrayList<File> odtInFolder = directoryM.directoryContent(folder, new ArrayList<>());
                for (File f : odtInFolder) {
                    MetaFile metaFile = new MetaFile(f);
                    File destDir = new File(f.getName().substring(0, f.getName().lastIndexOf(".")));
                    fileM.readMetaData(metaFile);
                    metaFile.setTitle("Hello world!");
                    metaFile.save();
                    File fileToZip = new File("./" + destDir.getName());
                    File zipFile = new File(destDir.getName() + ".zip");
                    try {
                        fileM.zip(fileToZip.toPath(), zipFile.toPath());
                        fileM.changeExtension(zipFile, ".odt");
                        fileM.delete(destDir);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (args.length == 4) {
            if (args[0].equalsIgnoreCase("-f")) {
                File file = new File(args[1]);
                File destDir = new File(file.getName().substring(0, file.getName().lastIndexOf(".")));
                String attribute = args[2].replace("--", "");
                String content = args[3];
                fileM.unzip(file, destDir);
                // fileM.modifyMetaData(new File(destDir.getPath() + "/meta.xml"), destDir.getPath(), attribute, content);
                File fileToZip = new File("./" + destDir.getName());
                File zipFile = new File(destDir.getName() + ".zip");
                try {
                    fileM.zip(fileToZip.toPath(), zipFile.toPath());
                    fileM.changeExtension(zipFile, ".odt");
                    fileM.delete(destDir);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
