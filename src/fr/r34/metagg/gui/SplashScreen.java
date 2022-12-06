package fr.r34.metagg.gui;

import fr.r34.metagg.MetaFile;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
public class SplashScreen {

    private ImageIcon background;
    private JWindow window;
    private JLabel bgContainer;

    public SplashScreen(){
        window = new JWindow();
        background = new ImageIcon("./assets/img/splashscreen.png");
        bgContainer = new JLabel(background);
        window.getContentPane().add(bgContainer);
        window.setSize(720, 514);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        try {
            Thread.sleep(1000);
            new MainMenuGUI();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException |
                 ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        window.dispose();
    }

    public static void main(String[] arg) throws IOException {
        new SplashScreen();
    }
}