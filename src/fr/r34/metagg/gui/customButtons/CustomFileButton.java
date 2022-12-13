package fr.r34.metagg.gui.customButtons;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.Strings;
import fr.r34.metagg.gui.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;

public class CustomFileButton extends JButton {

    private static Color color = new Color(39, 51, 67);

    public CustomFileButton(MetaFile metaFile) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        super();
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setBackground(color);
        this.setOpaque(true);
        this.setPreferredSize(new Dimension(271, 271));
        double round = (double) Math.round(metaFile.getSize() * 10) / 10;
        this.setText("<html><p style=\"margin-right: 150px\">" + metaFile.getFile().getName() + "<br><br>Taille : <br>" + "<font color=#577297>" + round + "Ko</html>");
        this.setFont(new Font(fr.r34.metagg.gui.Dimension.FONT, Font.PLAIN, fr.r34.metagg.gui.Dimension.PARAGRAPH_SIZE));
        this.setForeground(Colors.WHITE);
        this.setIcon(new ImageIcon(Strings.FILE_BUTTON_ICON_PATH));
        this.setVerticalTextPosition(AbstractButton.BOTTOM);
        this.setHorizontalTextPosition(AbstractButton.CENTER);
        this.setIconTextGap(10);
        this.setVisible(true);
    }
}
