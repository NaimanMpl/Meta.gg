package fr.r34.metagg;

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
import javax.xml.*;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileManager {

    /*
     * Gestionnaire de fichiers permettant de lire et accéder aux métadonnées d'un fichier
     * @version 0.0.1
     * @author Naiman Mpl, Andrea PL
     */

    /*
     * @param filePath Le chemin vers le fichier dont on veut extraire les métadonnées
     */
    public void readMetaData(String filePath) {
        this.readMetaData(new File(filePath));
    }
    /* Récupère et affiche (terminal) les métadonnées du fichier (unzip) passé en paramètre selon un panel prédéfinis de métadonnées souhaitées
     * @param file Fichier dont on veut extraire les métadonnées
     */

    /* Extrait les métadonnées d'un fichier zip passé en paramètres pour en lire le contenu et l'afficher à l'écran
     * @param file Fichier dont on veut lire les métadonnées
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXEception
     */
    public void readMetaData(File file) {
        ArrayList<File> metaFiles = this.unzip(file, new File("./" + file.getName().substring(0, file.getName().lastIndexOf("."))));
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc;
            HashMap<String, String> tagsMap = new HashMap<>();
            tagsMap.put("dc:title", "Titre");
            tagsMap.put("dc:subject", "Sujet");
            tagsMap.put("meta:creation-date", "Date de création");
            tagsMap.put("meta:document-statistic", "Donées diverses :");
            HashMap<String, String> metaStatsAttrMap = new HashMap<>();

            metaStatsAttrMap.put("meta:page-count", "Nombre de pages : ");
            metaStatsAttrMap.put("meta:paragraph-count", "Nombre de paragraphes : ");
            metaStatsAttrMap.put("meta:word-count", "Nombre de mots : ");
            metaStatsAttrMap.put("meta:character-count", "Nombre de caractères : ");
            for (File f : metaFiles) {
                if (f.getName().endsWith(".xml") && f.getName().equalsIgnoreCase("meta.xml")) {
                    doc = builder.parse(f);
                    doc.getDocumentElement().normalize();
                    NodeList metaDatasList = doc.getElementsByTagName("office:meta");
                    for (int i = 0; i < metaDatasList.getLength(); i++) {
                        Node node = metaDatasList.item(i);
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            Element metaElement = (Element) node;
                            for (Map.Entry<String, String> set : tagsMap.entrySet()) {
                                String tag = set.getKey();
                                String tagTitle = set.getValue();
                                Node metaItem = metaElement.getElementsByTagName(tag).item(0);
                                if (metaItem == null) continue;
                                String metaData = metaItem.getTextContent();
                                System.out.println(tagTitle + " " + metaData);
                                if (tag.equalsIgnoreCase("meta:document-statistic")) {
                                    if (metaItem.getAttributes().getLength() == 0) continue;
                                    if (metaItem.getNodeType() == Node.ELEMENT_NODE) {
                                        Element metaItemElement = (Element) metaItem;
                                        for (Map.Entry<String, String> metaSet : metaStatsAttrMap.entrySet()) {
                                            System.out.println("\t" + metaSet.getValue() + metaItemElement.getAttribute(metaSet.getKey()));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            this.changeExtension(file, ".odt");
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    /*
     * Modifie les métadonnées d'un fichier xml existant.
     * @param xmlFile le fichier xml dont on souhaite modifier les métadonnées
     * @param destDirPath Le chemin vers le dossier ou l'on souhaite sauvegarder notre fichier xml modifié
     * @param attribute L'attribut que l'on souhaite modifier
     * @param content Le contenu de l'attribut que l'on souhaite modifier
     */
    public void modifyMetaData(File xmlFile, String destDirPath, String attribute, String content) {
        HashMap<String, String> attributeMap = new HashMap<>();
        attributeMap.put("title", "dc:title");
        attributeMap.put("subject", "dc:subject");
        attributeMap.put("keyword", "meta:keyword");
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList metaDataList = doc.getElementsByTagName("office:meta");
            Node metaNode = metaDataList.item(0);
            if (metaNode.getNodeType() == Node.ELEMENT_NODE) {
                Element metaElement = (Element) metaNode;
                Node metaData = metaElement.getElementsByTagName(attributeMap.get(attribute)).item(0);
                metaData.setTextContent(content);
                System.out.println(metaData.getTextContent());
                System.out.println("Modification de la métadonnée effectuée ✨");
            }
            try (FileOutputStream fos = new FileOutputStream(destDirPath + "/meta.xml")) {
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
                    if (ze.getName().equalsIgnoreCase("meta.xml")) metaFiles.add(newFile);
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
            Files.move(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFile;
    }

}
