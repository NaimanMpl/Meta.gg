package fr.r34.metagg.gui;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.gui.panels.openfolder.FolderLeftPanel;
import fr.r34.metagg.gui.panels.openfile.MainRightPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FolderMenuGUI {

    private final JFrame frame;
    private static JPanel leftPanel, rightPanel;
    private final Container container;
    private ArrayList<File> listFile = new ArrayList<>();
    private ArrayList<String> listFileName = new ArrayList<>();

    /**
     * Frame principale de l'application qui affichera l'arborescence, les
     * dossiers du dossier parent et les fichiers ODT du dossier parent (partie gauche)
     * La partie droite de la frame sera le fichier ouvert.
     *
     * @throws UnsupportedLookAndFeelException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IOException
     */
    public FolderMenuGUI() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {

        frame = new JFrame();
        File parcoursODT = new File("./ParcoursODT");
        listFile.add(parcoursODT);
        listFileName.add(parcoursODT.getName());
        leftPanel = new FolderLeftPanel(parcoursODT, this);
        rightPanel = new MainRightPanel(new MetaFile(new File("./sujet.odt")));

        container = frame.getContentPane();

        container.setLayout(new BorderLayout());

        container.add(leftPanel, BorderLayout.WEST);
        container.add(rightPanel, BorderLayout.EAST);

        frame.pack();
        frame.setSize(Dimension.WINDOW_WIDTH, Dimension.WINDOW_HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Met à jour le panel de gauche (celui qui concerne les
     * éléments du dossier parent) en fonction d'un dossier
     * pour afficher désormais les éléments de ce dossier
     * quand on clique sur le CustomFolderButton correspondant.
     *
     * @param superFolder   Dossier choisit par l'utilisateur pour afficher ses éléments sur la partie gauche du panel.
     * @throws IOException
     */
    public void updateFolderLeftPanel(File superFolder) throws IOException {
        container.remove(leftPanel);
        leftPanel = new FolderLeftPanel(superFolder, this);
        container.add(leftPanel, BorderLayout.WEST);
        container.revalidate();
        container.repaint();
    }

    /**
     * Met à jour le contenu du panneau de droite (celui concernant les informations du fichier ouvert).
     * On fait appel à cette méthode lorsque l'on clique sur un fichier ouvert récemment ou lors de
     * l'ouverture d'un fichier.
     * @param metaFile Le fichier que l'on souhaite ouvrir (ou consulter)
     * @throws UnsupportedLookAndFeelException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void updateFolderRightPanel(MetaFile metaFile) throws UnsupportedLookAndFeelException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        container.remove(rightPanel);
        rightPanel = new MainRightPanel(metaFile);
        container.add(rightPanel, BorderLayout.EAST);
        container.revalidate();
        container.repaint();
    }

    /**
     * Renvoi la liste des dossiers parcourus par l'utilisateur.
     * @return ArrayList<File>  la liste des dossiers parcourus par l'utilisateur
     */
    public ArrayList<File> getListFile() {
        return listFile;
    }

    /**
     * Renvoi la liste des noms des dossiers parcourus par l'utilisateur.
     * @return  ArrayList<File>  la liste des noms des dossiers parcourus
     */
    public ArrayList<String> getListFileName() {
        return listFileName;
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        new FolderMenuGUI();
    }
}
