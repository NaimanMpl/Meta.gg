package fr.r34.metagg.gui.panels.openfile;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.Strings;
import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.gui.custombuttons.CustomFileButton;
import fr.r34.metagg.gui.Dimension;
import fr.r34.metagg.gui.MainMenuGUI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


public class MainLeftPanel extends JPanel {

    private final JPanel header, filesContainer;
    private final JLabel appTitle, recentFiles;
    private static int LABEL_WIDTH = (int) (0.7* Dimension.WINDOW_WIDTH);
    private static int LABEL_HEIGHT = Dimension.WINDOW_HEIGHT;
    private final MainMenuGUI main;

    /**
     * Panneau gauche de l'application, contenant les fichiers récemments ouverts ainsi que le titre de l'application
     * Ce panneau affiche les fichiers récemments ouverts sous la forme d'une grille 3x3 et laisse des cases vides
     * si jamais il y a une minorité de fichiers ouverts par rapport à la place disponible.
     * @param main L'instance de la classe principale contenant la frame principale de l'application
     * @throws UnsupportedLookAndFeelException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public MainLeftPanel(MainMenuGUI main) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, ParserConfigurationException, SAXException {

        this.main = main;

        header = new JPanel();
        filesContainer = new JPanel();

        appTitle = new JLabel(Strings.APP_TITLE);
        recentFiles = new JLabel(Strings.RECENT_FILES_TITLE);

        appTitle.setFont(new Font(Dimension.FONT, Font.PLAIN, Dimension.TITLE_SIZE));
        appTitle.setForeground(Colors.WHITE);
        appTitle.setBorder(new EmptyBorder(
                Dimension.DEFAULT_MARGIN,
                Dimension.DEFAULT_MARGIN,
                0,0
        ));

        recentFiles.setFont(new Font(Dimension.FONT, Font.PLAIN, Dimension.TITLE_SIZE));
        recentFiles.setForeground(Colors.WHITE);
        recentFiles.setBorder(new EmptyBorder(
                Dimension.COMPONENT_MARGIN_TOP,
                Dimension.DEFAULT_MARGIN,
                0, 0
        ));

        this.setLayout(new BorderLayout());

        header.add(appTitle);
        header.add(recentFiles);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(Colors.BG_COLOR);

        // Grille contenant les fichiers récemments ouverts
        filesContainer.setLayout(new GridLayout(3, 3, Dimension.DEFAULT_MARGIN, Dimension.DEFAULT_MARGIN));
        filesContainer.setBackground(Colors.BG_COLOR);
        filesContainer.setBorder(new EmptyBorder(
                Dimension.COMPONENT_MARGIN_TOP,
                Dimension.DEFAULT_MARGIN,
                Dimension.DEFAULT_MARGIN,
                Dimension.DEFAULT_MARGIN
        ));

        // Chargement des fichiers récents pour y remplir la grille
        loadRecentFiles();

        this.add(header, BorderLayout.NORTH);
        this.add(filesContainer, BorderLayout.CENTER);

        this.setPreferredSize(new java.awt.Dimension(LABEL_WIDTH, LABEL_HEIGHT));
        this.setBackground(Colors.BG_COLOR);
    }

    /**
     * Charge les fichiers récemments ouverts depuis le fichier cache, puis remplit la grille en fonction des fichiers présents dans le cache.
     * @throws UnsupportedLookAndFeelException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    private void loadRecentFiles() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(new File(Strings.CACHE_PATH));
        NodeList recentFilesList = doc.getElementsByTagName("file");
        for (int i = 0; i < Dimension.MAX_RECENT_FILES_SIZE; i++) {
            /*
            Si jamais il y a une minorité de fichiers ouverts par rapport au nombre de case disponible.
            Dans ce cas, on affiche des cases vides.
             */
            if (i >= recentFilesList.getLength()) {
                filesContainer.add(new CustomFileButton());
                continue;
            }
            Node recentFileNode = recentFilesList.item(i);
            if (recentFileNode.getNodeType() != Node.ELEMENT_NODE) continue;
            Element recentFile = (Element) recentFileNode;
            Node pathNode = recentFile.getElementsByTagName("path").item(0);
            if (pathNode.getNodeType() != Node.ELEMENT_NODE) continue;
            Element pathElement = (Element) pathNode;
            String path = pathElement.getTextContent();
            File file = new File(path);
            MetaFile metaFile = new MetaFile(file);
            CustomFileButton fileBtn = new CustomFileButton(metaFile);
            if (!main.getMetaFilesOpened().contains(metaFile)) main.getMetaFilesOpened().add(metaFile);
            /*
            Si jamais on clique sur un fichier récemment ouvert, il faut charger ses données puis
            les afficher sur le panneau de droite de l'application. Ce qui implique que l'on doit mettre
            à jour l'affichage lorsque l'on appuie sur ce bouton.
             */
            fileBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        main.updateRightPanel(metaFile);
                    } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException |
                             ClassNotFoundException | IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            filesContainer.add(fileBtn);
        }
    }
}
