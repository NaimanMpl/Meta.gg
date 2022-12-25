package fr.r34.metagg.gui.custombuttons;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.Constants;
import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.gui.MainMenuGUI;
import fr.r34.metagg.manager.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;

public class CustomFileInFolderButton extends JPanel {

    private static JLabel metafileNameLabel, metafileSizeLabel, metafileDateLabel, fileIcon;
    private double round;
    private Utils utils;
    private MainMenuGUI main;
    private String path = Constants.FILE_BUTTON_ICON_FOLDER_PANEL_PATH;

    /**
     * JPanel modifié pour correspondre à un bouton cliquable.
     * Ce JPanel a été modifié et personnalisé dans le but
     * de rendre l'interface plus claire et pour afficher
     * des informations d'un fichier étudié présent dans
     * un dossier ouvert et choisit par l'utilisateur.
     * Les informations affichées sont les suivantes :
     * - Le nom du fichier
     * - Le poids (en Ko) du fichier
     * - La date de création du fichier
     * @param file  Le fichier dont on affiche les informations et que l'on veut rendre accessible grâce au bouton.
     * @param main      Instance de la Frame principale FolderMenuGUI à laquelle ce bouton est rattaché.
     * @throws IOException
     */
    public CustomFileInFolderButton(File file, MainMenuGUI main) throws IOException {
        super();
        this.main = main;
        this.setBackground(Colors.BLUE_1);
        this.setOpaque(true);
        this.utils = new Utils();
        this.setPreferredSize(new Dimension(600, 60));
        metafileNameLabel = new JLabel();
        metafileNameLabel.setText(file.getName());
        metafileNameLabel.setForeground(Colors.WHITE);
        metafileNameLabel.setFont(new Font(fr.r34.metagg.gui.Dimension.FONT, Font.PLAIN, fr.r34.metagg.gui.Dimension.SUBTITLE_SIZE));
        round = (double) Math.round(file.length()) / 1000;
        metafileSizeLabel = new JLabel();
        metafileSizeLabel.setText(round + "Ko");
        metafileSizeLabel.setForeground(Colors.BLUE_0);
        metafileSizeLabel.setFont(new Font(fr.r34.metagg.gui.Dimension.FONT, Font.PLAIN, fr.r34.metagg.gui.Dimension.PARAGRAPH_SIZE));
        metafileDateLabel = new JLabel();
        String dateDisplay = "";
        try {
            FileTime creationTime = (FileTime) Files.getAttribute(file.toPath(), "creationTime");
            dateDisplay = creationTime.toString().substring(0, 10);
        } catch (IOException e) {
            e.printStackTrace();
            dateDisplay = "1970-01-01";
        }
        metafileDateLabel.setText(dateDisplay);
        metafileDateLabel.setForeground(Colors.BLUE_0);
        metafileDateLabel.setFont(new Font(fr.r34.metagg.gui.Dimension.FONT, Font.PLAIN, fr.r34.metagg.gui.Dimension.PARAGRAPH_SIZE));
        path = utils.getIconFolderPanelPathFromType(file);
        fileIcon = new JLabel(utils.getImageFromResource(path));
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.setLayout(new GridLayout(1, 3, 30, 0));
        this.add(fileIcon);
        this.add(metafileNameLabel);
        this.add(metafileSizeLabel);
        this.add(metafileDateLabel);
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        this.setVisible(true);

        /*
            Si jamais on clique sur un fichier, il faut charger ses données puis
            les afficher sur le panneau de droite de l'application. Ce qui implique que l'on doit mettre
            à jour l'affichage lorsque l'on appuie sur ce bouton.
            À chaque fois qu'on appuie sur ce bouton, on sauvegarde tous les fichiers pour éviter d'avoir
            des modifications non prises en comptes.
         */
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    main.saveAllFiles();
                    main.updateRightPanel(new MetaFile(file));
                } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException |
                         ClassNotFoundException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    /**
     * JPanel modifié selon le même design que le constructeur ci-dessus.
     * La fonction de ce JPanel n'est pas d'être un bouton cliquable, mais
     * un bouton vide non cliquable, non lié à un fichier ODT et sans
     * information pour combler le vide dans le JPanel parent.
     * Ce bouton sera généré si le nombre de fichiers ODT présent dans le
     * dossier parent est strictement inférieur à 4 (nombre de cases du JPanel
     * parent arbitrairement définit pour une question de design).
     */
    public CustomFileInFolderButton() {
        super();
        this.setBackground(Colors.BLUE_1);
        this.setOpaque(true);
        this.setPreferredSize(new Dimension(600, 60));
        this.setVisible(true);
    }
}
