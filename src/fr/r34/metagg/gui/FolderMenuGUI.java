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

    public void updateFolderLeftPanel(File superFolder) throws IOException {
        container.remove(leftPanel);
        leftPanel = new FolderLeftPanel(superFolder, this);
        container.add(leftPanel, BorderLayout.WEST);
        container.revalidate();
        container.repaint();
    }

    public void updateFolderRightPanel(MetaFile metaFile) throws UnsupportedLookAndFeelException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        container.remove(rightPanel);
        rightPanel = new MainRightPanel(metaFile);
        container.add(rightPanel, BorderLayout.EAST);
        container.revalidate();
        container.repaint();
    }

    public ArrayList<File> getListFile() {
        return listFile;
    }

    public ArrayList<String> getListFileName() {
        return listFileName;
    }

    public void setListFile(ArrayList<File> listFile) {
        this.listFile = listFile;
    }

    public void setListFileName(ArrayList<String> listFileName) {
        this.listFileName = listFileName;
    }




    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        new FolderMenuGUI();
    }
}
