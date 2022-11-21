package fr.r34.metagg;

import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;

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
        fileM.unzip(newFile);
        fileM.modifyMetaData(new File("meta.xml"), "title", "Mon super projet !!");
        file = fileM.changeExtension(newFile, ".odt");
    }
}
