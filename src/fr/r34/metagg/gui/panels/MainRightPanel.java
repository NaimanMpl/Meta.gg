package fr.r34.metagg.gui.panels;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.Strings;
import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.gui.Dimension;
import fr.r34.metagg.gui.custombuttons.CustomEditButton;
import fr.r34.metagg.manager.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

public class MainRightPanel extends JPanel {

    private BufferedImage fileIcon;
    private JLabel picture;
    private final JLabel name;
    private final JLabel size;
    private final JLabel title;
    private final JLabel subject;
    private final JLabel pagesAmount;
    private final JLabel wordsAmount;
    private final JLabel charAmount;
    private final JLabel paragraphsAmount;
    private final JLabel showImgs;
    private final JLabel showLinks;
    private final JLabel panelTitle;
    private final JLabel keywords;
    private final JTextField titleField, subjectField;

    private final MetaFile metaFile;
    private final ArrayList<JTextField> keywordsFieldsList;
    private final CustomEditButton editButton;
    private final Utils utils;
    private final JPanel panel, showLinksPanel, keywordsPanel;

    public MainRightPanel(MetaFile metaFile) throws UnsupportedLookAndFeelException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.metaFile = metaFile;
        this.keywordsFieldsList = new ArrayList<>();
        this.utils = new Utils();
        this.panel = new LinksPanel(metaFile);

        titleField = new JTextField(metaFile.getTitle());
        subjectField = new JTextField(metaFile.getSubject());

        panelTitle = new JLabel(Strings.RIGHT_PANEL_TITLE);
        picture = new JLabel();
        name = new JLabel(metaFile.getFile().getName());
        double round = (double) Math.round(metaFile.getSize() * 10) / 10;
        size = new JLabel(round + "KB, " + metaFile.getCreationDate().substring(0, 10));
        title = new JLabel(Strings.TITLE);
        subject = new JLabel(Strings.SUBJECT);
        keywords = new JLabel(Strings.KEYWORDS);
        pagesAmount = new JLabel(Strings.PAGES_AMOUNT + metaFile.getPagesAmount());
        wordsAmount = new JLabel(Strings.WORDS_AMOUNT + metaFile.getWordAmount());
        charAmount = new JLabel(Strings.CHARACTER_AMOUNT + metaFile.getCharacterAmount());
        paragraphsAmount = new JLabel(Strings.PARAGRAPHS_AMOUNT + metaFile.getPagesAmount());

        showImgs = new JLabel(Strings.SHOW_IMAGES, SwingConstants.CENTER);
        showImgs.setForeground(Colors.BLUE_0);
        showImgs.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        showImgs.setFont(Dimension.ANNOTATION_FONT);

        showLinks = new JLabel(Strings.SHOW_HYPERTEXT_LINKS, SwingConstants.CENTER);
        showLinks.setForeground(Colors.BLUE_0);
        showLinks.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        showLinks.setFont(Dimension.ANNOTATION_FONT);
        showLinks.addMouseListener(new DisplayLinksAction());

        JPanel titlePanel = new JPanel();
        JPanel subjectPanel = new JPanel();
        JPanel showPanel = new JPanel();
        this.showLinksPanel = new JPanel();
        this.keywordsPanel = new KeywordsPanel(metaFile, keywordsFieldsList);

        showLinksPanel.add(showLinks);
        showLinksPanel.setVisible(false);

        editButton = new CustomEditButton();
        editButton.addActionListener(new EditAction());

        titleField.setBackground(null);
        titleField.setBorder(null);
        titleField.setForeground(Colors.WHITE);
        titleField.setEditable(false);

        subjectField.setBackground(null);
        subjectField.setBorder(null);
        subjectField.setForeground(Colors.WHITE);
        subjectField.setEditable(false);

        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(null);
        titlePanel.setBorder(null);

        subjectPanel.setBackground(null);
        subjectPanel.setBorder(null);

        subjectPanel.setLayout(new BoxLayout(subjectPanel, BoxLayout.Y_AXIS));
        titlePanel.add(title);
        titlePanel.add(titleField);

        subjectPanel.add(subject);
        subjectPanel.add(subjectField);

        showPanel.setLayout(new BoxLayout(showPanel, BoxLayout.Y_AXIS));
        showPanel.setBackground(null);
        showPanel.setBorder(null);
        showPanel.add(showImgs);
        showPanel.add(showLinks);

        initFont();
        initColor();

        this.setLayout(new GridBagLayout());

        try {
            URL odtUrl = this.getClass().getResource(Strings.ODT_FILE_PATH);
            if (odtUrl == null) throw new IllegalArgumentException(Strings.ERROR_ODT_ICON_NOT_LOADED);
            fileIcon = ImageIO.read(odtUrl);
            ImageIcon imgIcon = utils.getImageFromResource(Strings.ODT_FILE_PATH);
            picture = new JLabel(imgIcon);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.WEST;
            gbc.weighty = 1;

            List<JComponent> components = Arrays.asList(
                    panelTitle, picture, name, size,
                    titlePanel, subjectPanel, showLinksPanel, keywords,
                    keywordsPanel, pagesAmount, wordsAmount, charAmount,
                    paragraphsAmount, showPanel, editButton
            );
            gbc.gridx = 0;
            for (int i = 0; i < components.size(); i++) {
                gbc.gridy = i;
                this.add(components.get(i), gbc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setBorder(new EmptyBorder(
                Dimension.LITTLE_MARGIN,
                Dimension.LITTLE_MARGIN,
                Dimension.LITTLE_MARGIN,
                Dimension.LITTLE_MARGIN
        ));
        this.setPreferredSize(new java.awt.Dimension(
                (int) (0.3* Dimension.WINDOW_WIDTH),
                Dimension.WINDOW_HEIGHT
        ));
        this.setBackground(Colors.BLUE_1);
    }

    private void initColor() {
        panelTitle.setForeground(Colors.WHITE);
        name.setForeground(Colors.WHITE);
        size.setForeground(Colors.BLUE_0);
        title.setForeground(Colors.WHITE);
        subject.setForeground(Colors.WHITE);
        pagesAmount.setForeground(Colors.WHITE);
        wordsAmount.setForeground(Colors.WHITE);
        charAmount.setForeground(Colors.WHITE);
        paragraphsAmount.setForeground(Colors.WHITE);
        keywords.setForeground(Colors.WHITE);
    }

    public void initFont() {
        panelTitle.setFont(Dimension.TITLE_FONT);
        name.setFont(Dimension.SUBTITLE_FONT);
        size.setFont(Dimension.SUBTITLE_FONT);
        title.setFont(Dimension.SUBTITLE_FONT);
        subject.setFont(Dimension.SUBTITLE_FONT);
        keywords.setFont(Dimension.SUBTITLE_FONT);
        pagesAmount.setFont(Dimension.SUBTITLE_FONT);
        wordsAmount.setFont(Dimension.SUBTITLE_FONT);
        charAmount.setFont(Dimension.SUBTITLE_FONT);
        paragraphsAmount.setFont(Dimension.SUBTITLE_FONT);
        showImgs.setFont(Dimension.SUBTITLE_FONT);
        showLinks.setFont(Dimension.SUBTITLE_FONT);
    }

    class EditAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateTextField();
        }

        private void updateTextField() {
            titleField.setEditable(!titleField.isEditable());
            subjectField.setEditable(!subjectField.isEditable());
            for (JTextField keywordField : keywordsFieldsList) {
                keywordField.setEditable(!keywordField.isEditable());
            }
            if (titleField.isEditable()) {
                editButton.setText(Strings.SAVE_MODIFICATIONS);
            } else {
                editButton.setText(Strings.EDIT);
                metaFile.setTitle(titleField.getText());
                metaFile.setSubject(subjectField.getText());
                metaFile.getKeywords().clear();
                for (JTextField keywordField : keywordsFieldsList) {
                    metaFile.getKeywords().add(keywordField.getText());
                }
                metaFile.save();
            }
        }
    }

    class DisplayLinksAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            keywordsPanel.setVisible(!keywordsPanel.isVisible());
            pagesAmount.setVisible(!pagesAmount.isVisible());
            wordsAmount.setVisible(!wordsAmount.isVisible());
            charAmount.setVisible(!charAmount.isVisible());
            paragraphsAmount.setVisible(!paragraphsAmount.isVisible());
            showLinksPanel.setVisible(!showLinksPanel.isVisible());
        }
    }

}
