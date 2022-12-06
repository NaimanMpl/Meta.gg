package fr.r34.metagg.gui;

import javax.swing.*;
import java.awt.*;

public class CustomButton extends JButton {

    private boolean over;
    private Color color, colorOver, colorClick, borderColor;
    private int radius = 0;
    private ImageIcon icon;

    public CustomButton(Color color, Color colorOver, Color colorClick, Color borderColor, ImageIcon icon){
        this.color = color;
        this.colorOver = colorOver;
        this.colorClick = colorClick;
        this.borderColor = borderColor;
        this.icon = icon;
    }

    public CustomButton(Color color, Color colorOver, Color colorClick, Color borderColor){
        this.color = color;
        this.colorOver = colorOver;
        this.colorClick = colorClick;
        this.borderColor = borderColor;
        this.icon = null;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public Color getColor() {
        return color;
    }

    public Color getColorClick() {
        return colorClick;
    }

    public Color getColorOver() {
        return colorOver;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setColorClick(Color colorClick) {
        this.colorClick = colorClick;
    }

    public void setColorOver(Color colorOver) {
        this.colorOver = colorOver;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //  Paint Border
        g2.setColor(borderColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.setColor(getBackground());
        //  Border set 2 Pix
        g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, radius, radius);
        super.paintComponent(grphcs);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        CustomButton customButton = new CustomButton(Color.BLUE, Color.cyan, Color.GREEN, Color.RED);
        frame.add(customButton);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
