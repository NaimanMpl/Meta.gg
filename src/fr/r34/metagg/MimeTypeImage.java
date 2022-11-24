package fr.r34.metagg;

public enum MimeTypeImage {
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

    MimeTypeImage(String mimetype, String title){
        this.mimetype = mimetype;
        this.title = title;
    }

    public String getMimetype() {
        return mimetype;
    }

    public String getTitle() {
        return title;
    }
}
