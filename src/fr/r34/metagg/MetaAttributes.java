package fr.r34.metagg;

public enum MetaAttributes {
    TITLE("dc:title", "Titre"),
    SUBJECT("dc:subject", "Sujet"),
    KEYWORD("meta:keyword", "Mots-clés"),
    CREATION_DATE("meta:creation-date", "Date de création"),
    PAGE_COUNT("meta:page-count", "Nombre de pages"),
    CHARACTERS_COUNT("meta:character-count", "Nombre de caractères"),
    PARAGRAPHS_COUNT("meta:paragraph-count", "Nombre de paragraphes"),
    WORD_COUNT("meta:word-count", "Nombre de mots");
    private final String tag;
    private final String title;
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
