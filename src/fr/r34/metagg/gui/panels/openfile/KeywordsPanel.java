package fr.r34.metagg.gui.panels.openfile;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.Constants;
import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.manager.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class KeywordsPanel extends JPanel {

    /**
     * Panneau stockant dans un JScrollPane l'ensemble des mots-clés d'un fichier donné en paramètre.
     * Ce panneau est composé d'un panneau "principal" qui aura une JScrollPane.
     * Quant au panneau principal pour chaque mot-clé, on va lui ajouter un autre panneau contenant un JLabel avec le mot-clé.
     * En cas de surplus de lien l'utilisateur pourra simplement scroller dans les mots-clés.
     * @param metaFile Le fichier dont on souhaite afficher les mots-clés
     * @param keywordFields La liste des champs de texte contenant les mots clés
     */
    public KeywordsPanel(MetaFile metaFile, ArrayList<JTextField> keywordFields) {
        Utils utils = new Utils();
        JPanel mainPanel = new JPanel();
        try {
            // Création d'un panel "principal" qui va contenir d'autre panel. (Un mot clé = 1 panel)
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            for (String keyword : metaFile.getKeywords()) {

                JLabel closeImg = new JLabel(utils.getImageFromResource(Constants.CLOSE_FILE_PATH));
                closeImg.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                JPanel keywordPanel = new JPanel(new GridLayout(1, 2, 10, 10));
                JTextField keywordField = new JTextField(keyword);

                // Ajout de la possibilité de supprimer un mot-clé à l'aide de la croix situé à côté
                closeImg.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Si jamais l'utilisateur n'a pas au préalable appuyer sur le bouton "éditer" il ne peut retirer un mot clé.
                        if (!keywordField.isEditable()) return;
                        mainPanel.remove(keywordPanel);
                        mainPanel.revalidate();
                        mainPanel.repaint();
                        keywordFields.remove(keywordField);
                    }
                });

                keywordPanel.setBackground(Colors.BLUE_1);
                keywordPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

                keywordField.setEditable(false);
                keywordField.setBackground(Colors.BLUE_1);
                keywordField.setForeground(Color.WHITE);

                keywordPanel.add(keywordField);
                keywordPanel.add(closeImg);

                keywordFields.add(keywordField);
                mainPanel.add(keywordPanel);
            }
            /*
            Création d'une scrollPane à partir du panneau "principal" pour gérer le cas où on aurait
            un surplus de mot-clé. Dans ce cas, on peut alors simplement scroll dans la liste des mots-clés
             */
            JScrollPane scrollPane = new JScrollPane(mainPanel);
            scrollPane.setPreferredSize(new Dimension((int) (0.25* fr.r34.metagg.gui.Dimension.WINDOW_WIDTH), 100));
            scrollPane.setBorder(null);
            scrollPane.setBackground(Colors.BLUE_1);
            this.add(scrollPane);
            mainPanel.setBackground(Colors.BLUE_1);
            scrollPane.setBackground(Colors.BLUE_1);
            this.setBackground(Colors.BLUE_1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
