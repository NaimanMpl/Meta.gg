package  fr.r34.metagg;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class MetaFile {

    private String title, subject, mime;
    private final File file, destDir;
    private final FileManager fileM;
    private int pagesAmount, paragraphAmount, wordAmount, characterAmount;
    private Date creationDate;
    private float size;
    private final ArrayList<String> keywords;

    public MetaFile(File file) {
        this.file = file;
        this.title = null;
        this.subject = null;
        this.pagesAmount = 0;
        this.paragraphAmount = 0;
        this.wordAmount = 0;
        this.characterAmount = 0;
        this.creationDate = null;
        this.size = 0;
        this.keywords = new ArrayList<>();
        this.fileM = new FileManager();
        this.destDir = new File(file.getName().substring(0, file.getName().lastIndexOf(".")));
        fileM.readMetaData(file);
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
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

    public String getMime() {
        return mime;
    }

    public File getDestDir() {
        return destDir;
    }
}