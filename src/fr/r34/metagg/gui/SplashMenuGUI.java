package fr.r34.metagg.gui;

import javax.swing.*;
import java.io.IOException;



public class SplashMenuGUI {

    private JFrame frame;
    private final static String APP_TITLE = "Meta.gg";
    
    public SplashMenuGUI() throws IOException {
        frame = new JFrame();
        frame.setTitle(APP_TITLE);
        frame.getContentPane().add(new BasicBackgroundPanel("./assets/img/splash_bg.png"));
        frame.pack();
        frame.setSize(Dimension.WINDOW_MIN_WIDTH, Dimension.WINDOW_MIN_HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) throws IOException {
        new SplashMenuGUI();
    }

}
