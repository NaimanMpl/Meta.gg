package fr.r34.metagg.gui.panels.openfile;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.Strings;
import fr.r34.metagg.gui.Colors;
import fr.r34.metagg.gui.Dimension;
import fr.r34.metagg.gui.MainMenuGUI;
import fr.r34.metagg.gui.custombuttons.CustomAddKeywordButton;
import fr.r34.metagg.manager.Utils;
import fr.r34.metagg.gui.custombuttons.CustomEditButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
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
    private final JPanel linksPanel, showLinksPanel, keywordsPanel;
    private final ArrayList<File> pictures;
    private File currentPicture;
    private int i;

    public MainRightPanel(MainMenuGUI main, MetaFile metaFile) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.metaFile = metaFile;
        this.keywordsFieldsList = new ArrayList<>();
        this.pictures = new ArrayList<>(metaFile.getPictures().keySet());
        this.utils = new Utils();
        this.linksPanel = new LinksPanel(metaFile);
        this.i = 0;

        titleField = new JTextField(metaFile.getTitle().isEmpty() ? Strings.NO_TITLE : metaFile.getTitle());
        subjectField = new JTextField(metaFile.getSubject().isEmpty() ? Strings.NO_SUBJECT : metaFile.getSubject());

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
        showImgs.addMouseListener(new DisplayImagesAction());

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

        JPanel buttonsPanel = new JPanel();

        editButton = new CustomEditButton();
        editButton.addActionListener(new EditAction());

        CustomAddKeywordButton addKeywordButton = new CustomAddKeywordButton(main, metaFile);

        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.setBackground(null);

        buttonsPanel.add(editButton);
        buttonsPanel.add(addKeywordButton);

        titleField.setBackground(null);
        titleField.setBorder(null);
        titleField.setForeground(Colors.WHITE);
        titleField.setEditable(false);
        titleField.setPreferredSize(new java.awt.Dimension((int) (0.25*Dimension.WINDOW_WIDTH), 20));

        subjectField.setBackground(null);
        subjectField.setBorder(null);
        subjectField.setForeground(Colors.WHITE);
        subjectField.setEditable(false);
        subjectField.setPreferredSize(new java.awt.Dimension((int) (0.25*Dimension.WINDOW_WIDTH), 20));


        linksPanel.setVisible(false);

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
            ImageIcon imgIcon;
            String miniaturePath = Strings.ODT_FILE_PATH;
            if (metaFile.getThumbnail() != null) {
                BufferedImage miniatureImg = ImageIO.read(new File(metaFile.getThumbnail().getAbsolutePath()));
                imgIcon = new ImageIcon(miniatureImg);
                Image resizeImage = imgIcon.getImage().getScaledInstance(75, 88, Image.SCALE_SMOOTH);
                imgIcon = new ImageIcon(resizeImage);
            } else {
                imgIcon = utils.getImageFromResource(miniaturePath);
            }
            JPanel pictureLabel = new JPanel();
            picture = new JLabel(imgIcon);
            pictureLabel.add(picture);
            pictureLabel.setPreferredSize(new java.awt.Dimension((int) (0.25* Dimension.WINDOW_WIDTH), 100));
            pictureLabel.setBackground(Colors.BLUE_1);
            pictureLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.WEST;
            gbc.weighty = 1;

            List<JComponent> components = Arrays.asList(
                    panelTitle, pictureLabel, name, size,
                    titlePanel, subjectPanel, linksPanel, keywords,
                    keywordsPanel, pagesAmount, wordsAmount, charAmount,
                    paragraphsAmount, showPanel, buttonsPanel
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
                Dimension.DEFAULT_MARGIN,
                Dimension.DEFAULT_MARGIN,
                Dimension.DEFAULT_MARGIN,
                Dimension.DEFAULT_MARGIN
        ));
        this.setPreferredSize(new java.awt.Dimension(
                (int) (0.3* Dimension.WINDOW_WIDTH),
                Dimension.WINDOW_HEIGHT
        ));
        this.setBackground(Colors.BLUE_1);
    }

    private void initColor() {
        titleField.setForeground(Colors.WHITE);
        subjectField.setForeground(Colors.WHITE);
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
        titleField.setFont(Dimension.PARAGRAPH_FONT);
        subjectField.setFont(Dimension.PARAGRAPH_FONT);
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
            keywords.setVisible(!keywords.isVisible());
            paragraphsAmount.setVisible(!paragraphsAmount.isVisible());
            if (!linksPanel.isVisible()) showLinks.setText(Strings.HIDE_HYPERTEXT_LINKS);
            else showLinks.setText(Strings.SHOW_HYPERTEXT_LINKS);
            linksPanel.setVisible(!linksPanel.isVisible());
        }
    }

    class DisplayImagesAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (pictures.isEmpty()) {
                JOptionPane.showMessageDialog(null, Strings.NO_PICTURES, "Système", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Object[] options = {"Suivant", "Précédent", "Quitter"};

            currentPicture = pictures.get(i);

            String size = metaFile.getMedia().get(currentPicture.getName()).get(1);
            ImageIcon imageIcon = new ImageIcon(currentPicture.getAbsolutePath());
            JLabel img = new JLabel(imageIcon);
            JLabel imgType = new JLabel(metaFile.getPictures().get(currentPicture).getTitle() + " " + size);

            JPanel imagePanel = new JPanel();

            imgType.setForeground(Colors.WHITE);
            imgType.setFont(Dimension.SUBTITLE_FONT);
            imgType.setAlignmentX(Component.CENTER_ALIGNMENT);
            img.setAlignmentX(Component.CENTER_ALIGNMENT);

            imagePanel.setBackground(Colors.BLUE_1);
            imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
            imagePanel.add(img);
            imagePanel.add(imgType);

            int choice = -5;
            while (choice != JOptionPane.CANCEL_OPTION && choice != -1) {
                ImageIcon currentPictureIcon = new ImageIcon(currentPicture.getAbsolutePath());
                Image currentPictureImg = currentPictureIcon.getImage().getScaledInstance(
                        currentPictureIcon.getIconWidth() - 150,
                        currentPictureIcon.getIconHeight() - 150,
                        Image.SCALE_SMOOTH
                );
                img.setIcon(new ImageIcon(currentPictureImg));
                size = metaFile.getMedia().get(currentPicture.getName()).get(1);
                imgType.setText(metaFile.getPictures().get(currentPicture).getTitle() + " " + size);
                choice = JOptionPane.showOptionDialog(
                        null,
                        imagePanel,
                        currentPicture.getName(),
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options,
                        null
                );
                switch (choice) {
                    case JOptionPane.OK_OPTION -> {
                        i += 1;
                        currentPicture = getNextPicture(pictures, i);
                    }
                    case JOptionPane.NO_OPTION -> {
                        i -= 1;
                        currentPicture = getPreviousPicture(pictures, i);
                    }
                }

            }
        }

        private File getNextPicture(ArrayList<File> pictures, int k) {
            if (k >= pictures.size()) {
                i = 0;
                return pictures.get(i);
            }
            return pictures.get(k);
        }

        private File getPreviousPicture(ArrayList<File> pictures, int k) {
            if (k < 0) {
                i = pictures.size() - 1;
                return pictures.get(i);
            }
            return pictures.get(k);
        }

    }

}
