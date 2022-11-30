package fr.r34.metagg.gui;

import javax.swing.*;

public class SplashMenuGUI {

    private JFrame frame;

    private final static String APP_TITLE = "Meta.gg";

    public SplashMenuGUI(){
        frame = new JFrame();
        frame.setTitle(APP_TITLE);

        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) {
        new SplashMenuGUI();
    }

}
