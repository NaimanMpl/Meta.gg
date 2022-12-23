package fr.r34.metagg.gui.custombuttons;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.Strings;
import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.gui.MainMenuGUI;
import fr.r34.metagg.manager.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class CustomFileInFolderButton extends JPanel {

    private static JLabel metafileNameLabel, metafileSizeLabel, metafileDateLabel, fileIcon;
    private double round;
    private Utils utils;
    private MainMenuGUI main;
    private String path = Strings.FILE_BUTTON_ICON_FOLDER_PANEL_PATH;

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
     * @param metaFile  Le fichier dont on affiche les informations et que l'on veut rendre accessible grâce au boutton.
     * @param main      Instance de la Frame principale FolderMenuGUI à laquelle ce bouton est rattaché.
     * @throws IOException
     */
    public CustomFileInFolderButton(MetaFile metaFile, MainMenuGUI main) throws IOException {
        super();
        this.main = main;
        this.setBackground(Colors.BLUE_1);
        this.setOpaque(true);
        this.utils = new Utils();
        this.setPreferredSize(new Dimension(600, 60));
        metafileNameLabel = new JLabel();
        metafileNameLabel.setText(metaFile.getFile().getName());
        metafileNameLabel.setForeground(Colors.WHITE);
        metafileNameLabel.setFont(new Font(fr.r34.metagg.gui.Dimension.FONT, Font.PLAIN, fr.r34.metagg.gui.Dimension.SUBTITLE_SIZE));
        round = (double) Math.round(metaFile.getSize() * 10) / 10;
        metafileSizeLabel = new JLabel();
        metafileSizeLabel.setText(round + "Ko");
        metafileSizeLabel.setForeground(Colors.BLUE_0);
        metafileSizeLabel.setFont(new Font(fr.r34.metagg.gui.Dimension.FONT, Font.PLAIN, fr.r34.metagg.gui.Dimension.PARAGRAPH_SIZE));
        metafileDateLabel = new JLabel();
        metafileDateLabel.setText("" + metaFile.getCreationDate().substring(0, 10));
        metafileDateLabel.setForeground(Colors.BLUE_0);
        metafileDateLabel.setFont(new Font(fr.r34.metagg.gui.Dimension.FONT, Font.PLAIN, fr.r34.metagg.gui.Dimension.PARAGRAPH_SIZE));
        path = utils.getIconPathFromType(metaFile);
        System.out.println("PATH = " + path);
        fileIcon = new JLabel(utils.getImageFromResource(path));

        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.setLayout(new GridLayout(1, 3, 30, 0));
        this.add(fileIcon);
        this.add(metafileNameLabel);
        this.add(metafileSizeLabel);
        this.add(metafileDateLabel);
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        this.setVisible(true);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    main.updateRightPanel(metaFile);
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
