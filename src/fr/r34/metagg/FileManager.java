package fr.r34.metagg;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.awt.Frame;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.*;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileManager {

    /*
     * Gestionnaire de fichiers permettant de lire et accéder aux métadonnées d'un fichier
     * @version 0.0.1
     * @author Naiman Mpl, Andrea PL
     */

    /* Récupère et affiche (terminal) les métadonnées du fichier (unzip) passé en paramètre selon un panel prédéfinis de métadonnées souhaitées
     * @param file Fichier dont on veut extraire les métadonnées
     */

    /* Extrait les métadonnées d'un fichier zip passé en paramètres pour en lire le contenu et l'afficher à l'écran
     * @param file Fichier dont on veut lire les métadonnées
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXEception
     */

    private final static String DOCUMENT_STATISTIC_TAG = "meta:document-statistic";
    private final static String OFFICE_META_TAG = "office:meta";

    public void readMetaData(MetaFile metaFile) {
        File file = metaFile.getFile();
        ArrayList<File> metaFiles = this.unzip(file, new File("./" + file.getName().substring(0, file.getName().lastIndexOf("."))));
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc;
            for (File f : metaFiles) {
                if (f.getName().endsWith(".xml") && f.getName().equalsIgnoreCase("meta.xml")) {
                    this.readAttribute(metaFile, f, MetaAttributes.TITLE);
                    this.readAttribute(metaFile, f, MetaAttributes.SUBJECT);
                    this.readAttribute(metaFile, f, MetaAttributes.CREATION_DATE);
                    this.readAttribute(metaFile, f, MetaAttributes.KEYWORD);
                    this.readDiverseData(metaFile, f, MetaAttributes.PAGE_COUNT);
                    this.readDiverseData(metaFile, f, MetaAttributes.CHARACTERS_COUNT);
                    this.readDiverseData(metaFile, f, MetaAttributes.WORD_COUNT);
                    this.readDiverseData(metaFile, f, MetaAttributes.PARAGRAPHS_COUNT);
                }
                if(f.getName().equalsIgnoreCase("content.xml")) {
                    ArrayList<String> hyperTxtWbList = new ArrayList<>();
                    BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));
                    String lineToFound ="<text:a xlink:href=";
                    String lineCut = "";
                    String hyperTxtWeb = "";
                    String line = br.readLine();
                    int indexD = 0;
                    int indexF;
                    line = br.readLine();
                    while (indexD > -1){
                        indexD = line.indexOf(lineToFound);
                        lineCut = line.substring(indexD + 20);
                        indexF = lineCut.indexOf('"');
                        hyperTxtWeb = line.substring(indexD + 20, indexD + 20 + indexF);
                        if(!hyperTxtWbList.contains(hyperTxtWeb) && indexD > -1){
                            hyperTxtWbList.add(hyperTxtWeb);
                        }
                        line = line.substring(indexD + 20 + indexF);
                    }
                    System.out.println("Liste des liens hypertextes vers des resources web : \n");
                    for (String weblink : hyperTxtWbList){
                        System.out.println("🔘\t" + weblink);
                    }
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    public void readAttribute(MetaFile metaFile, File xmlFile, MetaAttributes attribute) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc;
            if (xmlFile.getName().equalsIgnoreCase("meta.xml")) {
                doc = builder.parse(xmlFile);
                doc.getDocumentElement().normalize();
                NodeList metaDatasList = doc.getElementsByTagName(OFFICE_META_TAG);
                for (int i = 0; i < metaDatasList.getLength(); i++) {
                    Node node = metaDatasList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element metaElement = (Element) node;
                        String tag = attribute.getTag();
                        if (attribute == MetaAttributes.KEYWORD) {
                            NodeList keywordsList = metaElement.getElementsByTagName(attribute.getTag());
                            for (int j = 0; j < keywordsList.getLength(); j++) {
                                Node keyword = keywordsList.item(j);
                                metaFile.getKeywords().add(keyword.getTextContent());
                            }
                            continue;
                        }
                        Node metaItem = metaElement.getElementsByTagName(tag).item(0);
                        if (metaItem == null) continue;
                        String metaData = metaItem.getTextContent();
                        metaFile.updateAttribute(attribute, metaData);
                    }
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    public void readDiverseData(MetaFile metaFile, File xmlFile, MetaAttributes attribute) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc;
            if (xmlFile.getName().equalsIgnoreCase("meta.xml")) {
                doc = builder.parse(xmlFile);
                doc.getDocumentElement().normalize();
                NodeList metaDatasList = doc.getElementsByTagName(OFFICE_META_TAG);
                Node node = metaDatasList.item(0);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element metaElement = (Element) node;
                    Node metaItem = metaElement.getElementsByTagName(DOCUMENT_STATISTIC_TAG).item(0);
                    if (metaItem.getAttributes().getLength() == 0) return;
                    if (metaItem.getNodeType() == Node.ELEMENT_NODE) {
                        Element metaItemElement = (Element) metaItem;
                        String metaData = metaItemElement.getAttribute(attribute.getTag());
                        metaFile.updateAttribute(attribute , metaData);
                    }
                }
            }
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Récupère la miniature du fichier ODT et l'affiche sous forme de frame
     * @param file Le dossier dans lequel est stockée la miniature
     * @return thumbnail Le fichier de la miniature du fichier ODT
     */
    public File getThumbnail(File file) {
    	File thumbnail = null;
    	if(file.getName().equalsIgnoreCase("thumbnails")) {
    		for(File fileOfThumbnails : file.listFiles()) {
    			System.out.println(fileOfThumbnails.getName());
    			if(fileOfThumbnails.getName().equalsIgnoreCase("thumbnail.png")){
    				thumbnail = fileOfThumbnails;
    			}
    		}
            JFrame frame = new JFrame();
            ImageIcon thumbnailAffiche = new ImageIcon(thumbnail.getAbsolutePath());
            frame.add(new JLabel(thumbnailAffiche));
            frame.pack();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            return thumbnail;
    	}
        return null;
    }
    /*
    * Récupère et affiche les métadonnées (nom/type mime/poids en Ko) des images présentes dans le fichier ODT passé
    * @param file Le dossier contenant les différentes images (mzdia) du fichier ODT étudié
    */
    public void readPictureMetaData(File file) {
        HashMap<String, ArrayList<String>> imageMap = new HashMap<>();
        try {
            if (file.getName().equals("media") && file.isDirectory()) {
                for(File picture : file.listFiles()) {
                    ArrayList<String> pictureData = new ArrayList<>();
                    for(MimeTypeImage m : MimeTypeImage.values()){
                        String mimeType = picture.toURL().openConnection().getContentType();
                        if(m.getMimetype().equals(mimeType)){
                            pictureData.add(m.getTitle());
                        }
                    }
                    DecimalFormat df = new DecimalFormat("0.0");
                    float bytes = (float) picture.length() / 1024;
                    pictureData.add(String.valueOf(df.format(bytes)) + "Ko");
                    imageMap.put(picture.getName(), pictureData);
                }
                int nombreImage = imageMap.size();
                System.out.println("Nombre d'image : " + nombreImage);
                for(Map.Entry mapentry: imageMap.entrySet()) {
                    System.out.println("File : " + mapentry.getKey() + " data : " + mapentry.getValue());
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    /*
     * Modifie les métadonnées d'un fichier xml existant.
     * @param xmlFile le fichier xml dont on souhaite modifier les métadonnées
     * @param destDirPath Le chemin vers le dossier ou l'on souhaite sauvegarder notre fichier xml modifié
     * @param attribute L'attribut que l'on souhaite modifier
     * @param content Le contenu de l'attribut que l'on souhaite modifier
     */
    public void saveMetaDataXML(MetaFile metaFile, File xmlFile) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList metaDataList = doc.getElementsByTagName(OFFICE_META_TAG);
            Node officeMetaNode = metaDataList.item(0);
            if (officeMetaNode.getNodeType() == Node.ELEMENT_NODE) {
                Element officeMetaElement = (Element) officeMetaNode;
                for (int i = 0; i < MetaAttributes.values().length - 4; i++) {
                    MetaAttributes attribute = MetaAttributes.values()[i];
                    Node metaData = officeMetaElement.getElementsByTagName(attribute.getTag()).item(0);
                    if (metaData == null) {
                        Element newElement = doc.createElement(attribute.getTag());
                        officeMetaElement.appendChild(newElement);
                        metaData = officeMetaElement.getElementsByTagName(attribute.getTag()).item(0);
                    }
                    switch (attribute) {
                        case TITLE -> { metaData.setTextContent(metaFile.getTitle()); }
                        case SUBJECT -> { metaData.setTextContent(metaFile.getSubject()); }
                        case CREATION_DATE -> { metaData.setTextContent(metaFile.getCreationDate().toString()); }
                        case KEYWORD -> {
                            NodeList keywords = officeMetaElement.getElementsByTagName(attribute.getTag());
                            for (int j = 0; j < metaFile.getKeywords().size(); j++) {
                                Node keyword = keywords.item(j);
                                if (keyword == null) {
                                    keyword = doc.createElement(attribute.getTag());
                                    officeMetaElement.appendChild(keyword);
                                }
                                keyword.setTextContent(metaFile.getKeywords().get(j));
                            }
                        }
                    }
                }
                for (int i = 4; i < MetaAttributes.values().length; i++) {
                    MetaAttributes attribute = MetaAttributes.values()[i];
                    Node metaStatsNode = officeMetaElement.getElementsByTagName(DOCUMENT_STATISTIC_TAG).item(0);
                    if (officeMetaNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element metaStatsData = (Element) metaStatsNode;
                        switch (attribute) {
                            case PAGE_COUNT -> {
                                metaStatsData.setAttribute(attribute.getTag(), String.valueOf(metaFile.getPagesAmount()));
                            }
                            case CHARACTERS_COUNT -> {
                                metaStatsData.setAttribute(attribute.getTag(), String.valueOf(metaFile.getCharacterAmount()));
                            }
                            case PARAGRAPHS_COUNT -> {
                                metaStatsData.setAttribute(attribute.getTag(), String.valueOf(metaFile.getParagraphAmount()));
                            }
                            case WORD_COUNT -> {
                                metaStatsData.setAttribute(attribute.getTag(), String.valueOf(metaFile.getWordAmount()));
                            }
                        }
                    }
                }
                System.out.println("Sauvegarde des métas données effectuée ! ✨");
            }
            try (FileOutputStream fos = new FileOutputStream(metaFile.getDestDir() + "/meta.xml")) {
                writeXml(doc, fos);
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Créer (ou écrase si le fichier xml est déjà existant) un fichier xml
     * @param doc
     * @param output
     */
    private void writeXml(Document doc, OutputStream output) throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        Transformer transformer = transformerFactory.newTransformer();

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);
    }

    /* Extrait les fichiers et répertoires du fichier (zip) passé en paramètre
     * @param file Le fichier (zip) que l'on souhaite extraire
     * @return metaFiles La liste des fichiers extraits (xml)
     */
    public ArrayList<File> unzip(File file, File destDir) {
        ArrayList<File> metaFiles = new ArrayList<>();
        try {
            byte[] buffer = new byte[1024];
            System.out.println(file.getAbsolutePath());
            ZipInputStream zis = new ZipInputStream(new FileInputStream(file.getAbsolutePath()));
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                File newFile = new File(destDir.getAbsolutePath(), ze.getName());
                if (ze.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Impossible de créer un dossier " + newFile);
                    }
                } else {
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Impossible de créer un dossier " + newFile);
                    }
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int length = zis.read(buffer);
                    while (length > 0) {
                        fos.write(buffer, 0, length);
                        length = zis.read(buffer);
                    }
                    if (ze.getName().equalsIgnoreCase("meta.xml") || ze.getName().equalsIgnoreCase("content.xml")) metaFiles.add(newFile);
                    fos.close();
                }
                ze = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return metaFiles;
    }

    /*
     * Compresse complètement un dossier en un fichier zip
     * @param sourceDirPath Le chemin vers le dossier que l'on souhaite compresser
     * @param zipPath Le chemin ou l'on souhaite sauvegarder notre dossier compressé
     * @throws IOException
     */
    public void zip(Path sourceDirPath, Path zipPath) throws IOException {
       ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath.toFile()));
       Files.walkFileTree(sourceDirPath, new SimpleFileVisitor<Path>() {
           public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
               zos.putNextEntry(new ZipEntry(sourceDirPath.relativize(file).toString()));
               Files.copy(file, zos);
               zos.closeEntry();
               return FileVisitResult.CONTINUE;
           }
       });
       zos.close();
    }

    /*
     * @param file Le fichier dont on souhaite changer l'extension
     * @param newExtension L'extension que l'on souhaite utiliser
     * @return Le nouveau fichier dont l'extension a été modifié
     */
    public File changeExtension(File file, String newExtension) {
        int i = file.getName().lastIndexOf('.');
        String name = file.getName().substring(0, i);
        File newFile = new File(file.getParent(), name + newExtension);
        try {
            Files.move(file.toPath(), newFile.toPath().resolveSibling(newFile.getName()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFile;
    }

    public void delete(File folder) {
        if (folder.isDirectory()) {
            if (folder.list().length == 0) {
                folder.delete();
            } else {
                String files[] = folder.list();
                for (String tmp : files) {
                    File file = new File(folder, tmp);
                    delete(file);
                }
                if (folder.list().length == 0) {
                    folder.delete();
                }
            }
        } else {
            folder.delete();
        }
    }

}
