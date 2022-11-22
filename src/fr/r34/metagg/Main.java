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
                File newFile = fileM.changeExtension(file, ".zip");
                fileM.readMetaData(newFile);
            } else if (args[0].equalsIgnoreCase("-d")) {
                File folder = new File("./");
                ArrayList<File> odtInFolder = directoryM.directoryContent(folder);
                for (File f : odtInFolder) {
                    File newFile = fileM.changeExtension(f, ".zip");
                    fileM.readMetaData(newFile);
                }
                fileM.readMetaData(fileM.changeExtension(new File(args[1]), ".zip"));
            }
        } else if (args.length == 4) {
            if (args[0].equalsIgnoreCase("-f")) {
                File file = new File(args[1]);
                String attribute = args[2];
                String content = args[3];
            }
        }
        File file = new File("sujet.odt");
        File newFile = fileM.changeExtension(file, ".zip");
        File destDir = new File(file.getName().substring(0, file.getName().lastIndexOf(".")));
        fileM.readMetaData(newFile);
        fileM.modifyMetaData(new File(destDir.getPath() + "/meta.xml"), destDir.getPath(), "title", "Mon nouveau super titre !!");
        File fileToZip = new File("./" + destDir.getName());
        File zipFile = new File(destDir.getName() + ".zip");
        try {
            fileM.zip(fileToZip.toPath(), zipFile.toPath());
            fileM.changeExtension(zipFile, ".odt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
