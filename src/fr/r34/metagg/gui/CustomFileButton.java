package fr.r34.metagg.gui;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.Strings;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.Dimension;

public class CustomFileButton extends JButton {

    private static Color color = new Color(39, 51, 67);
    private static Color colorSelection = new Color(23, 38, 54);

    private static Color colorInsideBorder = new Color(250, 250, 250, 0); //Transparent

    public CustomFileButton(MetaFile metaFile) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        super();
        this.setBorderPainted(false);
        this.setBackground(color);
        this.setOpaque(true);
        this.setPreferredSize(new Dimension(271, 271));
        double round = (double) Math.round(metaFile.getSize() * 10) / 10;
        this.setText("<html><p style=\"margin-right: 150px\">" + metaFile.getFile().getName() + "<br><br>Taille : <br>" + "<font color=#577297>" + round + "Ko</html>");
        this.setFont(new Font(fr.r34.metagg.gui.Dimension.FONT, Font.PLAIN, fr.r34.metagg.gui.Dimension.PARAGRAPH_SIZE));
        this.setForeground(Color.WHITE);
        this.setIcon(new ImageIcon(Strings.FILE_BUTTON_ICON_PATH));
        this.setVerticalTextPosition(AbstractButton.BOTTOM);
        this.setHorizontalTextPosition(AbstractButton.CENTER);
        this.setIconTextGap(10);
        this.setVisible(true);
        this.setUImanagerCustomFileButton();
    }

    public void setUImanagerCustomFileButton() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        UIManager.put("Button.focus", new ColorUIResource(color));
        UIManager.put("ToggleButton.focus", new ColorUIResource(color));
        UIManager.put("Button.shadow", new ColorUIResource(color));
        UIManager.put("Button.select", new ColorUIResource(colorSelection));
        UIManager.put("Button.border", new ColorUIResource(color));
    }
}
