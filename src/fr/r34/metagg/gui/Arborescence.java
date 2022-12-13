package fr.r34.metagg.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class Arborescence {

    private File parentFolder;
    private ArrayList<File> folderContent = new ArrayList<>();

    public ArrayList<File> getArborescence(File parentFolder){
        for (File element : Objects.requireNonNull(parentFolder.listFiles())){
            if(element.isDirectory()){
                folderContent.add(element);
            }
        }
        return folderContent;
    }
}
