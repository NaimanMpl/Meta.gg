package fr.r34.metagg.gui;

import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

public class CustomRectangle extends JPanel {

    private final int width, height, marginLeft, marginTop;

    public CustomRectangle(int width, int height) {
        this(width, height, 0, 0);
    }

    public CustomRectangle(int width, int height, int marginLeft, int marginTop) {
        this.width = width;
        this.height = height;
        this.marginLeft = marginLeft;
        this.marginTop = marginTop;
        this.setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Colors.GRAY);
        Rectangle r = new Rectangle(marginLeft, marginTop, width, height);
        g.fillRect(
                (int) r.getX() + marginLeft,
                (int) r.getY() + marginTop,
                width, height
        );
    }

}
