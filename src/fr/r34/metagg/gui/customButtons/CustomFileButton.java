package fr.r34.metagg.gui.custombuttons;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.Strings;
import fr.r34.metagg.gui.Colors;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class CustomFileButton extends JButton {

    private static Color color = new Color(39, 51, 67);
    private static Color colorSelection = new Color(23, 38, 54);
    private BufferedImage odtIcon = null;

    public CustomFileButton() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        super();
        this.setBorderPainted(false);
        this.setBackground(color);
        this.setFocusPainted(false);
        this.setOpaque(true);
        this.setPreferredSize(new Dimension(271, 271));
        this.setVisible(true);
    }
    public CustomFileButton(MetaFile metaFile) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        super();
        URL odtUrl = this.getClass().getResource(Strings.ODT_FILE_PATH);
        if (odtUrl == null) throw new IllegalArgumentException(Strings.ERROR_ODT_ICON_NOT_LOADED);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setBackground(color);
        this.setOpaque(true);
        this.setPreferredSize(new Dimension(271, 271));
        double round = (double) Math.round(metaFile.getSize() * 10) / 10;
        this.setText("<html><p style=\"margin-right: 150px\">" + metaFile.getFile().getName() + "<br><br>Taille : <br>" + "<font color=#577297>" + round + "Ko</html>");
        this.setFont(new Font(fr.r34.metagg.gui.Dimension.FONT, Font.PLAIN, fr.r34.metagg.gui.Dimension.PARAGRAPH_SIZE));
        this.setForeground(Colors.WHITE);
        this.odtIcon = ImageIO.read(odtUrl);
        this.setIcon(new ImageIcon(odtIcon));
        this.setVerticalTextPosition(AbstractButton.BOTTOM);
        this.setHorizontalTextPosition(AbstractButton.CENTER);
        this.setIconTextGap(10);
        this.setVisible(true);
    }
}
