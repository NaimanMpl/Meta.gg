package fr.r34.metagg.gui;

import javax.swing.*;
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
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        window.dispose();
    }

    public static void main(String[] arg) throws IOException {
        new SplashScreen();
    }
}