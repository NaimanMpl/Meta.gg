package fr.r34.metagg;

import fr.r34.metagg.manager.FileManager;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MetaFile {

    private String title, subject, author;
    private File file, destDir;
    private File thumbnail;
    private FileManager fileM;
    private int pagesAmount, paragraphAmount, wordAmount, characterAmount;
    private String creationDate;
    private MimeTypeOD mimeTypeOD;
    private float size;
    private final ArrayList<String> keywords;
    private final HashMap<String, ArrayList<String>> media;
    private final ArrayList<String> hyperTextWebList;
    private final HashMap<File, MimeTypeImage> pictures;
    private final static int BUFFER_SIZE = 1024;

    /**
     * Initialise l'objet en chargeant les métadonnées présentes dans le paramètre "file"
     * pour y inscrires dans les différents attributs de la classe les métadonnées.
     * À chaque appel du constructeur les métadonnées sont chargées et stockées dans leurs
     * attributs respectifs puis extrait le fichier ODT
     * à la racine du fichier renseigné en paramètre afin qu'on puisse modifier et lire le contenu
     * du fichier XML.
     * Cet objet a pour but de simplifier l'affichage des métadonnées ainsi que leur stockage.
     * @param file Le fichier dont on souhaite charger les métadonnées
     */
    public MetaFile(File file) throws IOException {
        this.file = file;
        // Attribution des métadonnées par défaut
        this.thumbnail = null;
        this.title = "";
        this.author = "";
        this.subject = "";
        this.pagesAmount = 0;
        this.paragraphAmount = 0;
        this.wordAmount = 0;
        this.characterAmount = 0;
        this.creationDate = "01-01-1970";
        this.size = (float) file.length() / BUFFER_SIZE;
        this.keywords = new ArrayList<>();
        this.hyperTextWebList = new ArrayList<>();
        this.media = new HashMap<>();
        this.fileM = new FileManager();
        this.pictures = new HashMap<>();
        /*
        Dossier de destination, (là ou sera extrait le contenu du fichier renseigné en paramètre)
        Si le fichier s'appelle "hello.odt" le dossier de destination sera alors "hello"
         */
        this.destDir = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(".")));
        this.mimeTypeOD = setMimeTypeOD(file);
        // Lecture des métadonnées, c'est ici que l'on va charger les métadonnées et les renseigner dans les attributs de l'objet
        fileM.readMetaData(this);
    }


    /**
     * Initialise un fichier vide, un fichier par défaut ne contenant que des informations factices.
     * Ce constructeur a pour but de simplifier l'affichage d'un fichier par défaut dans le GUI.
     */
    public MetaFile() {
        this.file = new File("unknown");
        this.thumbnail = null;
        this.title = "";
        this.author = "";
        this.subject = "";
        this.pagesAmount = 0;
        this.paragraphAmount = 0;
        this.wordAmount = 0;
        this.characterAmount = 0;
        this.creationDate = "01-01-1970";
        this.size = 0;
        this.keywords = new ArrayList<>();
        this.hyperTextWebList = new ArrayList<>();
        this.pictures = new HashMap<>();
        this.media = new HashMap<>();
    }

    /**
     * Permet de récuperer le fichier (Java) de l'objet
     * @return Le fichier (Java) de l'objet
     */
    public File getFile() {
        return file;
    }

    /**
     * Permet de récuperer le titre du fichier
     * @return Le titre du fichier
     */
    public String getTitle() {
        return title;
    }

    /**
     * Permet de modifier le titre du fichier
     * @param title Le nouveau titre que l'on souhaite attribuer au fichier
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Permet de récupérer l'auteur d'un fichier
     * @return L'auteur du fichier
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Permet de modifier l'auteur d'un fichier
     * @param author Le nouvel auteur que l'on souhaite attribuer au fichier
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Permet de récuperer le sujet d'un fichier
     * @return Le sujet du fichier
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Permet de modifier le sujet d'un fichier
     * @param subject Le nouveau sujet que l'on souhaite attribuer au fichier
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Permet de récuperer le nombre de pages d'un fichier
     * @return Le nombre de pages que l'on souhaite attribuer au fichier
     */
    public int getPagesAmount() {
        return pagesAmount;
    }

    /**
     * Permet de modifier le nombre de pages d'un fichier
     * @param pagesAmount Le nouveau nombre de pages que l'on souhaite attribuer au fichier
     */
    public void setPagesAmount(int pagesAmount) {
        this.pagesAmount = pagesAmount;
    }

    /**
     * Permet de récuperer le nombre de paragraphes d'un fichier
     * @return Le nombre de pages du fichier
     */
    public int getParagraphAmount() {
        return paragraphAmount;
    }

    /**
     * Permet de modifier le nombre de paragaphes d'un fichier
     * @param paragraphAmount Le nouveau nompbre de paragraphes que l'on souhaite attribuer au fichier
     */
    public void setParagraphAmount(int paragraphAmount) {
        this.paragraphAmount = paragraphAmount;
    }

    /**
     * Permet de récuperer le nombre de mots d'un fichier
     * @return Le nombre de mots du fichier
     */
    public int getWordAmount() {
        return wordAmount;
    }

    /**
     * Permet de modifier le nombre de mots d'un fichier
     * @param wordAmount Le nombre de mots que l'on souhaite attribuer au fichier
     */
    public void setWordAmount(int wordAmount) {
        this.wordAmount = wordAmount;
    }

    /**
     * Permet de récuperer le nombre de caractères d'un fichier
     * @return Le nombre de caractères du fichier
     */
    public int getCharacterAmount() {
        return characterAmount;
    }

    /**
     * Permet de modifier le nombre de caractères d'un fichier
     * @param characterAmount Le nombre de caractères que l'on souhaite attribuer au fichier
     */
    public void setCharacterAmount(int characterAmount) {
        this.characterAmount = characterAmount;
    }

    /**
     * Permet de récuperer la date de création d'un fichier
     * @return La date de création du fichier
     */
    public String getCreationDate() {
        return creationDate;
    }

    /**
     * Permet de modifier la date de création d'un fichier
     * @param creationDate La date de création que l'on souhaite attribuer au fichier
     */
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Permet de récuperer la taille d'un fichier
     * @return La taille du fichier
     */
    public float getSize() {
        return size;
    }

    /**
     * Permet de modifier la taille d'un fichier
     * @param size La taille que l'on souhaite attribuer au fichier
     */
    public void setSize(float size) {
        this.size = size;
    }

    /**
     * Permet de récuperer les mots clés sous la forme d'une liste de chaine de caractères
     * @return La liste des mots clés de notre fichier
     */
    public ArrayList<String> getKeywords() {
        return keywords;
    }

    /**
     * Permet de récuperer les liens hypertextes sous la forme d'une liste de chaine de caractères
     * @return La liste des liens hypertextes de notre fichier
     */
    public ArrayList<String> getHyperTextWebList() {
        return hyperTextWebList;
    }

    /**
     * Permet de récuperer les images de notre fichier sous la forme d'un dictionnaire.
     * La clé est l'image tandis que la valeur est son Mime (PNG, GIF, JPEG...)
     * @return
     */
    public HashMap<File, MimeTypeImage> getPictures() {
        return pictures;
    }

    public HashMap<String, ArrayList<String>> getMedia() {
        return media;
    }

    /**
     * Permet de récuperer le dossier extrait lors du chargement des métadonnées
     * @return Le fichier faisant référence au dossier extrait
     */
    public File getDestDir() {
        return destDir;
    }

    /**
     * Permet de récuperer le nombre d'images présentes dans un fichier
     * @return Le nombre d'images du fichier
     */
    public int getMediasLength() { return media.size(); }

    /**
     * Permet de récuperer la miniature de notre fichier
     * @return La miniature du fichier
     */
    public File getThumbnail() {
        return thumbnail;
    }

    /**
     * Permet la modification de la miniature du fichier. (Utile uniquement lors du chargement des métadonnées)
     * La nouvelle miniature ne sera pas modifié lors de la sauvegarde
     * @param thumbnail La miniature que l'on souhaite attribuer au fichier
     */
    public void setThumbnail(File thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * Affiche les différentes métadonnées stockées au préalable de manière claire et distincte.
     */
    public void displayMetaData() {
        System.out.println("Métadonnées du fichier : " + file.getName());
        System.out.println("Données principales :");
        System.out.println("\tTitre : " + title);
        System.out.println("\tSujet : " + subject);
        System.out.println("\tAuteur : " + author);
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
     * Sauvegarde les métadonnées de l'objet dans le fichier "meta.xml" extrait lors de l'initialisation
     * dans le constructeur.
     */
    public void save() {
        File xmlFile = new File(destDir.getAbsolutePath() + "/meta.xml");
        fileM.saveMetaDataXML(this, xmlFile);
    }

    /**
     * Compresse le dossier extrait lors de l'initialisation (appel du constructeur) et convertit son extension en ".odt".
     * Puis enfin, supprime le dossier extrait lors de l'initialisation de l'objet
     */
    public void deleteTempFolder() {
        File fileToZip = this.getDestDir();
        String zipFilePath = this.getDestDir().getAbsolutePath();
        File zipFile = new File(zipFilePath + ".zip");
        String newExtension = ".odt";
        switch (this.mimeTypeOD) {
            case ODS:
                newExtension = ".ods";
                break;
            case ODG:
                newExtension = ".odg";
                break;
            case ODP:
                newExtension = ".odp";
                break;
        }
        try {
            fileM.zip(fileToZip.toPath(), zipFile.toPath());
            fileM.changeExtension(zipFile, newExtension);
            fileM.delete(this.getDestDir());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Met à jour l'attribut (de l'objet) en fonction de l'attribut passé en paramètre et du
     * contenu que l'on souhaite insérer à cet attribut
     * @param attribute L'attribut que l'on souhaite mettre à jour
     * @param content Le nouveau contenu de notre attribut
     */
    public void updateAttribute(MetaAttributes attribute, String content) throws ParseException {
        switch (attribute) {
            case TITLE:
                this.setTitle(content);
                break;
            case SUBJECT:
                this.setSubject(content);
                break;
            case AUTHOR:
                this.setAuthor(content);
                break;
            case CREATION_DATE:
                this.setCreationDate(content);
                break;
            case KEYWORD:
                this.getKeywords().add(content);
                break;
            case PAGE_COUNT:
                this.setPagesAmount(Integer.parseInt(content));
                break;
            case CHARACTERS_COUNT:
                this.setCharacterAmount(Integer.parseInt(content));
                break;
            case PARAGRAPHS_COUNT:
                this.setParagraphAmount(Integer.parseInt(content));
                break;
            case WORD_COUNT:
                this.setWordAmount(Integer.parseInt(content));
                break;
        }
    }

    /**
     * Fonction qui permet de comparer deux objets de types "MetaFile" entre eux en se basant sur leur chemin absolu.
     * Si les deux MetaFile ont le même chemin absolu, alors ils sont égaux.
     * @param  object L'objet que l'on souhaite comparer
     * @return Le résultat booléen de la comparaison des deux chemins absolus.
     */
    @Override
    public boolean equals(Object object) {
        if (object == this) return true;
        if (!(object instanceof MetaFile)) return false;
        MetaFile m = (MetaFile) object;
        return this.getFile().getAbsolutePath().equalsIgnoreCase(m.getFile().getAbsolutePath());
    }

    /**
     * Permet d'attribuer un MIME à l'objet, utile lors du chargement des métadonnées.
     * Si le MIME du fichier ne se trouve pas parmi l'énumération alors le MIME "null" lui
     * est attribué.
     * @param file Le fichier auquel on souhaite attribuer un MIME
     * @return Le MIME du fichier
     * @throws IOException
     */
    private MimeTypeOD setMimeTypeOD(File file) throws IOException {
        String mimetype = Files.probeContentType(file.toPath());
        for (MimeTypeOD m : MimeTypeOD.values()){
            if(m.getMimetype().equals(mimetype)){
                return m;
            }
        }
        return null;
    }

    /**
     * Permet de récuperer le MIME d'un fichier
     * @return Le MIME du fichier
     */
    public MimeTypeOD getMimeTypeOD() {
        return mimeTypeOD;
    }
}
