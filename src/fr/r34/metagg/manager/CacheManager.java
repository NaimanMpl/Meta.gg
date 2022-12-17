package fr.r34.metagg.manager;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.Strings;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
     * Créer le fichier cache à la racine de là ou l'utilisateur lance l'application
     * si ce dernier n'existe pas.
     */
    public void initCache() {
        if (new File(Strings.CACHE_PATH).exists()) return;
        try {
            Element root = doc.createElement("cache");
            doc.appendChild(root);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult res = new StreamResult(new File(Strings.CACHE_PATH));
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
            Node filesContainer = doc.getElementsByTagName("cache").item(0);
            if (filesContainer.getNodeType() == Node.ELEMENT_NODE) {
                Element recentFiles = (Element) filesContainer;
                Element fileChild = doc.createElement("file");
                Element name = doc.createElement("name");
                Element path = doc.createElement("path");

                name.setTextContent(metaFile.getFile().getName());
                path.setTextContent(metaFile.getFile().getAbsolutePath());

                fileChild.appendChild(name);
                fileChild.appendChild(path);

                recentFiles.insertBefore(fileChild, recentFiles.getFirstChild());
                try (FileOutputStream fos = new FileOutputStream(Strings.CACHE_PATH)) {
                    new FileManager().writeXml(doc, fos);
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
                System.out.println("✅ Le fichier " + metaFile.getFile().getName() + " a bien été ajouté au cache !");
            }
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
