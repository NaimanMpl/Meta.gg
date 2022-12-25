package fr.r34.metagg.gui.custombuttons;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.MimeTypeOD;
import fr.r34.metagg.Constants;
import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.gui.MainMenuGUI;
import fr.r34.metagg.manager.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.text.DecimalFormat;

public class CustomFileButton extends JButton {

    private JTextArea jTextArea;
    private BufferedImage odtIcon = null;
    private String metafileNameDisplay, mimeType, path = Constants.FILE_BUTTON_ICON_PATH;
    private Utils utils;
    private final static int BUFFER_SIZE = 1024;

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
     * pour y afficher davantage de métadonnées. Les informations
     * sont récupérées grâce au metafile du fichier ODT passé en paramètre.
     *
     * @param file Fichier qui va correspondre au bouton et dont on va extraire les informations
     *
     * @throws IOException
     */
    public CustomFileButton(MainMenuGUI main, File file) throws IOException {
        super();

        this.utils = new Utils();
        URL odtUrl = this.getClass().getResource(Constants.ODT_FILE_PATH);
        if (odtUrl == null) throw new IllegalArgumentException(Constants.ERROR_ODT_ICON_NOT_LOADED);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setBackground(Colors.BLUE_1);
        this.setOpaque(true);
        this.setPreferredSize(new Dimension(271, 271));
        this.metafileNameDisplay = file.getName();
        double size = (float) file.length() / BUFFER_SIZE;
        DecimalFormat df = new DecimalFormat("0.0");
        if(file.getName().length() > 10){
            metafileNameDisplay = utils.shortenText(file.getName());
        }
        this.setText("<html><p style=\"margin-right: 150px\">" + metafileNameDisplay + "<br><br>Taille : <br>" + "<font color=#577297>" + df.format(size) + "Ko</html>");
        this.setFont(new Font(fr.r34.metagg.gui.Dimension.FONT, Font.PLAIN, fr.r34.metagg.gui.Dimension.PARAGRAPH_SIZE));
        this.setForeground(Colors.WHITE);

        mimeType = Files.probeContentType(file.toPath());
        for (MimeTypeOD m : MimeTypeOD.values()){
            if(m.getMimetype().equals(mimeType)){
                switch (m) {
                    case ODP:
                        path = Constants.ODP_BUTTON_ICON_PATH;
                        break;
                    case ODS:
                        path = Constants.ODS_BUTTON_ICON_PATH;
                        break;
                    case ODG:
                        path = Constants.ODG_BUTTON_ICON_PATH;
                        break;
                }
            }
        }
        this.setIcon(utils.getImageFromResource(path));
        this.setVerticalTextPosition(AbstractButton.BOTTOM);
        this.setHorizontalTextPosition(AbstractButton.CENTER);
        this.setIconTextGap(10);
        this.setVisible(true);
        /*
            Si jamais on clique sur un fichier récemment ouvert, il faut charger ses données puis
            les afficher sur le panneau de droite de l'application. Ce qui implique que l'on doit mettre
            à jour l'affichage lorsque l'on appuie sur ce bouton.
            À chaque fois qu'on appuie sur ce bouton, on sauvegarde tous les fichiers pour éviter d'avoir
            des modifications non prises en comptes.
         */
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
     * Ce bouton sera généré si le nombre de fichiers récent du JPanel
     * parent est strictement inférieur à 9 (nombre de cases du JPanel
     * parent arbitrairement définit pour une question de design).
     */
    public CustomFileButton() {
        super();
        this.setBorderPainted(false);
        this.setBackground(Colors.BLUE_1);
        this.setFocusPainted(false);
        this.setOpaque(true);
        this.setPreferredSize(new Dimension(271, 271));
        this.setVisible(true);
    }
}
