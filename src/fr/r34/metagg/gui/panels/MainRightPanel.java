package fr.r34.metagg.gui.panels;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.Strings;
import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.gui.CustomEditButton;
import fr.r34.metagg.gui.Dimension;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class MainRightPanel extends JPanel {

    private ImageIcon fileIcon;
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
    private final JPanel titlePanel;
    private final JPanel subjectPanel;
    private final JPanel showPanel;
    private final JTextField titleField, subjectField;
    private final JScrollPane keywordsScroller;
    private final Vector<String> keywordsModel;
    private final JList<String> keywordsList;

    private final CustomEditButton edit_Button;

    private final MetaFile metaFile;
    private final SimpleDateFormat dateFormat;


    public MainRightPanel(MetaFile metaFile)  {
        this.metaFile = metaFile;

        titleField = new JTextField(metaFile.getTitle());
        subjectField = new JTextField(metaFile.getSubject());

        panelTitle = new JLabel(Strings.RIGHT_PANEL_TITLE);
        picture = new JLabel();
        name = new JLabel(metaFile.getFile().getName());
        double round = (double) Math.round(metaFile.getSize() * 10) / 10;
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dateText = dateFormat.format(metaFile.getCreationDate());
        size = new JLabel(round + "KB, " + dateText);
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
        showLinks = new JLabel(Strings.SHOW_HYPERTEXT_LINKS, SwingConstants.CENTER);
        showLinks.setForeground(Colors.BLUE_0);
        showLinks.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        titlePanel = new JPanel();
        subjectPanel = new JPanel();
        showPanel = new JPanel();

        titleField.setBackground(null);
        titleField.setBorder(null);
        titleField.setForeground(Colors.WHITE);
        titleField.setEditable(false);

        subjectField.setBackground(null);
        subjectField.setBorder(null);
        subjectField.setForeground(Colors.WHITE);
        subjectField.setEditable(false);

        titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(null);
        titlePanel.setBorder(null);

        subjectPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        subjectPanel.setBackground(null);
        subjectPanel.setBorder(null);

        titlePanel.add(title);
        titlePanel.add(titleField);

        subjectPanel.add(subject);
        subjectPanel.add(subjectField);

        showPanel.setLayout(new FlowLayout());
        showPanel.setBackground(null);
        showPanel.setBorder(null);
        showPanel.add(showImgs);
        showPanel.add(showLinks);

        initFont();
        initColor();

        keywordsModel = new Vector<>();
        initKeywordsModel();

        keywordsList = new JList<>(keywordsModel);
        keywordsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        keywordsList.setLayoutOrientation(JList.VERTICAL);
        keywordsList.setVisibleRowCount(-1);
        keywordsList.setBackground(Colors.BLUE_1);
        keywordsList.setForeground(Colors.WHITE);

        keywordsScroller = new JScrollPane(keywordsList);

        keywordsScroller.setPreferredSize(new java.awt.Dimension(150, 80));
        keywordsScroller.setBorder(new EmptyBorder(0, Dimension.LITTLE_MARGIN, 0, 0));
        keywordsScroller.setBackground(Colors.BLUE_1);

        try {
            edit_Button = new CustomEditButton();
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException |
                 ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        this.setLayout(new GridBagLayout());

        fileIcon = new ImageIcon(Strings.FILE_BUTTON_ICON_PATH);
        picture = new JLabel(fileIcon);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weighty = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(panelTitle, gbc);
        gbc.gridy = 1;
        this.add(picture, gbc);
        gbc.gridy = 2;
        this.add(name, gbc);
        gbc.gridy = 3;
        this.add(size, gbc);
        gbc.gridy = 4;
        this.add(keywords, gbc);
        gbc.gridy = 5;
        this.add(keywordsScroller, gbc);
        gbc.gridy = 6;
        this.add(pagesAmount, gbc);
        gbc.gridy = 7;
        this.add(wordsAmount, gbc);
        gbc.gridy = 8;
        this.add(charAmount, gbc);
        gbc.gridy = 9;
        this.add(paragraphsAmount, gbc);
        gbc.gridy = 10;
        this.add(edit_Button, gbc);

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

    private void initKeywordsModel() {
        keywordsModel.addAll(metaFile.getKeywords());
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

}
