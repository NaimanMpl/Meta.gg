package fr.r34.metagg;

public enum MimeTypeOD {

    ODT("application/vnd.oasis.opendocument.text", "ODT"),
    ODS("application/vnd.oasis.opendocument.spreadsheet", "ODS"),
    ODG("application/vnd.oasis.opendocument.graphics","ODG"),
    ODP("application/vnd.oasis.opendocument.presentation","ODP");

    String mimetype, title;
    /**
     * Enumeration qui liste et stock et liste des
     * types mimes de fichier OpenDocument. On utilise
     * cet enumeration pour déterminer le type mime
     * que contient notre fichier OD.
     *
     * @param mimetype  mime type du fichier.
     * @param title     titre correspondant au mime type du fichier.
     */
    MimeTypeOD(String mimetype, String title){
        this.mimetype = mimetype;
        this.title = title;
    }

    /**
     * Retourne le type mime du fichier
     * @return  mimetype
     */
    public String getMimetype() {
        return mimetype;
    }

    /**
     * Retourne le titre du type mime du fichier
     * @return  title
     */
    public String getTitle() {
        return title;
    }
}
