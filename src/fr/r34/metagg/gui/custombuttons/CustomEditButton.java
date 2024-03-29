package fr.r34.metagg.gui.custombuttons;

import fr.r34.metagg.Constants;
import fr.r34.metagg.gui.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;

public class CustomEditButton extends JButton {

    /**
     * Bouton modifié en fonction du besoin de l'interface.
     * Ce bouton est personnalisé dans le but de rendre
     * l'interface graphique plus claire et plus propre
     * pour l'utilisateur.
     * Ce bouton sera exclusivement réservé à la fonction
     * d'édition et de sauvegarde des métadonnées d'un fichier
     * ouvert par l'utilisateur dans l'application.
     */
    public CustomEditButton() {
        super();
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setBackground(Colors.WHITE);
        this.setOpaque(true);
        this.setPreferredSize(new Dimension(fr.r34.metagg.gui.Dimension.BUTTON_WIDTH, fr.r34.metagg.gui.Dimension.BUTTON_HEIGHT));
        this.setText(Constants.EDIT);
        this.setFont((new Font(fr.r34.metagg.gui.Dimension.FONT, Font.PLAIN, fr.r34.metagg.gui.Dimension.SUBTITLE_SIZE)));
        this.setForeground(Color.BLACK);
        this.setIcon(new ImageIcon(Constants.EDIT_BUTTON_ICON_PATH));
        this.setHorizontalTextPosition(AbstractButton.RIGHT);
        this.setIconTextGap(10);
        this.setVisible(true);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

}
