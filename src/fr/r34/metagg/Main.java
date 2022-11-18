package fr.r34.metagg;

import java.io.*;

public class Main {

    public static void main(String[] args) {

        FileManager fileM = new FileManager();
        File file = new File("expose.odt");
        File newFile = fileM.changeExtension(file, ".zip");
        fileM.readMetaData(newFile);

    }

}
