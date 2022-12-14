package fr.r34.metagg;

import fr.r34.metagg.manager.FileManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MetaFile {

    private String title, subject, mime;
    private File file, destDir;
    private File thumbnail;
    private FileManager fileM;
    private int pagesAmount, paragraphAmount, wordAmount, characterAmount;
    private String creationDate;
    private float size;
    private final ArrayList<String> keywords;
    private final HashMap<String, ArrayList<String>> media;

    private final ArrayList<String> hyperTextWebList;
    private final static int BUFFER_SIZE = 1024;

    /**
     * Initialise l'objet en chargeant les métadonnées présentes dans file pour les inscrires dans les
     * attributs de la classe
     * @param file Le fichier dont on souhaite charger les métadonnées
     */
    public MetaFile(File file) {
        this.file = file;
        this.thumbnail = null;
        this.title = "";
        this.subject = "";
        this.pagesAmount = 0;
        this.paragraphAmount = 0;
        this.wordAmount = 0;
        this.characterAmount = 0;
        this.creationDate = null;
        this.size = (float) file.length() / BUFFER_SIZE;
        this.keywords = new ArrayList<>();
        this.hyperTextWebList = new ArrayList<>();
        this.media = new HashMap<>();
        this.fileM = new FileManager();
        this.destDir = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(".")));
        fileM.readMetaData(this);
    }

    public MetaFile() {
        this.file = new File("unknown");
        this.thumbnail = null;
        this.title = "";
        this.subject = "";
        this.pagesAmount = 0;
        this.paragraphAmount = 0;
        this.wordAmount = 0;
        this.characterAmount = 0;
        this.creationDate = "01-01-1970";
        this.size = 0;
        this.keywords = new ArrayList<>();
        this.hyperTextWebList = new ArrayList<>();
        this.media = new HashMap<>();
    }

    public File getFile() {
        return file;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getPagesAmount() {
        return pagesAmount;
    }

    public void setPagesAmount(int pagesAmount) {
        this.pagesAmount = pagesAmount;
    }

    public int getParagraphAmount() {
        return paragraphAmount;
    }

    public void setParagraphAmount(int paragraphAmount) {
        this.paragraphAmount = paragraphAmount;
    }

    public int getWordAmount() {
        return wordAmount;
    }

    public void setWordAmount(int wordAmount) {
        this.wordAmount = wordAmount;
    }

    public int getCharacterAmount() {
        return characterAmount;
    }

    public void setCharacterAmount(int characterAmount) {
        this.characterAmount = characterAmount;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public ArrayList<String> getHyperTextWebList() {
        return hyperTextWebList;
    }

    public HashMap<String, ArrayList<String>> getMedia() {
        return media;
    }

    public String getMime() {
        return mime;
    }

    public File getDestDir() {
        return destDir;
    }

    public int getMediasLength() { return media.size(); }

    public void displayMetaData() {
        System.out.println("Métadonnées du fichier : " + file.getName());
        System.out.println("Données principales :");
        System.out.println("\tTitre : " + title);
        System.out.println("\tSujet : " + subject);
        System.out.println("\tDate de création : " + creationDate);
        System.out.println("\tMots-clés : " + keywords);
        System.out.println("\tNombre d'images : " + this.getMediasLength());
        for(Map.Entry<String, ArrayList<String>> entry : media.entrySet()) {
            System.out.println("\tFile : " + entry.getKey() + " data : " + entry.getValue());
        }
        System.out.println("Miniature : " + thumbnail);
        System.out.println("Données diverses :");
        System.out.println("\tNombre de pages : " + pagesAmount);
        System.out.println("\tNombre de paragraphes : " + paragraphAmount);
        System.out.println("\tNombre de mots : " + wordAmount);
        System.out.println("\tNombre de caractères : " + characterAmount);
        System.out.println("Liste des liens hypertextes :");
        for (String webLink : hyperTextWebList) { System.out.println("\t⚪️ " + webLink); }
    }

    /**
     * Sauvegarde les métadonnées de l'objet dans un nouveau fichier XML
     */
    public void save() {
        File xmlFile = new File(destDir.getAbsolutePath() + "/meta.xml");
        fileM.saveMetaDataXML(this, xmlFile);
    }

    public void deleteTempFolder() {
        File fileToZip = this.getDestDir();
        String zipFilePath = this.getDestDir().getAbsolutePath();
        File zipFile = new File(zipFilePath + ".zip");
        try {
            fileM.zip(fileToZip.toPath(), zipFile.toPath());
            fileM.changeExtension(zipFile, ".odt");
            fileM.delete(this.getDestDir());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Met à jour l'attribut (de l'objet) en fonction de l'attribut passé en paramètre
     * @param attribute L'attribut que l'on souhaite mettre à jour
     * @param content Le nouveau contenu de notre attribut
     */
    public void updateAttribute(MetaAttributes attribute, String content) throws ParseException {
        switch (attribute) {
            case TITLE -> { this.setTitle(content); }
            case SUBJECT -> { this.setSubject(content); }
            case CREATION_DATE -> { this.setCreationDate(content); }
            case KEYWORD -> { this.getKeywords().add(content); }
            case PAGE_COUNT -> { this.setPagesAmount(Integer.parseInt(content)); }
            case CHARACTERS_COUNT -> { this.setCharacterAmount(Integer.parseInt(content)); }
            case PARAGRAPHS_COUNT -> { this.setParagraphAmount(Integer.parseInt(content)); }
            case WORD_COUNT -> { this.setWordAmount(Integer.parseInt(content)); }
        }
    }

    public File getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(File thumbnail) {
        this.thumbnail = thumbnail;
    }
}
