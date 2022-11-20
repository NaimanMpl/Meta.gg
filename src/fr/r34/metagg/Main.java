package fr.r34.metagg;

import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

    	DirectoryManager dirM = new DirectoryManager();
    	File folder = new File("C:\\Users\\Utilisateur\\OneDrive\\Documents\\Devoir\\L2_informatique\\POO java\\ProjetJava\\ParcoursODT");
    	ArrayList<File> odtInFolder = new ArrayList<>();
    	odtInFolder = dirM.directoryContent(folder);
    	
        FileManager fileM = new FileManager();
        File file = new File("expose.odt");
        File newFile = fileM.changeExtension(file, ".zip");
        fileM.readMetaData(newFile);
      
    }
}
