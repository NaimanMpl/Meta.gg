package fr.r34.metagg.gui.custombuttons;

import fr.r34.metagg.Strings;

import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;

public class CustomEditButton extends JButton {

    private static Color color = new Color(255, 255, 255);
    private static Color colorSelection = new Color(143, 147, 150);

    public CustomEditButton() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        super();
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setBackground(color);
        this.setOpaque(true);
        this.setPreferredSize(new Dimension(fr.r34.metagg.gui.Dimension.BUTTON_WIDTH, fr.r34.metagg.gui.Dimension.BUTTON_HEIGHT));
        this.setText(Strings.EDIT);
        this.setFont((new Font(fr.r34.metagg.gui.Dimension.FONT, Font.PLAIN, fr.r34.metagg.gui.Dimension.SUBTITLE_SIZE)));
        this.setForeground(Color.BLACK);
        this.setIcon(new ImageIcon(Strings.EDIT_BUTTON_ICON_PATH));
        this.setHorizontalTextPosition(AbstractButton.RIGHT);
        this.setIconTextGap(10);
        this.setVisible(true);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

}
