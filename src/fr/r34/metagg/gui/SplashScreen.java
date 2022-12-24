package fr.r34.metagg.gui;

import fr.r34.metagg.Constants;
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

    /**
     * Ouvre un page de chargement le temps que le menu principal
     * charge et se lance. Une fois que le menu principal est lancé,
     * la page de chargement se ferme automatiquement.
     * L'image a été choisie arbitrairement selon un design précis.
     * @throws IOException
     */
    public SplashScreen() throws IOException {
        window = new JWindow();
        URL splashUrl = this.getClass().getResource(Constants.SPLASH_SCREEN_FILE_PATH);
        if (splashUrl == null) throw new IllegalArgumentException(Constants.ERROR_SPLASH_NOT_LOADED);
        splashImg = ImageIO.read(splashUrl);
        background = new ImageIcon(splashImg);
        bgContainer = new JLabel(background);
        window.getContentPane().add(bgContainer);
        window.setSize(720, 512);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        UIManager.put("OptionPane.background", Colors.BLUE_1);
        UIManager.put("Panel.background", Colors.BLUE_1);
        try {
            new MainMenuGUI();
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