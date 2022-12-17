package fr.r34.metagg.gui.panels.openfile;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.gui.Colors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

public class LinksPanel extends JPanel {

    /**
     * Panneau stockant dans un JScrollPane l'ensemble des liens hypertextes d'un fichier donné en paramètre.
     * Ce panneau est composé d'un panneau "principal" qui aura une JScrollPane.
     * Quant au panneau principal pour chaque lien, on va lui ajouter un autre panneau contenant un JLabel avec le lien hypertexte.
     * En cas de surplus de lien l'utilisateur pourra simplement scroller dans les liens hypertextes.
     * @param metaFile Le fichier dont on souhaite afficher les liens hypertextes
     */
    public LinksPanel(MetaFile metaFile) {
        // Création du panneau "principal"
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        // Parcours des liens du fichier
        for (String link : metaFile.getHyperTextWebList()) {

            JPanel linkPanel = new JPanel(new GridLayout(1, 2, 30, 30));
            JLabel linkLabel = new JLabel(link);

            linkPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
            linkPanel.setBackground(Colors.BLUE_1);
            linkLabel.setForeground(Color.WHITE);

            // Ajout du lien dans le panneau "principal"
            linkPanel.add(linkLabel);

            mainPanel.add(linkPanel);
        }
        // Définition de la JScrollPane
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setPreferredSize(new Dimension((int) (0.2* fr.r34.metagg.gui.Dimension.WINDOW_WIDTH), 200));
        scrollPane.setBorder(null);
        scrollPane.setBackground(Colors.BLUE_1);
        mainPanel.setBackground(Colors.BLUE_1);
        this.add(scrollPane);
        this.setBackground(Colors.BLUE_1);
    }
}
