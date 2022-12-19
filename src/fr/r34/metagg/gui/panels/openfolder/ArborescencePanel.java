package fr.r34.metagg.gui.panels.openfolder;

import fr.r34.metagg.Strings;
import fr.r34.metagg.gui.Arborescence;
import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.gui.MainMenuGUI;
import fr.r34.metagg.manager.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class ArborescencePanel extends JPanel {

    private JLabel arborescencePath, returnButtonIcon;
    private JPanel top;
    private JPanel returnButton;
    private ArrayList<File> folderContent = new ArrayList<>();
    private MainMenuGUI main;
    private Utils utils;

    /**
     * JPanel modifié selon un design prédéfinis pour rendre l'interface
     * plus claire et plus ergonomique pour l'utilisateur.
     * Ce JPanel est un panel qui se compose des éléments qui vont représenter
     * l'arborescence d'un dossier ouvert par l'utilisateur avec :
     * - Les dossiers présents dans le dossier "parent" sous forme de CustomFolderButton
     * - Les fichiers ODT présnents dans le dossier "parent" sous forme de
     * CustomFileInFolder
     * - Un texte représentant le chemin du dossier ouvert initialement
     * vers  le dossier ouvert actuellement (grand-parent > parent > fille)
     * - Un JPanel personnalisé comme bouton selon un design prédéfinis et
     * qui a pour but de retourner une fois par clique en arrière dans
     * l'arborescence.
     *
     * Le texte d'arborescence est généré et mis à jour grâce la liste "listeFileName",
     * à chaque fois qu'un dossier est ajouté ou retiré de la liste "ListeFile" le texte
     * est modifié en fonction des éléments présents dans la liste "listeFileName" et
     * chaque élément est séparé pour un caractère spéciale (ici '>') pour mieux illustrer
     * le chemin d'arborescence.
     *
     * Une fois ce bouton "retour" cliqué, si et seulement si la taille de la
     * liste de dossier "listeFile" est strictement supérieur à 1 alors on
     * détermine et on efface le dernier élément de "listeFile" et "listFileName".
     * On détermine ensuite le nouveau dernier élément et on met à jour le JPanel
     * "FolderLeftPanel" avec ce dernier élément qui est lui aussi un dossier.
     * @param parentFolder
     * @param main
     * @throws IOException
     */
    public ArborescencePanel(File parentFolder, MainMenuGUI main) throws IOException {
        super();
        this.main = main;
        this.utils = new Utils();
        this.setBackground(Colors.BG_COLOR);
        this.setBorder(new EmptyBorder(fr.r34.metagg.gui.Dimension.COMPONENT_MARGIN_TOP, 2* fr.r34.metagg.gui.Dimension.DEFAULT_MARGIN, fr.r34.metagg.gui.Dimension.DEFAULT_MARGIN, fr.r34.metagg.gui.Dimension.DEFAULT_MARGIN));
        Arborescence arborescence = new Arborescence();
        folderContent = arborescence.getArborescence(parentFolder);
        arborescencePath = new JLabel();
        String arborescencePathText = main.getListFileName().stream().map(Objects::toString).collect(Collectors.joining(">"));
        arborescencePath.setText("<html><font color=#273343>" + arborescencePathText + "</html>");
        arborescencePath.setFont(new Font(fr.r34.metagg.gui.Dimension.FONT, Font.PLAIN, fr.r34.metagg.gui.Dimension.TITLE_SIZE));
        ImageIcon myFolderIcon = new Utils().getImageFromResource(Strings.MY_FOLDER_ICON_PATH);
        arborescencePath.setIcon(myFolderIcon);

        returnButton = new JPanel();
        returnButton.setBackground(Colors.BG_COLOR);
        returnButton.setOpaque(true);
        returnButton.setVisible(true);
        returnButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        returnButtonIcon = new JLabel(utils.getImageFromResource(Strings.RETURN_BUTTON_ICON));
        returnButton.add(returnButtonIcon);
        returnButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(main.getListFile().size() > 1) {
                    try {
                        int lastElement = main.getListFile().size() - 1;
                        main.getListFileName().remove(lastElement);
                        main.getListFile().remove(lastElement);

                        int newLastElement = main.getListFile().size() - 1;
                        main.updateFolderLeftPanel(main.getListFile().get(newLastElement));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        top = new JPanel();
        top.setBackground(Colors.BG_COLOR);
        top.setLayout(new FlowLayout(FlowLayout.LEFT));
        top.add(returnButton);
        top.add(arborescencePath);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(top);
        this.add(new FolderPanel(folderContent, main));
        this.setVisible(true);
    }
}
