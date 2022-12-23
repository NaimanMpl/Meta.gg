package fr.r34.metagg;

public enum MimeTypeOD {

    ODT("application/vnd.oasis.opendocument.text", "ODT"),
    ODS("application/vnd.oasis.opendocument.spreadsheet", "ODS"),
    ODG("application/vnd.oasis.opendocument.graphics","ODG"),
    ODP("application/vnd.oasis.opendocument.presentation","ODP");

    String mimetype, title;

    MimeTypeOD(String mimetype, String title){
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
