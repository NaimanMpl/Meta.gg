package fr.r34.metagg;

public enum MetaAttributes {

    TITLE("dc:title", "Titre"),
    SUBJECT("dc:subject", "Sujet"),
    KEYWORD("meta:keyword", "Mots-clés"),
    AUTHOR("meta:initial-creator", "Auteur"),
    CREATION_DATE("meta:creation-date", "Date de création"),
    PAGE_COUNT("meta:page-count", "Nombre de pages"),
    CHARACTERS_COUNT("meta:character-count", "Nombre de caractères"),
    PARAGRAPHS_COUNT("meta:paragraph-count", "Nombre de paragraphes"),
    WORD_COUNT("meta:word-count", "Nombre de mots");
    private final String tag;
    private final String title;

    /**
     * Enumération qui stock les différents tags (du fichier XML) des attributs que l'on souhaite lire.
     * Cet énumération a pour objectif de simplifier la lecture du code et de stocker les différents
     * tags des métadonnées et leur attribuer un titre pour simplifier l'affichage de ces dernières.
     * @param tag Le tag de l'attribut que l'on souhaite lire
     * @param title Le titre que l'on souhaite attributer au tag lors de l'affichage des métadonnées.
     */
    MetaAttributes(String tag, String title) {
        this.tag = tag;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
    public String getTag() {
        return tag;
    }
}
