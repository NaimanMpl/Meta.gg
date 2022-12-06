package fr.r34.metagg.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BasicBackgroundPanel extends JPanel {
    private Image background;

    public BasicBackgroundPanel(String fileName) throws IOException {
        this.background = ImageIO.read(new File(fileName));
        setLayout(new BorderLayout() );
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null); // image full size
        //g.drawImage(background, 0, 0, getWidth(), getHeight(), null); // image scaled
    }

}