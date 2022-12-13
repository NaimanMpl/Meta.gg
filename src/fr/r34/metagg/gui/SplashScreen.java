package fr.r34.metagg.gui;

import fr.r34.metagg.MetaFile;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
public class SplashScreen {

    private ImageIcon background;
    private JWindow window;
    private JLabel bgContainer;

    public SplashScreen() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        window = new JWindow();
        background = new ImageIcon("./assets/img/splashscreen.gif");
        bgContainer = new JLabel(background);
        window.getContentPane().add(bgContainer);
        window.setSize(720, 512);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        new MainMenuGUI();
        new FolderMenuGUI();
        window.dispose();
    }

    public static void main(String[] arg) throws IOException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        new SplashScreen();
    }
}