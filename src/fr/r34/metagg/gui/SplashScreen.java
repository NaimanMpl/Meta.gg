package fr.r34.metagg.gui;

import fr.r34.metagg.Strings;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class SplashScreen {

    private ImageIcon background;
    private JWindow window;
    private JLabel bgContainer;
    private BufferedImage splashImg;

    public SplashScreen() throws IOException {
        window = new JWindow();
        URL splashUrl = this.getClass().getResource(Strings.SPLASH_SCREEN_FILE_PATH);
        if (splashUrl == null) throw new IllegalArgumentException(Strings.ERROR_SPLASH_NOT_LOADED);
        splashImg = ImageIO.read(splashUrl);
        background = new ImageIcon(splashImg);
        bgContainer = new JLabel(background);
        window.getContentPane().add(bgContainer);
        window.setSize(720, 512);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        try {
            new MainMenuGUI();
            new FolderMenuGUI();
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException |
                 ClassNotFoundException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }

        window.dispose();
    }

    public static void main(String[] arg) throws IOException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        new SplashScreen();
    }
}