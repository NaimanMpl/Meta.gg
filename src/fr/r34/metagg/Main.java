package fr.r34.metagg;

import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

    	DirectoryManager dirM = new DirectoryManager();
    	File folder = new File("./");
    	ArrayList<File> odtInFolder = dirM.directoryContent(folder);

        FileManager fileM = new FileManager();
        for (File file : odtInFolder) {
            File newFile = fileM.changeExtension(file, ".zip");
            fileM.readMetaData(newFile);
        }
    }
}
