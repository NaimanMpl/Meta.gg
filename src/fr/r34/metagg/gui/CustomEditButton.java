package fr.r34.metagg.gui;

import fr.r34.metagg.Strings;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.Dimension;

public class CustomEditButton extends JButton {

    private static Color color = new Color(250, 250, 250);

    private static Color colorSelection = new Color(149, 149, 155, 255);

    private static Color colorInsideBorder = new Color(250, 250, 250, 0); //Transparent

    public CustomEditButton() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        super();
        this.setBorderPainted(false);
        this.setBackground(color);
        this.setOpaque(true);
        this.setPreferredSize(new Dimension(fr.r34.metagg.gui.Dimension.BUTTON_WIDTH, fr.r34.metagg.gui.Dimension.BUTTON_HEIGHT));
        this.setText(Strings.EDIT);
        this.setFont((new Font(fr.r34.metagg.gui.Dimension.FONT, Font.PLAIN, fr.r34.metagg.gui.Dimension.SUBTITLE_SIZE)));
        this.setForeground(Color.BLACK);
        this.setIcon(new ImageIcon(Strings.EDIT_BUTTON_ICON_PATH));
        this.setHorizontalTextPosition(AbstractButton.RIGHT);
        this.setIconTextGap(10);
        this.setVisible(true);
        this.setUImanagerCustomEditButton();
    }

    public void setUImanagerCustomEditButton() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        UIManager.put("Button.focus", new ColorUIResource(color));
        UIManager.put("ToggleButton.focus", new ColorUIResource(color));
        UIManager.put("Button.shadow", new ColorUIResource(color));
        UIManager.put("Button.select", new ColorUIResource(colorSelection));
        UIManager.put("Button.border", new ColorUIResource(color));
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        JFrame frame = new JFrame();
        frame.setPreferredSize(new Dimension(200, 200));
        Container cp = frame.getContentPane();
        CustomEditButton customEditButton = new CustomEditButton();
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        jPanel.add(customEditButton);
        cp.add(jPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

}
