package fr.r34.metagg.gui.panels;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.Strings;
import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.manager.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class KeywordsPanel extends JPanel {

    public KeywordsPanel(MetaFile metaFile, ArrayList<JTextField> keywordFields) {
        Utils utils = new Utils();
        JPanel mainPanel = new JPanel();
        try {
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            for (String keyword : metaFile.getKeywords()) {

                JLabel closeImg = new JLabel(utils.getImageFromResource(Strings.CLOSE_FILE_PATH));
                closeImg.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                JPanel keywordPanel = new JPanel(new GridLayout(1, 2, 30, 30));
                JTextField keywordField = new JTextField(keyword);
                closeImg.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
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
            JScrollPane scrollPane = new JScrollPane(mainPanel);
            scrollPane.setPreferredSize(new Dimension((int) (0.25* fr.r34.metagg.gui.Dimension.WINDOW_WIDTH), 100));
            scrollPane.setBorder(null);
            this.add(scrollPane);
            this.setBackground(Colors.BLUE_1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
