package fr.r34.metagg;

import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws IOException {

        DirectoryManager dirM = new DirectoryManager();
        File folder = new File("./");
        ArrayList<File> odtInFolder = dirM.directoryContent(folder, new ArrayList<File>());
        System.out.println(odtInFolder);

        FileManager fileM = new FileManager();

        File media = new File("C:\\Users\\andre\\OneDrive\\Documents\\Devoir\\L2_informatique\\POO java\\ProjetJava\\Meta\\Meta.gg\\media");
        fileM.readPictureMetaData(media);

        
        File thumbnailFolder = new File("C:\\Users\\andre\\OneDrive\\Documents\\Devoir\\L2_informatique\\POO java\\ProjetJava\\Thumbnails");
        File thumbnal = fileM.getThumbnail(thumbnailFolder);
        
        
        
        //for (File file : odtInFolder) {
        //    System.out.println(file.exists());
        //    File newFile = fileM.changeExtension(file, ".zip");
        //    System.out.println(newFile.getAbsolutePath());
        //    System.out.println(newFile.exists());
        //    fileM.readMetaData(newFile);
        //}
    }
}
