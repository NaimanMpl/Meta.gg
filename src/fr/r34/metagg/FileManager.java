package fr.r34.metagg;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
        File f = new File(filePath);
        this.readMetaData(f);
    }
    /* Récupère et affiche (terminal) les métadonnées du fichier (unzip) passé en paramètre selon un panel prédéfinis de métadonnées souhaitées
     * @param file Fichier dont on veut extraire les métadonnées
     */

    public void readMetaData(File file) {
        ArrayList<File> metaFiles = this.unzip(file);
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc;
            HashMap<String, String> tagsMap = new HashMap<>();
            tagsMap.put("dc:title", "Titre");
            tagsMap.put("dc:subject", "Sujet");
            tagsMap.put("meta:creation-date", "Date de création");
            for (File f : metaFiles) {
                if (f.getName().endsWith(".xml") && f.getName().equalsIgnoreCase("meta.xml")) {
                    doc = builder.parse(f);
                    doc.getDocumentElement().normalize();
                    NodeList metaDatasList = doc.getElementsByTagName("office:meta");
                    for (int i = 0; i < metaDatasList.getLength(); i++) {
                        Node node = metaDatasList.item(i);
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            Element el = (Element) node;
                            for (Map.Entry<String, String> set : tagsMap.entrySet()) {
                                String tag = set.getKey();
                                String tagTitle = set.getValue();
                                if (el.getElementsByTagName(tag).item(0) == null) continue;
                                String metaData = el.getElementsByTagName(tag).item(0).getTextContent();
                                System.out.println(tagTitle + " : " + metaData);
                            }
                        }
                    }
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }
    /* Extrait les fichiers et répertoires du fichier (zip) passé en paramètre
     * @param file Le fichier (zip) que l'on souhaite extraire
     * @return metaFiles La liste des fichiers extraits
     */
    private ArrayList<File> unzip(File file) {
        ArrayList<File> metaFiles = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(file.getName());
            BufferedInputStream bis = new BufferedInputStream(fis);
            ZipInputStream zis = new ZipInputStream(bis);
            ZipEntry ze = zis.getNextEntry();
            byte[] buffer = new byte[1024];
            while (ze != null) {
                if (!ze.isDirectory()) {
                    if (ze.getName().equalsIgnoreCase("meta.xml")) {
                        File metaFile = new File(ze.getName());
                        FileOutputStream fos = new FileOutputStream(metaFile);
                        int len = zis.read(buffer);
                        while (len > 0) {
                            fos.write(buffer, 0, len);
                            len = zis.read(buffer);
                        }
                        metaFiles.add(metaFile);
                        fos.close();
                    }
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
     * @param file Le fichier dont on souhaite changer l'extension
     * @param newExtension L'extension que l'on souhaite utiliser
     * @return Le nouveau fichier dont l'extension a été modifié
     */
    public File changeExtension(File file, String newExtension) {
        int i = file.getName().lastIndexOf('.');
        String name = file.getName().substring(0, i);
        File newFile = new File(file.getParent(), name + newExtension);
        file.renameTo(newFile);
        return newFile;
    }

}
