package fr.r34.metagg.gui.panels.openfile;

import fr.r34.metagg.MetaFile;
import fr.r34.metagg.Constants;
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
import java.util.*;
import java.util.List;

public class MainRightPanel extends JPanel {

    private JLabel picture;
    private JLabel name;
    private JLabel size;
    private JLabel title;
    private JLabel subject;
    private JLabel pagesAmount;
    private JLabel wordsAmount;
    private JLabel charAmount;
    private JLabel paragraphsAmount;
    private JLabel showImgs;
    private JLabel showLinks;
    private JLabel panelTitle;
    private JLabel keywords;
    private final JTextField titleField, subjectField;
    private final MetaFile metaFile;
    private final ArrayList<JTextField> keywordsFieldsList;
    private final CustomEditButton editButton;
    private final Utils utils;
    private final JPanel linksPanel, showLinksPanel, keywordsPanel;
    private final ArrayList<File> pictures;
    private File currentPicture;
    private int i;

    /**
     * Panneau de droite de l'application, contenat toutes les métadonnées du fichier renseigné en paramètre
     * affiché en colonne ainsi que de deux boutons l'un permettant l'édition de ces métadonnées ainsi que la sauvegarde
     * et l'autre permettant l'ajout d'un mot-clé.
     * @param main L'instance de la classe principale contenant la frame principale de l'application
     * @param metaFile Le fichier dont on souhaite afficher les métadonnées
     * @throws UnsupportedLookAndFeelException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public MainRightPanel(MainMenuGUI main, MetaFile metaFile) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.metaFile = metaFile;
        this.keywordsFieldsList = new ArrayList<>();
        this.pictures = new ArrayList<>(metaFile.getPictures().keySet());
        this.utils = new Utils();
        this.linksPanel = new LinksPanel(metaFile);
        this.i = 0;

        // Si jamais le fichier n'a pas de titre ou de sujet alors le message "Pas de titre" ou "Pas de sujet" est affiché à l'écran
        titleField = new JTextField(metaFile.getTitle().isEmpty() ? Constants.NO_TITLE : metaFile.getTitle());
        subjectField = new JTextField(metaFile.getSubject().isEmpty() ? Constants.NO_SUBJECT : metaFile.getSubject());

        initLabel();

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

        /*
        Chargement de la miniature afin de pouvoir l'afficher au top du panneau.
        Si jamais le fichier n'a pas de miniature, alors une image par défaut est affichée.
         */
        try {
            ImageIcon imgIcon;
            String miniaturePath = utils.getIconPathFromType(metaFile);
            if (metaFile.getThumbnail() != null) {
                // Chargement de l'image de la miniature
                BufferedImage miniatureImg = ImageIO.read(new File(metaFile.getThumbnail().getAbsolutePath()));
                imgIcon = new ImageIcon(miniatureImg);
                // Redimensionnement de la miniature afin qu'elle n'apparaisse pas trop grande sur le GUI
                Image resizeImage = imgIcon.getImage().getScaledInstance(75, 88, Image.SCALE_SMOOTH);
                imgIcon = new ImageIcon(resizeImage);
            } else {
                // Chargement de l'image par défaut si le fichier n'a pas de miniature
                imgIcon = utils.getImageFromResource(miniaturePath);
            }

            // Création d'un panel contenant l'image, afin qu'il soit plus facile de la centrer
            JPanel picturePanel = new JPanel();
            picture = new JLabel(imgIcon);
            picturePanel.add(picture);
            picturePanel.setPreferredSize(new java.awt.Dimension((int) (0.25* Dimension.WINDOW_WIDTH), 100));
            picturePanel.setBackground(Colors.BLUE_1);
            picturePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.WEST;
            gbc.weighty = 1;

            // Création de la liste des composants du panneau de droite dans l'ordre où ils sont censés apparaître
            List<JComponent> components = Arrays.asList(
                    panelTitle, picturePanel, name, size,
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

    /**
     * Initialisation des différents textes de l'application et remplissage si nécessaire de ces derniers
     */
    private void initLabel() {
        panelTitle = new JLabel(Constants.RIGHT_PANEL_TITLE);
        picture = new JLabel();
        name = new JLabel(metaFile.getFile().getName());
        double round = (double) Math.round(metaFile.getSize() * 10) / 10;
        size = new JLabel(round + "KB, " + metaFile.getCreationDate().substring(0, 10));
        title = new JLabel(Constants.TITLE);
        subject = new JLabel(Constants.SUBJECT);
        keywords = new JLabel(Constants.KEYWORDS);
        pagesAmount = new JLabel(Constants.PAGES_AMOUNT + metaFile.getPagesAmount());
        wordsAmount = new JLabel(Constants.WORDS_AMOUNT + metaFile.getWordAmount());
        charAmount = new JLabel(Constants.CHARACTER_AMOUNT + metaFile.getCharacterAmount());
        paragraphsAmount = new JLabel(Constants.PARAGRAPHS_AMOUNT + metaFile.getPagesAmount());

        showImgs = new JLabel(Constants.SHOW_IMAGES, SwingConstants.CENTER);
        showImgs.setForeground(Colors.BLUE_0);
        showImgs.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        showImgs.setFont(Dimension.ANNOTATION_FONT);
        showImgs.addMouseListener(new DisplayImagesAction());

        showLinks = new JLabel(Constants.SHOW_HYPERTEXT_LINKS, SwingConstants.CENTER);
        showLinks.setForeground(Colors.BLUE_0);
        showLinks.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        showLinks.setFont(Dimension.ANNOTATION_FONT);
        showLinks.addMouseListener(new DisplayLinksAction());
    }

    /**
     * Initialisation des couleurs des textes
     */
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

    /**
     * Initialisation de la police utilisée par les textes ainsi que leur taille
     */
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

    /**
     * Classe gérant l'édition des métadonnées. Lorsque l'utilisateur clique sur le bouton "Éditer"
     * L'ensemble des JTextField deviennent éditables et le bouton "Éditer" devient alors "Sauvegarder".
     * Par contre, si l'utilisateur clique sur le bouton "Sauvegarder" l'ensemble des JTextFields deviennent
     * non éditable et on effectue la sauvegarde du fichier en partant des données renseigné dans les JTextFields.
     */
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
                editButton.setText(Constants.SAVE_MODIFICATIONS);
            } else {
                editButton.setText(Constants.EDIT);
                metaFile.setTitle(titleField.getText());
                System.out.println("Titre : " + metaFile.getTitle());
                metaFile.setSubject(subjectField.getText());
                metaFile.getKeywords().clear();
                for (JTextField keywordField : keywordsFieldsList) {
                    metaFile.getKeywords().add(keywordField.getText());
                }
                metaFile.save();
            }
        }
    }

    /**
     * Classe gérant l'affichage des liens hypertextes.
     * Lorsque l'on clique sur le bouton "Afficher les liens hypertextes", l'ensemble du panneau de droite
     * contenant les données principales et diverses devient invisible (mais toujours présent) pour
     * laisser place au panneau qui contient les liens hypertextes. Si l'on rappuie sur le bouton l'ensemble du panneau de droite redevient visible.
     */
    class DisplayLinksAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            keywordsPanel.setVisible(!keywordsPanel.isVisible());
            pagesAmount.setVisible(!pagesAmount.isVisible());
            wordsAmount.setVisible(!wordsAmount.isVisible());
            charAmount.setVisible(!charAmount.isVisible());
            keywords.setVisible(!keywords.isVisible());
            paragraphsAmount.setVisible(!paragraphsAmount.isVisible());
            if (!linksPanel.isVisible()) showLinks.setText(Constants.HIDE_HYPERTEXT_LINKS);
            else showLinks.setText(Constants.SHOW_HYPERTEXT_LINKS);
            linksPanel.setVisible(!linksPanel.isVisible());
        }
    }

    /**
     * Classe gérant l'affichage des images sous la forme d'un popup avec 2 boutons permettant
     * la navigation entre les différentes images du fichier.
     */
    class DisplayImagesAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (pictures.isEmpty()) {
                JOptionPane.showMessageDialog(null, Constants.NO_PICTURES, "Système", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Liste des options disponibles
            Object[] options = {"Suivant", "Précédent", "Quitter"};

            currentPicture = pictures.get(i);

            String size = metaFile.getMedia().get(currentPicture.getName()).get(1);
            ImageIcon imageIcon = new ImageIcon(currentPicture.getAbsolutePath());
            JLabel img = new JLabel(imageIcon);
            // Affichage des données de l'image sous la forme : File.png : 34.0Ko
            JLabel imgType = new JLabel(metaFile.getPictures().get(currentPicture).getTitle() + " " + size);

            // Création du label contenant l'image ainsi que les boutons de navigation
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
            /*
            Algorithme permettant de continuer à afficher la popup contenant l'image tant que l'utilisateur
            n'appuie pas sur le bouton pour fermer la popup ou le bouton "Quitter"
             */
            while (choice != JOptionPane.CANCEL_OPTION && choice != -1) {
                ImageIcon currentPictureIcon = new ImageIcon(currentPicture.getAbsolutePath());
                // Redimension de l'image si jamais cette dernière est trop grande
                Image currentPictureImg = currentPictureIcon.getImage().getScaledInstance(
                        currentPictureIcon.getIconWidth() - 150,
                        currentPictureIcon.getIconHeight() - 150,
                        Image.SCALE_SMOOTH
                );
                img.setIcon(new ImageIcon(currentPictureImg));
                size = metaFile.getMedia().get(currentPicture.getName()).get(1);
                imgType.setText(metaFile.getPictures().get(currentPicture).getTitle() + " " + size);
                // Génération et affichage de la popup contenant l'image ainsi que les boutons de navigation à partir du label créer précédemment
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

        /**
         * Permet de récupérer l'image qui se situe après celle affichée à l'écran.
         * @param pictures Liste des images du fichier
         * @param k Indice de l'image affiché actuellement dans l'application
         * @return L'image suivante
         */
        private File getNextPicture(ArrayList<File> pictures, int k) {
            if (k >= pictures.size()) {
                i = 0;
                return pictures.get(i);
            }
            return pictures.get(k);
        }

        /**
         * Permet de récupérer l'image qui se situe avant celle affichée à l'écran.
         * @param pictures Liste des images du fichier
         * @param k Indice de l'image affiché actuellement dans l'application
         * @return L'image précédente
         */
        private File getPreviousPicture(ArrayList<File> pictures, int k) {
            if (k < 0) {
                i = pictures.size() - 1;
                return pictures.get(i);
            }
            return pictures.get(k);
        }

    }

}
