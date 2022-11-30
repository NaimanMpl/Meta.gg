package fr.r34.metagg.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Rectangle extends JComponent {

    private final int x, y;

    public Rectangle(int x,  int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(Colors.BLUE_1);
        g2.setColor(Colors.BLUE_1);

        RoundRectangle2D r = new RoundRectangle2D.Double(x, y, 200, 200, 10, 10);
        g2.draw(r);
        g2.fill(r);
    }

}
