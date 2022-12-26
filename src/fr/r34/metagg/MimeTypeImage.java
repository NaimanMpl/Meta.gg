package fr.r34.metagg;

public enum MimeTypeImage implements MimeType {
    AVIF("image/avif", "AVIF"),
    BMP("image/bmp", "BMP"),
    GIF("image/gif", "GIF"),
    JPEG("image/jpeg", "JPEG")  ,
    JPG("image/jpeg", "JPG"),
    PNG("image/png", "PNG"),
    TIF("image/tiff", "TIF"),
    TIFF("image/tiff", "TIFF"),
    WEBP("image/webp", "WEBP");

    String mimetype, title;

    /**
     * Enumeration qui liste et stock et liste tous
     * les types mimes d'image commun. On utilise
     * cet enumeration pour déterminer le type d'image
     * que contient notre fichier ODT.
     *
     * @param mimetype  mime type de l'image.
     * @param title     titre correspondant au mime type de l'image.
     */
    MimeTypeImage(String mimetype, String title){
        this.mimetype = mimetype;
        this.title = title;
    }

    /**
     * Renvoie le type mime de l'image.
     * @return  le type mime de l'image.
     */
    public String getMimetype() {
        return mimetype;
    }

    /**
     * Renvoie le titre du type de l'image.
     * @return  le titre du type de l'image.
     */
    public String getTitle() {
        return title;
    }
}
