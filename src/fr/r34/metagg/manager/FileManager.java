package fr.r34.metagg.manager;

import fr.r34.metagg.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileManager {

    /**
     * Gestionnaire de fichiers permettant de lire et accéder aux métadonnées d'un fichier
     * @version 0.0.1
     * @author Naiman Mpl, Andrea PL
     */
    private final static String DOCUMENT_STATISTIC_TAG = "meta:document-statistic";
    private final static String OFFICE_META_TAG = "office:meta";
    private final static int BUFFER_SIZE = 1024;

    /** Extrait les métadonnées d'un fichier odt passé en paramètres pour en lire le contenu et l'insérer dans l'objet metaFile
     * @param metaFile Fichier dont on veut lire les métadonnées
     */
    public void readMetaData(MetaFile metaFile) {
        File file = metaFile.getFile();
        // Extraction du fichier puis récupération des fichiers "meta.xml" et "content.xml"
        ArrayList<File> metaFiles = this.unzip(file, metaFile.getDestDir());
        initMetaXML(metaFile);
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc;
            for (File f : metaFiles) {
                if (f.getName().endsWith(".xml") && f.getName().equalsIgnoreCase("meta.xml")) {
                    this.readAttribute(metaFile, f, MetaAttributes.TITLE);
                    this.readAttribute(metaFile, f, MetaAttributes.SUBJECT);
                    this.readAttribute(metaFile, f, MetaAttributes.AUTHOR);
                    this.readAttribute(metaFile, f, MetaAttributes.CREATION_DATE);
                    this.readAttribute(metaFile, f, MetaAttributes.KEYWORD);
                    this.readDiverseData(metaFile, f, MetaAttributes.PAGE_COUNT);
                    this.readDiverseData(metaFile, f, MetaAttributes.CHARACTERS_COUNT);
                    this.readDiverseData(metaFile, f, MetaAttributes.WORD_COUNT);
                    this.readDiverseData(metaFile, f, MetaAttributes.PARAGRAPHS_COUNT);
                }
                if(f.getName().equalsIgnoreCase("content.xml")) {
                    ArrayList<String> hyperTxtWbList = new ArrayList<>();
                    FileReader fileReader = new FileReader(f.getAbsolutePath());
                    BufferedReader br = new BufferedReader(fileReader);
                    String lineToFound ="<text:a xlink:href=";
                    String lineCut = "";
                    String hyperTxtWeb = "";
                    String line = br.readLine();
                    line = br.readLine();
                    int indexD = line.indexOf(lineToFound);
                    int indexF;
                    while (indexD > -1) {
                        lineCut = line.substring(indexD + 20);
                        indexF = lineCut.indexOf('"');
                        hyperTxtWeb = line.substring(indexD + 20, indexD + 20 + indexF);
                        if(!hyperTxtWbList.contains(hyperTxtWeb)){
                            hyperTxtWbList.add(hyperTxtWeb);
                        }
                        line = line.substring(indexD + 20 + indexF);
                        indexD = line.indexOf(lineToFound);
                    }
                    for (String weblink : hyperTxtWbList){
                        metaFile.getHyperTextWebList().add(weblink);
                    }
                    fileReader.close();
                    br.close();
                    this.readPictureMetaData(metaFile);
                    this.readThumbnail(metaFile);
                }
            }
        } catch (ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Génère un fichier "meta.xml" dans le cas ou le fichier renseigné en paramètre ne dispose pas de métadonnées.
     * @param metaFile Le fichier dont on souhaite lire les métadonnées
     */
    private void initMetaXML(MetaFile metaFile) {
        if (new File(metaFile.getDestDir().getAbsolutePath() + "/meta.xml").exists()) return;
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element root = doc.createElement("office:document-meta");
            /*
            Attribution des namespaces "meta", "dc", "office" et "xlink", nécessaire pour
            formaliser le fichier XML et permettre la création des balises pour les métadonnées.
             */
            root.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:office", "urn:oasis:names:tc:opendocument:xmlns:office:1.0");
            root.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:dc", "http://purl.org/dc/elements/1.1/");
            root.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:meta", "urn:oasis:names:tc:opendocument:xmlns:meta:1.0");
            root.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xlink", "http://www.w3.org/1999/xlink");
            doc.appendChild(root);
            root.appendChild(doc.createElement("office:meta"));
            // Ecriture du fichier "meta.xml" dans le dossier de destination
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult res = new StreamResult(new File(metaFile.getDestDir().getAbsolutePath() + "/meta.xml"));
            transformer.transform(source, res);
            System.out.println("✅ Le fichier 'meta.xml' a bien été généré !");
        } catch (TransformerException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Permet de lire un attribut spécifique d'un fichier XML rentré en paramètre
     * @param metaFile Le fichier où l'on souhaite sauvegarder la donnée lue
     * @param xmlFile Le fichier XML dont on souhaite extraire les données de l'attribut
     * @param attribute L'attribut que l'on souhaite lire
     */
    public void readAttribute(MetaFile metaFile, File xmlFile, MetaAttributes attribute) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc;
            if (xmlFile.getName().equalsIgnoreCase("meta.xml")) {
                doc = builder.parse(xmlFile);
                doc.getDocumentElement().normalize();
                // Chargement de la liste des métadonnées
                NodeList metaDatasList = doc.getElementsByTagName(OFFICE_META_TAG);
                // Parcours de la liste des métadonnées
                for (int i = 0; i < metaDatasList.getLength(); i++) {
                    Node node = metaDatasList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element metaElement = (Element) node;
                        String tag = attribute.getTag();
                        // Les mots clés ont un comportement spécial, il faut les lire un par un.
                        if (attribute == MetaAttributes.KEYWORD) {
                            // Chargement de la liste des mots clés
                            NodeList keywordsList = metaElement.getElementsByTagName(attribute.getTag());
                            for (int j = 0; j < keywordsList.getLength(); j++) {
                                Node keyword = keywordsList.item(j);
                                // Attribution dans la liste des mots clés de notre objet
                                metaFile.getKeywords().add(keyword.getTextContent());
                            }
                            continue;
                        }
                        Node metaItem = metaElement.getElementsByTagName(tag).item(0);
                        if (metaItem == null) continue;
                        String metaData = metaItem.getTextContent();
                        // Affectation de la métadonnée dans les attributs de notre objet
                        metaFile.updateAttribute(attribute, metaData);
                    }
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Permet de lire un attribut spécifique d'un fichier XML rentré en paramètre mais ne regarde que les attributs
     * parmis les données diverses
     * @param metaFile Le fichier où l'on souhaite sauvegarder la donnée lue
     * @param xmlFile Le fichier XML dont on souhaite extraire les données de l'attribut
     * @param attribute L'attribut que l'on souhaite lire
     */
    public void readDiverseData(MetaFile metaFile, File xmlFile, MetaAttributes attribute) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc;
            if (xmlFile.getName().equalsIgnoreCase("meta.xml")) {
                doc = builder.parse(xmlFile);
                doc.getDocumentElement().normalize();
                // Chargement de la liste des métadonnées
                NodeList metaDatasList = doc.getElementsByTagName(OFFICE_META_TAG);
                Node node = metaDatasList.item(0);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element metaElement = (Element) node;
                    Node metaItem = metaElement.getElementsByTagName(DOCUMENT_STATISTIC_TAG).item(0);
                    // Si les données diverses n'existe pas, inutile de continuer
                    if (metaItem == null) return;
                    if (metaItem.getAttributes().getLength() == 0) return;
                    if (metaItem.getNodeType() == Node.ELEMENT_NODE) {
                        Element metaItemElement = (Element) metaItem;
                        String metaData = metaItemElement.getAttribute(attribute.getTag());
                        // Affectation de la métadonnée dans les attributs de notre objet
                        if (!metaData.isBlank()) metaFile.updateAttribute(attribute, metaData);
                    }
                }
            }
        } catch (SAXException | ParserConfigurationException | IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Récupère la miniature du fichier ODT et l'affiche sous forme de frame
     * @param metaFile Le dossier dans lequel est stockée la miniature
     */
    public void readThumbnail(MetaFile metaFile) {
    	File thumbnail = null;
        File file = new File(metaFile.getDestDir().getPath() + "/Thumbnails");
        if (!file.exists()) return;
    	if(file.getName().equalsIgnoreCase("thumbnails")) {
            // Parcours de la liste des images, dans le dossier thumbnails
    		for (File fileOfThumbnails : file.listFiles()) {
    			if (fileOfThumbnails.getName().equalsIgnoreCase("thumbnail.png")) {
                    thumbnail = fileOfThumbnails;
                    // Affectation de la miniature à l'objet
                    metaFile.setThumbnail(thumbnail);
                }
    		}
    	}
    }
    /**
    * Récupère et affiche les métadonnées (nom/type mime/poids en Ko) des images présentes dans le fichier ODT passé
    * @param metaFile Le dossier contenant les différentes images (media) du fichier ODT étudié
    */
    public void readPictureMetaData(MetaFile metaFile) {
        // Attribution du nom de dossier contenant les images
        String folderMediaName = "";
        MimeTypeOD mimeTypeOD = metaFile.getMimeTypeOD();
        switch (mimeTypeOD) {
            case ODT:
                folderMediaName = "media";
                break;
            default:
                folderMediaName = "Pictures";
                break;
        }

        File file = new File(metaFile.getDestDir().getAbsolutePath() + "/" + folderMediaName);
        try {
            // Lecture du dossier contenant les images
            if (file.getName().equals(folderMediaName) && file.isDirectory()) {
                // Parcours des images
                for (File picture : file.listFiles()) {
                    ArrayList<String> pictureData = new ArrayList<>();
                    // Affectation du MIME de l'image
                    for(MimeTypeImage m : MimeTypeImage.values()){
                        // Lecture du MIME de l'image
                        String mimeType = Files.probeContentType(picture.toPath());
                        if (m.getMimetype().equals(mimeType)) {
                            pictureData.add(m.getTitle());
                            // Affection du MIME dans l'objet
                            metaFile.getPictures().put(picture, m);
                        }
                    }
                    // Formalisation de la taille de l'image en ne retenant que la première virgule
                    DecimalFormat df = new DecimalFormat("0.0");
                    float bytes = (float) picture.length() / BUFFER_SIZE;
                    pictureData.add(String.valueOf(df.format(bytes)) + "Ko");
                    // Ajout de l'image dans la liste des images de l'objet en n'oubliant pas la taille
                    metaFile.getMedia().put(picture.getName(), pictureData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sauvegarde toutes les métadonnées d'un fichier rentré en paramètre dans un fichier XML
     * @param xmlFile le fichier xml où l'on souhaite sauvegarder nos métadonnées
     * @param metaFile Le fichier qui contient les métadonnées à sauvegarder
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
                /*
                Séparation de la sauvegarde en 2 boucles, la première s'occupant de mettre à jour
                dans le fichier XML les différentes balises des données principales du fichier,
                la deuxième mettant à jour dans le fichier XML les données concernant les données
                diverses du fichier renseigné en paramètre.
                 */

                /*
                Mis à jour dans le fichier XML des métadonnées principales.
                (Titre, Sujet, Date de création, Mots-clés et Auteur)
                 */
                for (int i = 0; i < MetaAttributes.values().length - 3; i++) {
                    MetaAttributes attribute = MetaAttributes.values()[i];
                    Node metaData = officeMetaElement.getElementsByTagName(attribute.getTag()).item(0);
                    // Créer la balise de l'attribut si jamais elle n'existe pas dans le fichier XML.
                    if (metaData == null) {
                        Element newElement = doc.createElement(attribute.getTag());
                        officeMetaElement.appendChild(newElement);
                        metaData = officeMetaElement.getElementsByTagName(attribute.getTag()).item(0);
                    }
                    // Affectation dans l'objet selon l'attribut
                    switch (attribute) {
                        case TITLE:
                            metaData.setTextContent(metaFile.getTitle());
                            break;
                        case SUBJECT:
                            metaData.setTextContent(metaFile.getSubject());
                            break;
                        case AUTHOR:
                            metaData.setTextContent(metaFile.getAuthor());
                            break;
                        case CREATION_DATE:
                            metaData.setTextContent(metaFile.getCreationDate());
                            break;
                        case KEYWORD:
                            // Les mots clés ont un comportement spécial, il faut tous les supprimer avant de sauvegarder
                            NodeList keywords = officeMetaElement.getElementsByTagName(attribute.getTag());
                            int n = keywords.getLength();
                            // Supression des mots clés existants
                            for (int k = n-1; k >= 0; k--) {
                                officeMetaElement.removeChild(keywords.item(k));
                            }
                            // Ajout des mots clés
                            for (int j = 0; j < metaFile.getKeywords().size(); j++) {
                                Node keyword = keywords.item(j);
                                if (keyword == null) {
                                    keyword = doc.createElement(attribute.getTag());
                                    officeMetaElement.appendChild(keyword);
                                }
                                keyword.setTextContent(metaFile.getKeywords().get(j));
                            }
                            break;
                    }
                }
                /*
                Mis à jour dans le fichier XML des métadonnées diverses.
                (Nombre de pages, Nombre de mots, Nombre de caractères, Nombre de paragraphes)
                 */
                for (int i = 4; i < MetaAttributes.values().length; i++) {
                    MetaAttributes attribute = MetaAttributes.values()[i];
                    Node metaStatsNode = officeMetaElement.getElementsByTagName(DOCUMENT_STATISTIC_TAG).item(0);
                    // Créer la balise des statistiques si jamais elle n'existe pas dans le fichier XML.
                    if (metaStatsNode == null) {
                        Element statistic = doc.createElement(DOCUMENT_STATISTIC_TAG);
                        officeMetaElement.appendChild(statistic);
                        metaStatsNode = officeMetaElement.getElementsByTagName(DOCUMENT_STATISTIC_TAG).item(0);;
                    }
                    if (officeMetaNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element metaStatsData = (Element) metaStatsNode;
                        // Affectation de la métadonnée dans le fichier XML selon l'attribut
                        switch (attribute) {
                            case PAGE_COUNT:
                                metaStatsData.setAttribute(attribute.getTag(), String.valueOf(metaFile.getPagesAmount()));
                                break;
                            case CHARACTERS_COUNT:
                                metaStatsData.setAttribute(attribute.getTag(), String.valueOf(metaFile.getCharacterAmount()));
                                break;
                            case PARAGRAPHS_COUNT:
                                metaStatsData.setAttribute(attribute.getTag(), String.valueOf(metaFile.getParagraphAmount()));
                                break;
                            case WORD_COUNT:
                                metaStatsData.setAttribute(attribute.getTag(), String.valueOf(metaFile.getWordAmount()));
                                break;
                        }
                    }
                }
                System.out.println("Sauvegarde des métas données effectuée ! ✨");
            }
            try {
                FileOutputStream fos = new FileOutputStream(metaFile.getDestDir() + "/meta.xml");
                // Une fois le fichier XML mis à jour il faut le sauvegarder en le re-écrivant
                writeXml(doc, fos);
                fos.close();
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Créer (ou écrase si le fichier xml est déjà existant) un fichier xml
     * @param doc
     * @param output
     */
    public void writeXml(Document doc, OutputStream output) throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        Transformer transformer = transformerFactory.newTransformer();

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);
    }

    /** Extrait les fichiers et répertoires du fichier (zip) passé en paramètre
     * @param file Le fichier (zip) que l'on souhaite extraire
     * @param destDir Le dossier de destination des données extraites
     * @return metaFiles La liste des fichiers extraits (xml)
     */
    public ArrayList<File> unzip(File file, File destDir) {
        ArrayList<File> metaFiles = new ArrayList<>();
        try {
            byte[] buffer = new byte[BUFFER_SIZE];
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

    /**
     * Compresse complètement un dossier en un fichier zip
     * @param sourceDirPath Le chemin vers le dossier que l'on souhaite compresser
     * @param zipPath Le chemin où l'on souhaite sauvegarder notre dossier compressé
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

    /** Permet de changer l'extension d'un fichier quelconque en une nouvelle extension.
     * @param file Le fichier dont on souhaite changer l'extension
     * @param newExtension L'extension que l'on souhaite utiliser
     * @return Le nouveau fichier dont l'extension a été modifié
     */
    public File changeExtension(File file, String newExtension) {
        // Récupération du nom du fichier en excluant son extension
        int i = file.getName().lastIndexOf('.');
        String name = file.getName().substring(0, i);
        // Création du nouveau fichier renommé avec la nouvelle extension
        File newFile = new File(file.getParent(), name + newExtension);
        try {
            // Modification du fichier source
            Files.move(
                    file.toPath(),
                    newFile.toPath().resolveSibling(newFile.getName()),
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFile;
    }

    /**
     * Supprime complètement un dossier (existant) rentré en paramètre
     * @param folder Le dossier que l'on souhaite supprimer
     */
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
