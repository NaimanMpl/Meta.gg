package fr.r34.metagg.gui.custombuttons;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.Strings;
import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.manager.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class CustomFileButton extends JButton {

    private JTextArea jTextArea;
    private static Color color = new Color(39, 51, 67);
    private static Color colorSelection = new Color(23, 38, 54);
    private BufferedImage odtIcon = null;
    private String metafileNameDisplay;
    private Utils utils;

    /**
     * Bouton modifié pour correspondre aux besoins de l'interface.
     * Ce bouton a été modifié pour afficher des informations d'un
     * fichier ODT précis. Les informations affichées sur le bouton
     * sont les suivantes :
     * - Le nom du fichier
     * - La taille du fichier (en ko)
     * Le bouton va permettre à l'utilisateur d'accéder au fichier ODT
     * correspondant en cliquant dessus. Une fois le bouton pressé
     * le fichier lié au bouton va être transmis au MainRightPanel
     * pour y afficher d'avantage de métadonnées. Les informations
     * sont récupérées grâce au metafile du fichier ODT passé en paramètre.
     *
     * @param metaFile  Metafile qui va correspondre au bouton et dont on va extraire les informations
     *
     * @throws IOException
     */
    public CustomFileButton(MetaFile metaFile) throws IOException {
        super();
        this.utils = new Utils();
        URL odtUrl = this.getClass().getResource(Strings.ODT_FILE_PATH);
        if (odtUrl == null) throw new IllegalArgumentException(Strings.ERROR_ODT_ICON_NOT_LOADED);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setBackground(color);
        this.setOpaque(true);
        this.setPreferredSize(new Dimension(271, 271));
        this.metafileNameDisplay = metaFile.getFile().getName();
        double round = (double) Math.round(metaFile.getSize() * 10) / 10;
        if(metaFile.getFile().getName().length() > 10){
            int indexD = 0;
            int indexF = 9;
            metafileNameDisplay = metaFile.getFile().getName().substring(indexD, indexF);
            metafileNameDisplay += "...";
        }
        this.setText("<html><p style=\"margin-right: 150px\">" + metafileNameDisplay + "<br><br>Taille : <br>" + "<font color=#577297>" + round + "Ko</html>");
        this.setFont(new Font(fr.r34.metagg.gui.Dimension.FONT, Font.PLAIN, fr.r34.metagg.gui.Dimension.PARAGRAPH_SIZE));
        this.setForeground(Colors.WHITE);
        this.setIcon(utils.getImageFromResource(Strings.FILE_BUTTON_ICON_PATH));
        this.setVerticalTextPosition(AbstractButton.BOTTOM);
        this.setHorizontalTextPosition(AbstractButton.CENTER);
        this.setIconTextGap(10);
        this.setVisible(true);
    }

    /**
     * JPanel modifié selon le même design que le constructeur ci-dessus.
     * La fonction de ce JPanel n'est pas d'être un bouton cliquable mais
     * un bouton vide non cliquable, non lié à un fichier ODT et sans
     * information pour combler le vide dans le JPanel parent.
     * Ce bouton sera généré si le nombre de fichier récent du JPanel
     * parent est strictement inférieur à 9 (nombre de case du JPanel
     * parent arbitrairement définit pour une question de design).
     */
    public CustomFileButton() {
        super();
        this.setBorderPainted(false);
        this.setBackground(color);
        this.setFocusPainted(false);
        this.setOpaque(true);
        this.setPreferredSize(new Dimension(271, 271));
        this.setVisible(true);
    }
}
