package fr.r34.metagg.gui.custombuttons;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.Strings;
import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.gui.MainMenuGUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class CustomAddKeywordButton extends JButton {

    /**
     * Bouton permettant l'ajout d'un mot-clé dans un fichier donné en paramètre. Lorsque l'on clique sur le bouton la popup apparaît donc nous laissant saisir un mot-clé.
     * Si l'on ne saisit rien, rien ne se passe par contre si l'on saisit quelque chose, on ajoute donc
     * le mot clé à notre fichier et on met à jour le panneau de droite (car le nouveau mot-clé doit apparaitre à l'écran
     * @param main L'instance de la classe principale contenant la frame principale de l'application
     * @param metaFile Le fichier dont on souhaite ajouter la possiblité d'y ajouter un mot clé
     */
    public CustomAddKeywordButton(MainMenuGUI main, MetaFile metaFile) {
        super();
        this.setText(Strings.ADD_KEYWORD);
        this.setOpaque(true);
        this.setBackground(Colors.WHITE);
        this.setHorizontalTextPosition(AbstractButton.RIGHT);
        this.setPreferredSize(new Dimension(fr.r34.metagg.gui.Dimension.BUTTON_WIDTH, fr.r34.metagg.gui.Dimension.BUTTON_HEIGHT));
        this.setFont((new Font(fr.r34.metagg.gui.Dimension.FONT, Font.PLAIN, fr.r34.metagg.gui.Dimension.SUBTITLE_SIZE)));
        // Gestion de l'ajout du mot-clé
        this.addActionListener(e -> {
            String keyword = JOptionPane.showInputDialog(Strings.ENTER_KEYWORD);
            if (keyword == null || keyword.isEmpty()) return;
            metaFile.getKeywords().add(keyword);
            metaFile.save();
            try {
                main.updateRightPanel(metaFile);
            } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IOException |
                     IllegalAccessException ex) {
                ex.printStackTrace();
            }
        });
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
