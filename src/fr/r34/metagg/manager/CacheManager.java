package fr.r34.metagg.manager;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.Constants;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CacheManager {

    private final File xmlFile;
    private final DocumentBuilder docBuilder;
    private Document doc;


    /**
     * Objet qui s'occupe de gérer le cache généré lors de l'ouverture de l'application GUI.
     * Ce cache stock le nom des différents fichiers ouverts ainsi que leur chemin absolu.
     * Ce cache a pour but de simplifier l'affichage des fichiers récemments ouvert lors de
     * l'ouverture de l'application.
     * @param xmlFile Le fichier XML dont on veut se servir de cache ("cache.xml")
     * @throws ParserConfigurationException
     */
    public CacheManager(File xmlFile) throws ParserConfigurationException {
        this.xmlFile = xmlFile;
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        this.docBuilder = docFactory.newDocumentBuilder();
        this.doc = docBuilder.newDocument();
    }

    /**
     * Créer le fichier cache à la racine de là où l'utilisateur lance l'application
     * si ce dernier n'existe pas.
     */
    public void initCache() {
        if (new File(Constants.CACHE_PATH).exists()) return;
        try {
            // Définition de la premiere balise "mère" du fichier cache.xml
            Element root = doc.createElement("cache");
            doc.appendChild(root);
            // Ecriture du fichier "cache.xml"
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult res = new StreamResult(new File(Constants.CACHE_PATH));
            transformer.transform(source, res);
            System.out.println("✅ Le fichier de cache a bien été généré !");
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Permet de rajouter un fichier dans le cache, seul le nom du fichier ainsi que son chemin absolu
     * sera retenu dans le cache.
     * @param metaFile Le fichier que l'on souhaite ajouter
     */
    public void addFileToCache(MetaFile metaFile) {
        try {
            doc = docBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            // Chargement de la liste des fichiers présents dans le cache
            Node filesContainer = doc.getElementsByTagName("cache").item(0);
            if (filesContainer.getNodeType() == Node.ELEMENT_NODE) {

                Element recentFiles = (Element) filesContainer;

                // Si le fichier fait déjà partie du cache il est inutile de le traiter
                if (checkIfFileInCache(recentFiles.getElementsByTagName("file"), metaFile)) {
                    System.out.println("Le fichier " + metaFile.getFile().getName() + " est déjà dans le cache !");
                    return;
                }

                // Création des balises nécessaires pour le fichier.

                Element fileChild = doc.createElement("file");
                Element name = doc.createElement("name");
                Element path = doc.createElement("path");

                // Remplissage des balises
                name.setTextContent(metaFile.getFile().getName());
                path.setTextContent(metaFile.getFile().getAbsolutePath());

                // Ajout dans le fichier XML
                fileChild.appendChild(name);
                fileChild.appendChild(path);

                // Ajout du fichier en tête de la liste, pour avoir un historique partant du fichier le plus récent au plus vieux
                recentFiles.insertBefore(fileChild, recentFiles.getFirstChild());
                try {
                    // Ecriture du fichier "cache.xml"
                    FileOutputStream fos = new FileOutputStream(Constants.CACHE_PATH);
                    new FileManager().writeXml(doc, fos);
                    fos.close();
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
                System.out.println("✅ Le fichier " + metaFile.getFile().getName() + " a bien été ajouté au cache !");
            }
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Permet de vérifier l'existence d'un fichier renseigné en paramètre dans le cache
     * @param recentFilesList La liste des fichiers présents dans le cache
     * @param metaFile Le fichier dont on souhaite vérifier l'appatenance
     * @return Un boolean, si oui ou non le fichier est présent
     */
    private boolean checkIfFileInCache(NodeList recentFilesList, MetaFile metaFile) {
        // Parcours de la liste de fichiers présents dans le cache
        for (int i = 0; i < recentFilesList.getLength(); i++) {
            Node recentFileNode = recentFilesList.item(i);
            if (recentFileNode.getNodeType() == Node.ELEMENT_NODE) {
                Element recentFile = (Element) recentFileNode;
                // Récupération du chemin absolu vers le fichier
                String path = recentFile.getElementsByTagName("path").item(0).getTextContent();
                // Deux fichiers sont égaux s'ils ont le même chemin absolu.
                if (path.equalsIgnoreCase(metaFile.getFile().getAbsolutePath())) {
                    return true;
                }
            }
        }
        return false;
    }
}
