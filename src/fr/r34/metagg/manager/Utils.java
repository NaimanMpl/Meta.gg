package fr.r34.metagg.manager;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Utils {

    public Utils() {}

    /**
     * Permet de charger une image depuis le fichier resource en spécifiant le chemin vers cette dernière.
     * @param path Le chemin vers l'image que l'on souhaite charger (à partir du dossier "resources")
     * @return imgIcon l'image chargé.
     * @throws IOException
     */
    public ImageIcon getImageFromResource(String path) throws IOException {
        URL odtUrl = this.getClass().getResource(path);
        if (odtUrl == null) throw new IllegalArgumentException(Constants.ERROR_ODT_ICON_NOT_LOADED);
        BufferedImage fileImg = ImageIO.read(odtUrl);
        return new ImageIcon(fileImg);
    }

    public String getIconFolderPanelPathFromType(MetaFile metaFile){
        String path = Constants.FILE_BUTTON_ICON_FOLDER_PANEL_PATH;
        switch (metaFile.getMimeTypeOD()) {
            case ODP -> path = Constants.ODP_BUTTON_ICON_FOLDER_PANEL_PATH;
            case ODS -> path = Constants.ODS_BUTTON_ICON_FOLDER_PANEL_PATH;
            case ODG -> path = Constants.ODG_BUTTON_ICON_FOLDER_PANEL_PATH;
        }
        return path;
    }

    public String getIconPathFromType(MetaFile metaFile){
        String path = Constants.FILE_BUTTON_ICON_PATH;
        if(metaFile.getMimeTypeOD() != null) {
            switch (metaFile.getMimeTypeOD()) {
                case ODP -> path = Constants.ODP_BUTTON_ICON_PATH;
                case ODS -> path = Constants.ODS_BUTTON_ICON_PATH;
                case ODG -> path = Constants.ODG_BUTTON_ICON_PATH;
            }
        }
        return path;
    }

    public String shortenText(String name){
        String shortName = "";
        shortName = name.substring(0, 8);
        shortName += "...";
        return shortName;
    }
}
