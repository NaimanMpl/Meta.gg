package fr.r34.metagg.gui.custombuttons;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.Strings;
import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.gui.MainMenuGUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class CustomAddKeywordButton extends JButton {

    public CustomAddKeywordButton(MainMenuGUI main, MetaFile metaFile) {
        super();
        this.setText(Strings.ADD_KEYWORD);
        this.setBackground(Colors.WHITE);
        this.setHorizontalTextPosition(AbstractButton.RIGHT);
        this.setPreferredSize(new Dimension(fr.r34.metagg.gui.Dimension.BUTTON_WIDTH, fr.r34.metagg.gui.Dimension.BUTTON_HEIGHT));
        this.setFont((new Font(fr.r34.metagg.gui.Dimension.FONT, Font.PLAIN, fr.r34.metagg.gui.Dimension.SUBTITLE_SIZE)));
        this.addActionListener(e -> {
            String keyword = JOptionPane.showInputDialog(Strings.ENTER_KEYWORD);
            if (keyword == null || keyword.isEmpty()) return;
            metaFile.getKeywords().add(keyword);
            try {
                main.updateRightPanel(metaFile);
            } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IOException |
                     IllegalAccessException ex) {
                ex.printStackTrace();
            }
        });
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
